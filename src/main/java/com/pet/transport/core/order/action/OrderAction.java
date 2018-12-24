package com.pet.transport.core.order.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.service.IOrderService;
import com.pet.transport.core.order.util.OrderMessageUtil;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import com.pet.transport.uc.user.util.UserUtil;
import com.pet.transport.uc.wechat.core.pay.WXPayUtil;
import com.pet.transport.uc.wechat.core.pay.po.BaseResult;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.*;

@Controller
@RequestMapping("/ticket/order")
public class OrderAction {
    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

    @Autowired
    @Qualifier("ticketPriceServiceImpl")
    ITicketPriceService ticketPriceServiceImpl;


    private OrderMessageUtil orderMessageUtil = OrderMessageUtil.getInstance();
    private Log logger = LogFactory.getLog(OrderAction.class);

    private WXPayUtil wxPayUtil =new WXPayUtil();
    @RequestMapping("/mine")
    @ResponseBody
    public ModelAndView myOrder(){
        return new ModelAndView("myOrder");
    }

    private Map organizeRequest(HttpServletRequest  request){
        String startPlaceCode = (String) request.getParameter("startPlaceCode");
        String startPlaceName = (String) request.getParameter("startPlaceName");

        String destinationPlaceCode = (String)request.getParameter("destinationPlaceCode");
        String destinationPlaceName = (String) request.getParameter("destinationPlaceName");

        String transDate = (String)request.getParameter("transDate");
        String petKind = (String)request.getParameter("petKind");
        String petWeight = (String)request.getParameter("petWeight");
        String selHkx = (String) request.getParameter("selHkx");
        String selSmjc = (String) request.getParameter("selSmjc");

        //地址
        String placeAreaCode = (String)request.getParameter("placeAreaCode");
        String placeAreaName = (String)request.getParameter("placeAreaName");
        String placeDetail = (String)request.getParameter("placeDetail");
        //保价 从前台传递
        String insuredPrice = (String)request.getParameter("insuredPrice");




        //不使用前台传过来的信息 重新计算价格信息 防止攻击
        Map costParam = new HashMap();
        costParam.put("selHkx",selHkx);
        costParam.put("selSmjc",selSmjc);
        costParam.put("transDate",transDate);
        costParam.put("placeAreaCode",placeAreaCode);
        costParam.put("petKind",petKind);
        costParam.put("petWeight",petWeight);
        costParam.put("startPlaceCode",startPlaceCode);
        costParam.put("destinationPlaceCode",destinationPlaceCode);
        costParam.put("insuredPrice",insuredPrice);

        Map map = ticketPriceServiceImpl.getAllCost(costParam);

        //票价需要从后台生成 不能依赖前台传递的值
        String ticketPrice = (String) map.get("ticketPrice");//(String)request.getParameter("ticketPrice");
        //航空箱自动匹配 需要判断 如果为空 根据体重生成
        String petBoxTypeId = (String) map.get("boxTypeId");//(String)request.getParameter("petBoxTypeName");
        String petBoxTypeName = (String) map.get("boxTypeName");//(String)request.getParameter("petBoxTypeName");
        String petBoxPrice = (String) map.get("petBoxPrice");//(String)request.getParameter("petBoxPrice");
        //地址价格需要从后台生成
        String placePrice = (String) map.get("placePrice");//(String)request.getParameter("placePrice");
        //航空箱价格需要从后台生成
        //总价 需要传递
        String totalPrice =(String) map.get("totalPrice");// (String)request.getParameter("totalPrice");


        //获取当前登录人 判空 如果为空 从shrio中获取
        String userId = (String)request.getParameter("userId");
        String userMobile = (String)request.getParameter("userMobile");
        if(userId == null || "".equals(userId)){
            Map userMap =UserUtil.getInstance().getLoginUserMap();
            if(userMap!=null && !userMap.isEmpty()){
                if(userMap.containsKey("userId")){
                    userId = (String) userMap.get("userId");
                }
                if(userMap.containsKey("userMobile")){
                    userMobile = (String) userMap.get("userMobile");
                }
            }
        }




        Map param = new HashMap();
        param.put("startPlaceCode",startPlaceCode);
        param.put("startPlaceName",startPlaceName);
        param.put("destinationPlaceCode",destinationPlaceCode);
        param.put("destinationPlaceName",destinationPlaceName);
        param.put("transDate",transDate);
        param.put("petWeight",petWeight);
        param.put("petKind",petKind);
        param.put("ticketPrice",ticketPrice);
        param.put("petBoxTypeId",petBoxTypeId);
        param.put("petBoxTypeName",petBoxTypeName);
        param.put("petBoxPrice",petBoxPrice);
        param.put("placeAreaCode",placeAreaCode);
        param.put("placeAreaName",placeAreaName);
        param.put("placeDetail",placeDetail);
        param.put("placePrice",placePrice);
        param.put("userId",userId);
        param.put("userMobile",userMobile);
        param.put("insuredPrice",insuredPrice);
        param.put("totalPrice",totalPrice);

        return param;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveOrder(HttpServletRequest request, HttpServletResponse response){
        String ret = "";
        Map map = new HashMap();
        Map param =organizeRequest(request);

        //orderStatus 三种 草稿draft 已提交sumbit 已支付pay 已完成complate 线下支付 offlinePay
        String orderStatus = "draft";//(String)request.getParameter("orderStatus");
        String payStatus = "0";//(String)request.getParameter("payStatus");
        String paySerialNo = "-1";//(String)request.getParameter("paySerialNo");
        String payAccount = "-1";//(String)request.getParameter("payAccount");

        param.put("orderStatus",orderStatus);
        param.put("payStatus",payStatus);
        param.put("paySerialNo",paySerialNo);
        param.put("payAccount",payAccount);

        param = orderService.sumbitOrder(param);
        int i= (Integer) param.get("resultStatus");
        if(i==1){
            if(logger.isDebugEnabled()){
                logger.debug("------开始发送消息--------");
            }
            orderMessageUtil.sendOrderSaveSuccessMessage(param);
            map.put("status","success");
        }else{
            map.put("status","fail");
        }

        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }

    @RequestMapping("/sumbitOrder")
    @ResponseBody
    public String sumbitOrder(HttpServletRequest request, HttpServletResponse response){
        String ret = "";
        Map map = new HashMap();
        Map param =organizeRequest(request);

        //orderStatus 三种 草稿draft 已提交sumbit 已支付pay 已完成complate
        String orderStatus = "sumbit";//(String)request.getParameter("orderStatus");
        String payStatus = "0";//(String)request.getParameter("payStatus");
        String paySerialNo = "-1";//(String)request.getParameter("paySerialNo");
        String payAccount = "-1";//(String)request.getParameter("payAccount");

        param.put("orderStatus",orderStatus);
        param.put("payStatus",payStatus);
        param.put("paySerialNo",paySerialNo);
        param.put("payAccount",payAccount);

        param = orderService.sumbitOrder(param);
        int i= (Integer) param.get("resultStatus");
        if(i==1){
            if(logger.isDebugEnabled()){
                logger.debug("------开始发送消息--------");
            }
            orderMessageUtil.sendOrderSaveSuccessMessage(param);
            map.put("status","success");
        }else{
            map.put("status","fail");
        }
        map.put("status","success");

        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }


    public String updateStatus(HttpServletRequest request, HttpServletResponse response,@PathVariable("orderStatus") String orderStatus,@PathVariable("payStatus") String payStatus){
        String ret = "";
        Map map = new HashMap();
        String id = request.getParameter("id");
        Map param = new HashMap();
        param.put("id",id);
        //param.put("orderStatus","pay");
        //param.put("payStatus","1");
        param.put("orderStatus",orderStatus);
        param.put("payStatus",payStatus);
        param= orderService.updateOrderStatus(param);
        int i= (Integer) param.get("resultStatus");
        if(i==1){
            map.put("status","success");
        }else{
            map.put("status","fail");
        }
        map.put("status","success");

        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }
    @RequestMapping("/orderLst/{status}")
    @ResponseBody
    public String selectOrderByStatus(HttpServletRequest request, HttpServletResponse response,@PathVariable("status") String status){
        List<String> statusLst = new ArrayList<String>();
        if("noPay".equals(status)){
            statusLst.add("draft");
            statusLst.add("sumbit");
        }else if("pay".equals(status)){
            statusLst.add("pay");
            statusLst.add("offlinePay");
        }else if("all".equals(status)){
            statusLst.add("draft");
            statusLst.add("sumbit");
            statusLst.add("pay");
            statusLst.add("offlinePay");
            statusLst.add("complate");
        }else{
            return "";
        }
        String userId = request.getParameter("userId");
        if(userId == null || "".equals(userId)){
            Map userMap =UserUtil.getInstance().getLoginUserMap();
            if(userMap!=null && !userMap.isEmpty()){
                if(userMap.containsKey("userId")){
                    userId = (String) userMap.get("userId");
                }
            }
        }
        Map param = new HashMap();
        param.put("userId",userId);
        int start = Integer.parseInt(request.getParameter("start"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        param.put("orderStatusLst",statusLst);
        param.put("start",start);
        param.put("limit",limit);

        List<Map> orderLst = orderService.selectOrderByParm(param);
        String ret = DataConvertUtil.convertBeanToJson(orderLst);
        return ret;
    }
    @RequestMapping("/pay4OrderByWx")
    @ResponseBody
    public String pay4OrderByWx(HttpServletRequest request, HttpServletResponse response){
        BaseResult baseResult = new BaseResult();
        String userId = request.getParameter("userId");
        if(userId == null || "".equals(userId)){
            Map userMap =UserUtil.getInstance().getLoginUserMap();
            if(userMap!=null && !userMap.isEmpty()){
                if(userMap.containsKey("userId")){
                    userId = (String) userMap.get("userId");
                }
            }
        }
        String orderId = request.getParameter("orderId");
        Order order = orderService.selectOrderById(orderId);
        //判断当前订单是否是当前登录人的订单
        String price = order.getTotalPrice();
        Double priceDouble = Double.parseDouble(price);
        logger.debug("获取到的orderId"+orderId+"价格为："+priceDouble);
        if (priceDouble <= 0) // 防止抓包修改订单金额造成损失
        {
            baseResult.setState(-999);
            baseResult.setMsg("付款金额错误!");
            baseResult.setSuccess(false);
            String ret = DataConvertUtil.convertBeanToJson(baseResult);
            return ret;
        }
        try
        {
            SortedMap<String, String> parameters = WXPayUtil.getWXPrePayID(); // 获取预付单，此处已做封装，需要工具类

            parameters.put("body", "订单号-" + order.getOrderNo());

            parameters.put("spbill_create_ip", request.getRemoteAddr());
            parameters.put("out_trade_no", order.getOrderNo()); // 订单id这里我的订单id生成规则是订单id+时间
            parameters.put("total_fee", "1"); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
            // parameters.put("total_fee", order.getOrderAmount()*100+""); // 上线后，将此代码放开
            parameters.put("openid", userId);//JSAPI支付必须传openid
            // 设置签名
            String sign = WXPayUtil.generateSignature(parameters);
            parameters.put("sign", sign);
            // 封装请求参数结束
            String requestXML = WXPayUtil.mapToXml(parameters); // 获取xml结果
            logger.debug("封装请求参数是：" + requestXML);
            // 调用统一下单接口
            String result = WXPayUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST",
                    requestXML);
            logger.debug("调用统一下单接口：" + result);
            SortedMap<String, String> parMap = WXPayUtil.startWXPay(result);
            logger.debug("最终的map是：" + parMap.toString());
            if (parMap != null)
            {
                //orders.setWxPayOrderString(JSON.toJSONString(parMap));
                if(parMap!=null){
                    baseResult.setData(DataConvertUtil.convertMapToJson(parMap));
                }
            } else
            {
                baseResult.setState(-999);
                baseResult.setMsg("支付出现异常，请稍后重试!");
                baseResult.setSuccess(false);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            baseResult.setState(-999);
            baseResult.setMsg("程序异常!");
            baseResult.setSuccess(false);
            logger.error(e.getMessage());
        }
        String ret = DataConvertUtil.convertBeanToJson(baseResult);
        return ret;
    }
    /**
     * 微信异步通知
     */
    @SuppressWarnings("unchecked")
    //@ValidatePermission
    @RequestMapping("/wxPayNotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        logger.debug("微信支付成功回调开始");
        String result = WXPayUtil.reciverWx(request); // 接收到异步的参数
        logger.debug("微信支付成功回调参数"+result);
        Map<String, String> m = new HashMap<String, String>();// 解析xml成map
        if (m != null && !"".equals(m))
        {
            m = WXPayUtil.xmlToMap(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext())
        {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue)
            {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        logger.debug("微信支付成功验证码开始");
        if (WXPayUtil.isSignatureValid(packageParams))
        {
            if ("SUCCESS".equals((String) packageParams.get("return_code")))
            {

                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                // String transaction_id = (String)
                // packageParams.get("transaction_id"); // 微信支付订单号
                // 查询订单 根据订单号查询订单
                //String orderId = out_trade_no.substring(0, out_trade_no.length() - PayCommonUtil.TIME.length());
                logger.debug("微信支付成功验证码成功编号："+out_trade_no);
                Order orders = orderService.selectOrderByOrderNo(out_trade_no);//ordersMapper.selectByPrimaryKey(Integer.parseInt(orderId));

                // 验证商户ID 和 价格 以防止篡改金额
                if (WeChatUtil.getInstance().getMchid().equals(mch_id) && orders != null
                    // &&
                    // total_fee.trim().toString().equals(orders.getOrderAmount())
                    // // 实际项目中将此注释删掉，以保证支付金额相等
                        )
                {
                    /** 这里是我项目里的消费状态
                     * 1.待付款=0 2.付款完成=1
                     * 3.消费成功=2
                     * 4.取消=-1
                     * 5.发起退款=-2
                     * 6.退款成功=-3
                     * 7.退款失败=3（由于商户拒绝退款或其他原因导致退款失败）
                     */
                    //insertWxNotice(packageParams);
                    //orders.setPayStatus("1");
                    //orders.setPayAccount();
                    //orders.setPayWay("1"); // 变更支付方式为wx
                    //orders.setOrderState("1"); // 订单状态为已付款
                    Map map = new HashMap();
                    map.put("id",orders.getId());
                    map.put("orderStatus","pay");
                    map.put("payStatus","1");
                    orderService.updateOrderStatus(map);
                    //ordersMapper.updateByPrimaryKeySelective(orders); // 变更数据库中该订单状态
                    // ordersMapper.updatePayStatus(Integer.parseInt(orderId));
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else
                {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }
            } else // 如果微信返回支付失败，将错误信息返回给微信
            {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
            }
        } else
        {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }

        // 处理业务完毕，将业务结果通知给微信
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
