import com.aastmt.renovation.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String RegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String RegisterForm(
        @RequestParam("fname") String fname,
        @RequestParam("lname") String lname,
        @RequestParam("username") String username,
        @RequestParam("profession") String profession,
        @RequestParam("role") Role role,
        @RequestParam("password") String password,
        @RequestParam("phonenumber") String phonenumber,
        HttpSession session
    ) {
        
        boolean isValid = authService.register(fname, lname, username, profession, role, password, phonenumber);
        
        if (isValid) {
            session.setAttribute("loggedInUser", username);
            session.setAttribute("userRole", role.name());
            
            if (role == Role.worker) {
                return "redirect:/worker-dashboard";
            } else if (role == Role.user) {
                return "redirect:/user-dashboard";
            }
        }
        
        return "register"; 
    }
    
    @GetMapping("/login")
    public String LoginPage() {
        return "login"; 
    }

    @PostMapping("/login")
    public String LoginForm(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("role") Role role,
        HttpSession session
    ) {
        boolean isValid = authService.login(username, password, role);
        
        if (isValid) {
            session.setAttribute("loggedInUser", username);
            session.setAttribute("userRole", role.name());
            
            if (role == Role.worker) {
                return "redirect:/worker-dashboard";
            } else if (username.equals("Maher") && password.equals("admin")) {
                return "redirect:/admin-dashboard";
            } else if (role == Role.user) {
                return "redirect:/user-dashboard";
            }
        }
        
      
        return "login"; 
    }
}