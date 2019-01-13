package com.pet.transport.core.order.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.service.IOrderService;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/ticket/orderaAdmin")
public class OrderAdminAction {
    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

    @Autowired
    @Qualifier("ticketPriceServiceImpl")
    ITicketPriceService ticketPriceServiceImpl;


    @RequestMapping(value = "/adminOrderSumbit", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView adminOrderSumbit(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("adminOrderSumbit");
        String ret = orderLstAdmin(request);
        mv.addObject("orderLst", ret);
        return mv;
    }
    @RequestMapping(value = "/orderLstAdmin")
    @ResponseBody
    public String orderLstAdmin(HttpServletRequest request){
        //String orderId = (String) request.getParameter("orderId");

        //TODO 判断当前用户是否是管理员

        List<String> statusLst = new ArrayList<String>();
        statusLst.add("sumbit");

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
        //TODO 判断状态是否正确 userID是否是管理员
        Order order = orderService.selectOrderById(orderId);
        mv.addObject("order", order);

        List orderPets = orderService.selectOrderPetByOrderId(orderId);
        mv.addObject("orderPets", orderPets);
        return mv;
    }


}
