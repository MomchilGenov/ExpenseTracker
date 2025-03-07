package com.momchilgenov.springboot.mvcweb.auth;

import com.momchilgenov.springboot.mvcweb.entity.User;
import com.momchilgenov.springboot.mvcweb.token.JwtAuthenticationToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        //todo - check if user is authenticated, if is authenticated - redirect to homepage(could enter url in search bar
        model.addAttribute("user", new User());
        System.out.println("In login controller");
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpServletResponse response) {
        System.out.println("in login POST method");
        //todo - check if user is authenticated, if is authenticated - redirect to homepage(could enter url in search bar)
        //test purposes jwt
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJJdmFuIEl2YW5vdiIsImV4cCI6MTc5MDc4NzIwMCwiaXNzIjoic2VydmljZWNvcmUiLCJhdWQiOiJtdmN3ZWIiLCJyb2xlcyI6WyJ1c2VyIiwiYWRtaW4iXX0.4Ju3e1gxcXxf0ZxKtA0tWwI9ff0Tj8shWagFGer22Ig";
        try {
            /*
             * calls the only available authentication provider(JwtAuthProvider)
             * that sends credentials to AuthenticationService
             * */
            JwtAuthenticationToken authentication =
                    (JwtAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            System.out.println("Backend returned access token = " + authentication.getTokenPair().accessToken().token());
            System.out.println("Backend returned refresh token = " + authentication.getTokenPair().refreshToken().token());

            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);


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
