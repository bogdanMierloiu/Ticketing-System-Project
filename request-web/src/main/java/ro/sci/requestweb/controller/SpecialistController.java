package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ro.sci.requestweb.dto.ItSpecialistResponse;
import ro.sci.requestweb.dto.RequestResponse;
import ro.sci.requestweb.dto.UserInSession;
import ro.sci.requestweb.exception.NotAuthorizedForThisActionException;
import ro.sci.requestweb.service.ItSpecialistService;
import ro.sci.requestweb.service.RequestService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("sessionUser")
@RequestMapping("/specialist")
public class SpecialistController {

    private final ItSpecialistService itSpecialistService;
    private final RequestService requestService;

    @GetMapping("/account")
    public String specialistAccount(HttpSession session, Model model) {
        UserInSession userSession = HomeController.getUserSession(session);
        checkIsSpecialist(userSession.getMemberOf());
        ItSpecialistResponse specialist = getSpecialistFromSession(userSession.getDisplayName());
        model.addAttribute("specialist", specialist);
        model.addAttribute("totalRequests", itSpecialistService.countAllRequests(specialist.getId()));
        model.addAttribute("totalRequestsInProgress", itSpecialistService.countAllRequestsInProgress(specialist.getId()));
        model.addAttribute("totalRequestsFinalized", itSpecialistService.countAllRequestsFinalized(specialist.getId()));
        return "specialist";
    }

    @GetMapping("/show-all-requests/{specialistId}")
    public String searchBySpecialistId(@PathVariable("specialistId") Long specialistId, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        List<RequestResponse> allRequestsByItSpecialist = requestService.getAllRequestsByItSpecialist(specialistId);
        boolean isBureauChief = isBureauChief(userSession);
        model.addAttribute("isBureauChief", isBureauChief);
        model.addAttribute("userSession", userSession);
        model.addAttribute("requests", allRequestsByItSpecialist);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }



    //UTILS

    private void checkIsSpecialist(String memberOf) {
        if (!(memberOf.equals("lucrator"))) {
            throw new NotAuthorizedForThisActionException("User not authorized for this action");
        }
    }

    private ItSpecialistResponse getSpecialistFromSession(String displayName) {
        int indexOf = displayName.indexOf(" ");
        String lastName = displayName.substring(0, indexOf);
        return itSpecialistService.findByName(lastName);
    }

    private boolean isBureauChief(UserInSession user) {
        return user.getMemberOf().equals("sef_birou");
    }


}
