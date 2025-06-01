package com.example.session16.controller;

import com.example.session16.model.LoginUser;
import com.example.session16.model.User;
import com.example.session16.model.RoleUser;
import com.example.session16.service.auth.AuthServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AuthServiceImp authServiceImp;

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("loginUser", new LoginUser());
        return "login";
    }

    @PostMapping("register-save")
    public String registerSave(
            @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        authServiceImp.register(user);
        return "redirect:/login";
    }

    @PostMapping("loginSave")
    public String loginSave(@Valid @ModelAttribute("loginUser")LoginUser loginUser, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        User newUser = authServiceImp.login(loginUser.getUsername(), loginUser.getPassword());
        if (newUser == null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
            return "login";
        }
        session.setAttribute("loginUser", newUser);
        if (newUser.getRole() == RoleUser.ADMIN) {
            return "redirect:/admin";
        }else {
            return "redirect:/listBusTrip";
        }
    }


}
