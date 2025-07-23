package org.doit.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if ("kakao".equals(logout)) {
            model.addAttribute("message", "카카오 로그아웃이 완료되었습니다.");
        }
        return "index";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}