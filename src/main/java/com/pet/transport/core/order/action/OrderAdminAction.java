package com.pet.transport.core.order.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.po.OrderTicket;
import com.pet.transport.core.order.service.IOrderService;
import com.pet.transport.core.order.util.OrderMessageUtil;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import com.pet.transport.uc.user.util.UserUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zimok
 * @Title: OrderAdminAction
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/1/139:54
 */
@Controller
@RequestMapping("/ticket/orderAdmin")
public class OrderAdminAction {
    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

    @Autowired
    @Qualifier("ticketPriceServiceImpl")
    ITicketPriceService ticketPriceServiceImpl;

    private OrderMessageUtil orderMessageUtil = OrderMessageUtil.getInstance();
    private Log logger = LogFactory.getLog(OrderAction.class);

    @RequestMapping(value = "/adminOrderSumbit", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView adminOrderSumbit(HttpServletRequest request){
        Map userMap =UserUtil.getInstance().getLoginUserMap();
        String isAdmin = "0";
        if(userMap!=null && !userMap.isEmpty()){
            if(userMap.containsKey("isAdmin")){
                isAdmin = (String) userMap.get("isAdmin");
            }
        }

        if(isAdmin.equals("0")){
            return null;
        }


        ModelAndView mv = new ModelAndView("adminOrderSumbit");
        String ret = orderLstAdmin(request,"sumbit");
        mv.addObject("orderLst", ret);
        return mv;
    }

    @RequestMapping(value = "/adminOrderPayed", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView adminOrderPayed(HttpServletRequest request){
        Map userMap =UserUtil.getInstance().getLoginUserMap();
        String isAdmin = "0";
        if(userMap!=null && !userMap.isEmpty()){
            if(userMap.containsKey("isAdmin")){
                isAdmin = (String) userMap.get("isAdmin");
            }
        }

        if(isAdmin.equals("0")){
            return null;
        }


        ModelAndView mv = new ModelAndView("payedOrder");
        String ret = orderLstAdmin(request,"pay");
        mv.addObject("orderLst", ret);
        return mv;
    }
    @RequestMapping(value = "/orderLstAdmin/{status}")
    @ResponseBody
    public String orderLstAdmin(HttpServletRequest request,@PathVariable("status") String status){
        //String orderId = (String) request.getParameter("orderId");

        //TODO 判断当前用户是否是管理员
        Map userMap =UserUtil.getInstance().getLoginUserMap();
        String isAdmin = "0";
        if(userMap!=null && !userMap.isEmpty()){
            if(userMap.containsKey("isAdmin")){
               isAdmin = (String) userMap.get("isAdmin");
            }
        }

        if(isAdmin.equals("0")){
            return "";
        }

        List<String> statusLst = new ArrayList<String>();
        //sumbit
        statusLst.add(status);

        Map param = new HashMap();
        //param.put("userId",userId);
        String startStr=request.getParameter("start");
        int start =0;
        if(startStr != null && !"".equals(startStr)){
            start = Integer.parseInt(startStr);
        }
        String limitStr=request.getParameter("limit");
        int limit =10;
        if(limitStr != null && !"".equals(limitStr)){
            limit = Integer.parseInt(limitStr);
        }
        String keyword = request.getParameter("keyword");
        param.put("orderStatusLst",statusLst);
        param.put("start",start);
        param.put("limit",limit);
        if(keyword != null && !"".equals(keyword)){
            param.put("orderNo",keyword);
        }


        List<Map> orderLst = orderService.selectOrderByParm(param);
        String ret = DataConvertUtil.convertBeanToJson(orderLst);
        return ret;
    }


    @RequestMapping(value = "/adminOrderDetailArrival", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView adminOrderDetailArrival(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("adminOrderArrival");
        String orderId = (String) request.getParameter("orderId");
        Order order = orderService.selectOrderById(orderId);
        Map userMap =UserUtil.getInstance().getLoginUserMap();
        String isAdmin = "0";
        if(userMap!=null && !userMap.isEmpty()){
            if(userMap.containsKey("isAdmin")){
                isAdmin = (String) userMap.get("isAdmin");
            }
        }

        if(isAdmin.equals("0")){
            mv.addObject("orderPets", null);
            //权限不足001
            mv.addObject("errorCode", "001");
            return mv;
        }
        String status = order.getOrderStatus();
        if(!"sumbit".equals(status)){
            mv.addObject("orderPets", null);
            //状态不对
            mv.addObject("errorCode", "101");
            return mv;
        }

        mv.addObject("order", order);
        mv.addObject("errorCode", "");
        List orderPets = orderService.selectOrderPetByOrderId(orderId);
        mv.addObject("orderPets", orderPets);
        return mv;
    }
    @RequestMapping(value = "/adminEditOrderTicket", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView adminEditOrderTicket(HttpServletRequest request){
        Map userMap =UserUtil.getInstance().getLoginUserMap();
        String isAdmin = "0";
        if(userMap!=null && !userMap.isEmpty()){
            if(userMap.containsKey("isAdmin")){
                isAdmin = (String) userMap.get("isAdmin");
            }
        }

        if(isAdmin.equals("0")){
            return null;
        }
        String orderId = request.getParameter("orderId");
        Order order = orderService.selectOrderById(orderId);
        OrderTicket orderTicket = orderService.selectOrderTicketByOrderId(orderId);
        ModelAndView mv = new ModelAndView("adminOrderTicket");

        mv.addObject("order", order);
        mv.addObject("orderTicket", orderTicket);
        return mv;
    }

    @RequestMapping("/sumbitAdminTicket")
    @ResponseBody
    public String sumbitAdminTicket(HttpServletRequest request, HttpServletResponse response){
        String ret = "";
        Map map = new HashMap();
        //获取提交的参数
        String orderId = (String) request.getParameter("orderId");
        String orderNo = (String) request.getParameter("orderNo");
        String orderTicketId = (String) request.getParameter("orderTicketId");
        String flight = (String) request.getParameter("flight");
        String flightNo = (String) request.getParameter("flightNo");
        String takeOffTime = (String) request.getParameter("takeOffTime");
        String arriveTime = (String) request.getParameter("arriveTime");
        Map param = new HashMap();
        param.put("orderId",orderId);
        param.put("orderNo",orderNo);
        param.put("orderTicketId",orderTicketId);
        param.put("flight",flight);
        param.put("flightNo",flightNo);
        param.put("takeOffTime",takeOffTime);
        param.put("arriveTime",arriveTime);
        param = orderService.sumbitAdminTicket(param);
        int i= (Integer) param.get("resultStatus");
        if(i==1){
            if(logger.isDebugEnabled()){
                logger.debug("------开始发送消息--------");
            }
            Order order = orderService.selectOrderById(orderId);
            param.put("userId",order.getUserId());
            param.put("startPlaceName",order.getStartPlaceName());
            param.put("destinationPlaceName",order.getDestinationPlaceName());
            orderMessageUtil.sendOrderTicketSuccessToUserMessage(param);
            map.put("status","success");
            map.put("orderId",orderId);
        }else{
            map.put("status","fail");
        }
        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }
}
