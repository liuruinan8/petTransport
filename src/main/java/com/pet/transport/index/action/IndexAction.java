package com.pet.transport.index.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexAction {
    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index(){
        return new ModelAndView("index");
    }
    @RequestMapping("/index")
    @ResponseBody
    public ModelAndView indexWithIndex(){
        return new ModelAndView("index");
    }
}
