package org.doit.member.controller;

import org.doit.member.model.User;
import org.doit.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String adminIndex(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUserId())) {
            return "redirect:/login";
        }
        
        return "adminpage/adminindex";
    }
    
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(@RequestParam(defaultValue = "") String keyword,
                          @RequestParam(defaultValue = "") String loginType,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size,
                          HttpSession session,
                          Model model) {
        
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getUserId())) {
            return "redirect:/login";
        }
        
        List<User> users = userService.searchUsers(keyword, loginType, page, size);
        int totalUsers = userService.countUsers(keyword, loginType);
        int totalPages = (int) Math.ceil((double) totalUsers / size);
        
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("loginType", loginType);
        model.addAttribute("size", size);
        
        return "admin/users";
    }
}