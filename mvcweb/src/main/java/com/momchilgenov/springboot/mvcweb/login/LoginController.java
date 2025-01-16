package com.momchilgenov.springboot.mvcweb.login;

import com.momchilgenov.springboot.mvcweb.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final String URL_OF_JWT_AUTHENTICATOR = "custom url";

    @Autowired
    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        System.out.println("In login controller");
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpServletResponse response) {
        System.out.println("in login POST method");
        //test purposes jwt
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.UJpLFJc3GLacvl2Y5v0k1NNkcJxUBMBL0_wqBaJoJ9I";
        try {
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));


        } catch (AuthenticationException e) {
            System.out.println("Did not work");
        }
        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String showHomepage() {
        System.out.println("In homepage");
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Current user is " + user);
        return "homepage";
    }

}
