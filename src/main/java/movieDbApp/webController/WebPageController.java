package movieDbApp.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moviedb")
public class WebPageController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; 
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }

    @GetMapping("/home")
    public String showHomePage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return "home"; // Kullanıcı giriş yapmışsa home sayfasına yönlendir
        }
        
        // Kullanıcı giriş yapmamışsa login sayfasına yönlendir
        redirectAttributes.addFlashAttribute("error", "Please login first");
        return "redirect:/moviedb/login";
    }
    
    @GetMapping("/management")
    public String showManagementPage(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return "management";
        }
        
        redirectAttributes.addFlashAttribute("error", "Please login first");
        return "redirect:/moviedb/login";
    }
}
