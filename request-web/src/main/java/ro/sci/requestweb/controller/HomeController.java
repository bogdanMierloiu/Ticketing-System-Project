package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ro.sci.requestweb.dto.LoginRequest;
import ro.sci.requestweb.dto.UserInSession;
import ro.sci.requestweb.exception.UserNotInSessionException;
import ro.sci.requestweb.service.*;

import javax.naming.NamingException;

@Controller
@RequiredArgsConstructor
@SessionAttributes("sessionUser")
public class HomeController {

    private final AuthenticationService authService;
    @GetMapping("/")
    public String home(){
        return "login";
    }

    @PostMapping("/login-page")
    public String authenticate(@ModelAttribute LoginRequest userAd, Model model, HttpSession session) {
        try {
            UserInSession user = authService.authentication(userAd.getUsername(), userAd.getPassword());
            session.setAttribute("sessionUser", user);
            return "redirect:/request";
        } catch (javax.naming.AuthenticationException e) {
            model.addAttribute("errorMessage", "Username sau parola nu sunt valide!");
            return "login";
        } catch (NamingException e) {
            model.addAttribute("errorMessage", "A aparut o eroare in procesul autentificarii!");
            return "login";
        }
    }

    protected static UserInSession getUserSession(HttpSession session) throws UserNotInSessionException {
        UserInSession user = (UserInSession) session.getAttribute("sessionUser");
        if (user == null) {
            throw new UserNotInSessionException("Utilizatorul nu este autentificat");
        }
        return user;
    }
}
