package com.pet.transport.uc.user.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/uc")
public class UserAction {

    public UserAction() {
    }



    @RequestMapping("/login")
    @ResponseBody
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("test1","test2");
        return mav;
    }
}
