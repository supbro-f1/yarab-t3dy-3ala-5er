package com.aastmt.renovation.controller; // Make sure your package is here!

import com.aastmt.renovation.dto.UserLoginDto;
import com.aastmt.renovation.dto.UserRegisterDto;
import com.aastmt.renovation.model.Role;
import com.aastmt.renovation.service.AuthService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String RegisterPage(Model model) {
      
        model.addAttribute("userDto", new UserRegisterDto());
        return "register"; 
    }

    @PostMapping("/register")
    public String RegisterForm(
        @Valid @ModelAttribute("userDto") UserRegisterDto userDto, 
        BindingResult bindingResult, 
        HttpSession session,
        Model model // 
    ) {
        
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        
        boolean isValid = authService.register(userDto);
        
        if (isValid) {
            session.setAttribute("loggedInUser", userDto.getUsername());
            session.setAttribute("userRole", userDto.getRole().name());
            
            if (userDto.getRole() == Role.worker) {
                return "redirect:/worker-dashboard";
            } else if (userDto.getRole() == Role.user) {
                return "redirect:/user-dashboard";
            }
        }
        
        
        model.addAttribute("registerError", "Username, Email, or Phone number is already in use.");
        return "register"; 
    }

    @GetMapping("/login")
    public String LoginPage(Model model) {
        model.addAttribute("loginDto", new UserLoginDto());
        return "login"; 
    }

    @PostMapping("/login")
    public String LoginForm(
        @Valid @ModelAttribute("loginDto") UserLoginDto loginDto,
        BindingResult bindingResult,
        HttpSession session,
        Model model
    ) {
  
        if (bindingResult.hasErrors()) {
            return "login"; 
        }

        
        boolean isValid = authService.login(loginDto);
        
        if (isValid) {
            session.setAttribute("loggedInUser", loginDto.getUsername());
            session.setAttribute("userRole", loginDto.getRole().name());
            
            if (loginDto.getUsername().equals("Maher") && loginDto.getPassword().equals("admin")) {
                return "redirect:/admin-dashboard";
            } else if (loginDto.getRole() == Role.worker) {
                return "redirect:/worker-dashboard";
            } else if (loginDto.getRole() == Role.user) {
                return "redirect:/user-dashboard";
            }
        }
        
        model.addAttribute("loginError", "Invalid username, password, or role combination.");
        return "login"; 
    }
}