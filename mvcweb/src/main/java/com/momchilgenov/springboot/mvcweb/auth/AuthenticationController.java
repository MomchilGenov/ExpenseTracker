package com.momchilgenov.springboot.mvcweb.auth;

import com.momchilgenov.springboot.mvcweb.entity.User;
import com.momchilgenov.springboot.mvcweb.token.JwtAuthenticationToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtAccessToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtRefreshToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;

    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        //if user is already authenticated and tries to access login page, redirect to homepage
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            System.out.println("User = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            return "redirect:/homepage";
        }
        model.addAttribute("user", new User());
        System.out.println("In login controller");
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, BindingResult result, HttpServletResponse response, Model model) {
        System.out.println("in login POST method");
        try {
            /*
             * calls the only available authentication provider(JwtAuthProvider)
             * that sends credentials to AuthenticationService
             * */
            JwtAuthenticationToken authentication =
                    (JwtAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtAccessToken accessToken = authentication.getTokenPair().accessToken();
            JwtRefreshToken refreshToken = authentication.getTokenPair().refreshToken();

            System.out.println("From controller - current authenticated user is " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            System.out.println("Backend returned access token = " + authentication.getTokenPair().accessToken().token());
            System.out.println("Backend returned refresh token = " + authentication.getTokenPair().refreshToken().token());

            Cookie accessTokenCookie = new Cookie("accessToken", accessToken.token());
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.token());
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            response.addCookie(refreshTokenCookie);


        } catch (AuthenticationException e) {
            System.out.println("Did not work");
            result.reject("error.usernameOrPassword", "Incorrect username or password");
            model.addAttribute("user", user);
            return "login";
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

    @PostMapping("/logout")
    public String logout() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authenticationService.logout(username);
        SecurityContextHolder.clearContext();
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/homepage";
        }
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result, HttpServletResponse response) {
        //todo - validate user input(password, username is normal, etc)
        UserRegistrationStatus userRegistrationStatus = authenticationService.register(user);
        if (userRegistrationStatus.usernameCollides()) {
            result.rejectValue("username", "error.username", "This username is already taken");
            return "registration";
        }
        JwtTokenPair tokenPair = userRegistrationStatus.tokenPair();
        JwtRefreshToken refreshToken = tokenPair.refreshToken();
        JwtAccessToken accessToken = tokenPair.accessToken();

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken.token());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.token());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        return "redirect:/homepage";
    }
}
