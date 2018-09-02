package com.pet.transport.core.ticket.common.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ticket")
public class TicketAction {

    @RequestMapping("/hkxsygz")
    @ResponseBody
    public ModelAndView index(){
        return new ModelAndView("pet/transport/ticket/hkx/hkxsygz");
    }

}
