package movieDbApp.webApiController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.UserService;
import movieDbApp.business.requests.UserRegisterRequests;
import movieDbApp.business.response.UserLoginResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/moviedb")
public class UserLoginController {
    
    private UserService service;  

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginResponse loginResponse, HttpServletRequest request) {
        
        return service.login(loginResponse, request);
    }

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserRegisterRequests registerRequests, HttpServletRequest request) {
        return service.registerUser(registerRequests, request);
    }

    
    @GetMapping("/checkSession")
    public ResponseEntity<?> checkSession(HttpServletRequest request) {
       
        return service.checkSession(request);
    }

    @PostMapping("/logout")
public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
    try {
        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID") || 
                    cookie.getName().equals("remember-me")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        return ResponseEntity.ok()
            .body(new HashMap<String, String>() {{
                put("message", "Başarıyla çıkış yapıldı");
                put("status", "success");
            }});

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new HashMap<String, String>() {{
                put("message", "Çıkış yapılırken bir hata oluştu");
                put("status", "error");
            }});
    }
}
}
