package com.pet.transport.uc.wechat.core.token;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.uc.user.util.UserUtil;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import com.pet.transport.uc.wechat.message.po.Image;
import com.pet.transport.uc.wechat.message.po.ImageMessage;
import com.pet.transport.uc.wechat.message.po.TextMessage;
import com.pet.transport.uc.wechat.message.util.MessageSendUtil;
import com.pet.transport.uc.wechat.message.util.MessageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = -7002640712248365625L;
	private Log logger = LogFactory.getLog(CoreServlet.class);
	private WeChatUtil weChatUtil = WeChatUtil.getInstance();
	
    /*
     * 1）、包括内容 服务器配置主要是当我们写好自己的接入微信开发平台的代码之后要配置的服务器和微信接入接口。 2）、服务器操作
     * 打开服务器的tomcat,将写好的代码放到webapps文件下。 3）、微信公众平台操作
     * 申请微信测试账号（直接用微信扫一扫即可以登录）：http:/
     * /mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login
     * 打开微信公众平台测试号，配置接口配置信息。配置如下 URL:http://ip/WeixinApiDemo/CoreServlet
     * Token:wgyscsf提交，配置成功和失败均会有提醒。 第二步：验证服务器地址的有效性
     * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，
     * GET请求携带四个参数：signature、timestamp、nonce、echostr
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。 若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，
     * 则接入生效， 成为开发者成功，否则接入失败。
     *
     * 加密/校验流程如下： 1. 将token、timestamp、nonce三个参数进行字典序排序 2.
     * 将三个参数字符串拼接成一个字符串进行sha1加密 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     */
    /*
     * 字典排序（lexicographical
     * order）是一种对于随机变量形成序列的排序方法。其方法是，按照字母顺序，或者数字小大顺序，由小到大的形成序列。
     */
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        validateUrl(req, resp);
	}
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("state");
        resp.setContentType("html/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        // 获取输出流
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(processRequest(req, resp));
        /*if (method != null && "V".equals(method)) {
            validateUrl(req, resp);
        } else if (method != null && "L".equals(method)) {

        } else if (method != null && "R".equals(method)) {
            regisiter(req, resp);
        } else {
            processRequest(req, resp);
        }*/
    }
 
    
    private void validateUrl(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
    	 // 设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("html/text;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        // 获取输出流
        PrintWriter printWriter = resp.getWriter();
 
        // 设置一个全局的token,开发者自己设置。api这样解释：Token可由开发者可以任意填写，
        // 用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
        String token = weChatUtil.getAppToken();
        // 根据api说明，获取上述四个参数
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        // 根据api所说的“加密/校验流程”进行接入。共计三步
 
        // 第一步:将token、timestamp、nonce三个参数进行字典序排序
        String[] parms = new String[] { token, timestamp, nonce };// 将需要字典序排列的字符串放到数组中
        Arrays.sort(parms);// 按照api要求进行字典序排序
        // 第二步:将三个参数字符串拼接成一个字符串进行sha1加密
        // 拼接字符串
        String parmsString = "";// 注意，此处不能=null。
        for (int i = 0; i < parms.length; i++) {
            parmsString += parms[i];
        }
        // sha1加密
        String mParms = null;// 加密后的结果
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(parmsString.getBytes());
        byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        mParms = hexString.toString();// 加密结果
 
        /*
         * api要求： 若确认此次GET请求来自微信服务器，请原样返回echostr参数内容， 则接入生效， 成为开发者成功，否则接入失败。
         */
        // 第三步： 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信接入成功。
        if (mParms.equals(signature)) {
            printWriter.write(echostr);
        } else {
            // 接入失败,不用回写
        }
    }


    public  String processRequest(HttpServletRequest request, HttpServletResponse resp) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！";
        try {
            // 调用parseXml方法解析请求消息
            Map requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = (String) requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = (String) requestMap.get("ToUserName");
            // 消息类型
            String msgType = (String) requestMap.get("MsgType");
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            logger.debug("msgType"+msgType );
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！";
                //判断是否是管理员
                // 文本消息内容
                String content =(String)  requestMap.get("Content");
                if(content != null &&"admin".equals(content.trim())){
                    Map map = UserUtil.getInstance().getUserMapByOpenId(fromUserName);
                    if(map.containsKey("isAdmin")){
                        String isAdmin =(String) map.get("isAdmin");
                        if(isAdmin != null &&("1".equals(isAdmin))){
                            respContent = "管理员，你好！ \n " +
                                    "收货管理：http://m.airgopet.com/pet/ticket/orderAdmin/adminOrderSumbit \n" +
                                    "承运/打印管理：http://m.airgopet.com/pet/ticket/orderAdmin/adminOrderPayed" ;
                        }
                    }
                }
                if(content != null &&"收货管理".equals(content.trim())){
                    Map map = UserUtil.getInstance().getUserMapByOpenId(fromUserName);
                    if(map.containsKey("isAdmin")){
                        String isAdmin =(String) map.get("isAdmin");
                        if(isAdmin != null &&("1".equals(isAdmin)||"2".equals(isAdmin))){
                            MessageSendUtil orderMessageUtil = MessageSendUtil.getInstance();
                            Map map1 = new HashMap();
                            map1.put("openid",fromUserName);
                            orderMessageUtil.sendShouhuoAdminMessage(map1);
                            respContent="请单击上方或下方通知进行收货信息维护";
                        }
                    }
                }
                if(content != null &&"打印管理".equals(content.trim())){
                    Map map = UserUtil.getInstance().getUserMapByOpenId(fromUserName);
                    if(map.containsKey("isAdmin")){
                        String isAdmin =(String) map.get("isAdmin");
                        if(isAdmin != null &&("1".equals(isAdmin)||"3".equals(isAdmin))){
                            MessageSendUtil orderMessageUtil = MessageSendUtil.getInstance();
                            Map map1 = new HashMap();
                            map1.put("openid",fromUserName);
                            orderMessageUtil.sendPrintAdminMessage(map1);
                            respContent="请单击上方或下方通知进行信息打印";
                        }
                    }
                }
                if(content != null &&"客服".equals(content.trim())){
                    respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！\n"
                            +"关注客服微信号【feigoukefu】或者拨打客服电话【17623498881】联系我们 \n"
                            +"扫一扫上方二维码直接联系客服";
                    String json = initImageMessage(fromUserName,toUserName);
                    MessageSendUtil.getInstance().sendKfMessage(json);
                    //respXml=initImageMessage(fromUserName,toUserName);
                }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！！";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = (String) requestMap.get("Event");
                logger.debug("eventType:"+eventType);
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！\n"
                            +"关注客服微信号【feigoukefu】或者拨打客服电话【17623498881】联系我们 \n"
                            +"扫一扫上方二维码直接联系客服";
                    String json = initImageMessage(fromUserName,toUserName);
                    MessageSendUtil.getInstance().sendKfMessage(json);
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件

                    //判断场景值  场景值为8888

                    String eventKey = (String) requestMap.get("EventKey");
                    //
                    if(eventKey.indexOf("qrscene_")>-1){
                        //关注后会进行提示 此处不再处理
                        respContent = "欢迎关注飞狗宠物出行，您可以点击 开始旅行-->下单 为爱宠开启旅程！！";
                    }else{
                        //获取scansrt
                        //在二维码表中查询 相关具体的推荐人
                        //int ceateTime = (Integer) requestMap.get("CreateTime");
                        //发送给当前人办理记录
                        respContent = "您正在进行二维码扫描 eventKey："+eventKey+" fromUserName： "+fromUserName
                                +" toUserName： "+toUserName;//+" ceateTime： "+ceateTime;
                        //url如何判断失效？

                        //记录表中 每次请求判断以下？

                        //使用以下模板进行 信息发送 pSbva3OD_BSjbOKIMh_d5UQwXuTn9Xy-PdygRGGiaVA
                        //改为跳转URL 跳转到
                    }


                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            // 设置文本消息的内容
            textMessage.setContent(respContent);
            if(respXml!=null){
                //同时发送文本以及图片消息
                respXml = MessageUtil.textMessageToXml(textMessage)+respXml;
            }else{
                // 将文本消息对象转换成xml
                respXml = MessageUtil.textMessageToXml(textMessage);
            }

            logger.debug("respXml"+respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    //初始化图片消息
    public  String initImageMessage(String toUserName,String fromUserName){
        String message="";
        //获取access_token
        String access_token =  weChatUtil.getAccessToken();
        //String path = "/home/images/qr4tuijian.jpg";//需替换图片路径
        //File file = new File(path);
        try{
            Image image = new Image();
            ImageMessage imageMessage = new ImageMessage();
            String mediaId = "11eF87TXNh9Nb-60QiSeg2SJss7JZVT4S6TlRK2kCjI";//MaterialUtil.uploadPermanentMaterial(access_token,file,"image","","");
            logger.debug("二维码素材上传成功，开始组装信息"+mediaId);
            image.setMedia_id(mediaId);
            imageMessage.setFromUserName(fromUserName);
            imageMessage.setToUserName(toUserName);
            imageMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
            imageMessage.setCreateTime(System.currentTimeMillis());
            imageMessage.setImage(image);
            //imageMessage.setMediaId(mediaId);
            //message = MessageUtil.imageMessageToXml(imageMessage);
            message = DataConvertUtil.convertBeanToJson(imageMessage);
            message = message.replaceAll("ToUserName","touser").replaceAll("MsgType","msgtype");
        }catch(Exception e){
            e.printStackTrace();
        }
        return message;
    }
    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return

    public  boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { weChatUtil.getAppToken(), timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr);
        weChatUtil.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = weChatUtil.byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }*/


}
