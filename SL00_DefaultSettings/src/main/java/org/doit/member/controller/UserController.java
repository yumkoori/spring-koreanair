package org.doit.member.controller;

import org.doit.member.model.User;
import org.doit.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // 모달 방식에서는 GET 요청이 필요하지 않음 - 대시보드에서 직접 처리
    
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateProfile(@RequestParam String koreanName,
                              @RequestParam String englishName,
                              @RequestParam String birthDate,
                              @RequestParam String gender,
                              @RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam(required = false) String address,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        try {
            boolean success = userService.updateProfile(currentUser, koreanName, englishName, 
                                                       birthDate, gender, email, phone, address);
            
            if (success) {
                User updatedUser = userService.findByUserNo(currentUser.getUserNo());
                session.setAttribute("user", updatedUser);
                redirectAttributes.addFlashAttribute("message", "프로필이 성공적으로 업데이트되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "프로필 업데이트에 실패했습니다.");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/dashboard";
    }
    
    // 모달 방식에서는 GET 요청이 필요하지 않음 - 대시보드에서 직접 처리
    
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String updatePassword(@RequestParam String currentPassword,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        try {
            boolean success = userService.updatePassword(currentUser, currentPassword, 
                                                        newPassword, confirmPassword);
            
            if (success) {
                redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "비밀번호 변경에 실패했습니다.");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/dashboard";
    }
    
    // 모달 방식에서는 GET 요청이 필요하지 않음 - 대시보드에서 직접 처리
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAccount(@RequestParam(required = false) String confirmPassword,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        try {
            if ("kakao".equals(currentUser.getLoginType())) {
                boolean success = userService.deleteUser(currentUser.getUserNo());
                if (success) {
                    session.invalidate();
                    redirectAttributes.addFlashAttribute("message", "계정이 성공적으로 삭제되었습니다.");
                    return "redirect:/";
                }
            } else {
                if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
                    throw new IllegalArgumentException("비밀번호를 입력해주세요.");
                }
                
                User authenticatedUser = userService.authenticateUser(currentUser.getUserId(), confirmPassword);
                if (authenticatedUser == null) {
                    throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                }
                
                boolean success = userService.deleteUser(currentUser.getUserNo());
                if (success) {
                    session.invalidate();
                    redirectAttributes.addFlashAttribute("message", "계정이 성공적으로 삭제되었습니다.");
                    return "redirect:/";
                }
            }
            
            redirectAttributes.addFlashAttribute("error", "계정 삭제에 실패했습니다.");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/dashboard";
    }
}