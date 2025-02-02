package movieDbApp.business.abstracts;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import movieDbApp.business.requests.UserRegisterRequests;
import movieDbApp.business.response.UserLoginResponse;

public interface UserService {

	ResponseEntity<?> login(UserLoginResponse loginResponse,HttpServletRequest request);
	boolean registerUser(UserRegisterRequests registerRequests, HttpServletRequest request);
	ResponseEntity<?> checkSession(HttpServletRequest request);
}
