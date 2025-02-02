package movieDbApp.business.concretes;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import movieDbApp.business.abstracts.UserService;
import movieDbApp.business.requests.UserRegisterRequests;
import movieDbApp.business.response.UserLoginResponse;
import movieDbApp.dataAccess.UserRepository;
import movieDbApp.entities.User;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class UserManager implements UserService{

	@Autowired
	private UserRepository repository;
	private PasswordEncoder passwordEncoder;
	


	@Override
	public ResponseEntity<?> login(UserLoginResponse loginResponse, HttpServletRequest request) {
	    try {
	        System.out.println("Login isteği alındı: " + loginResponse.getName());
	        
	        
	        User user = repository.findByName(loginResponse.getName());
	        
	        if (user != null && passwordEncoder.matches(loginResponse.getPassword(), user.getPassword())) {
	            System.out.println("Kullanıcı doğrulandı: " + user.getName());
	            
	            List<GrantedAuthority> authorities = Arrays.asList(
	                new SimpleGrantedAuthority("ROLE_USER")
	            );
	            
	            Authentication auth = new UsernamePasswordAuthenticationToken(
	                user.getName(), 
	                null, 
	                authorities
	            );
	            
	            SecurityContextHolder.getContext().setAuthentication(auth);
	            
	            HttpSession session = request.getSession(true);
	            session.setAttribute("user", user);
	            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	            session.setMaxInactiveInterval(3600); 
	            
	            System.out.println("Session oluşturuldu, ID: " + session.getId());
	            
	            return ResponseEntity.ok()
	                .header("Location", "/moviedb/home")
	                .body(Map.of(
	                    "success", true,
	                    "message", "Login successful",
	                    "redirectUrl", "/moviedb/home"
	                ));
	        } else {
	            System.out.println("Kullanıcı doğrulanamadı: " + loginResponse.getName());
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of(
	                    "success", false,
	                    "message", "Hatalı kullanıcı adı veya şifre!"
	                ));
	        }
	    } catch (Exception e) {
	        System.err.println("Login hatası: " + e.getMessage());
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of(
	                    "success", false,
	                    "message", "Giriş işlemi sırasında bir hata oluştu."
	                ));
	    }
	}

	
	@Override
	public boolean registerUser(UserRegisterRequests registerRequests, HttpServletRequest request) {
        if(repository.findByName(registerRequests.getName()) != null) {
            return false;
        }
        
        User user = new User();
        user.setName(registerRequests.getName());
        user.setPassword(passwordEncoder.encode(registerRequests.getPassword()));
        
        repository.save(user);
        
        
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        
        return true;
	}
	
	@Override
    public ResponseEntity<?> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  

        if (session != null && session.getAttribute("user") != null) {
            
            return ResponseEntity.ok("Kullanıcı doğrulandı");
        } else {
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Oturum bulunamadı");
        }
    }
	
}
