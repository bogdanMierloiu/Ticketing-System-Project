package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestweb.dto.*;
import ro.sci.requestweb.service.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@SessionAttributes("user")
@RequestMapping("/request")
public class RequestWebController {

    private final RequestService requestService;
    private final RankService rankService;
    private final RequestTypeService requestTypeService;
    private final ItSpecialistService itSpecialistService;
    private final AuthenticationService authService;


    @GetMapping
    public String authentication() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute LoginRequest userAd, Model model, HttpSession session) {
        try {
            UserInSession user = authService.authentication(userAd.getUsername(), userAd.getPassword());
            session.setAttribute("sessionUser", user);
            model.addAttribute("sessionUser", user);
            model.addAttribute("requests", requestService.getAllRequests());
            model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
            return "index";
        } catch (javax.naming.AuthenticationException e) {
            model.addAttribute("errorMessage", "Username sau parola nu sunt valide!");
            return "login";
        }
    }

    @GetMapping("/index")
    public String indexPage(Model model, HttpSession session) {
        model.addAttribute("requests", requestService.getAllRequests());
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/find/{requestId}")
    public String viewRequest(@PathVariable("requestId") Long requestId, Model model) {
        RequestResponse requestResponse = requestService.findById(requestId);
        assert requestResponse != null;
        boolean isApproved = isFullyApproved(requestResponse);
        model.addAttribute("isApproved", isApproved);
        model.addAttribute("request", requestResponse);
        return "request-print";
    }

    @PostMapping("/finalize/{requestId}")
    public String finalizeRequest(@PathVariable("requestId") Long requestId, Model model) {
        try {
            requestService.finalize(requestId);
            model.addAttribute("request", requestService.findById(requestId));
            return "request-print";
        } catch (Exception exception) {
            model.addAttribute("request", requestService.findById(requestId));
            model.addAttribute("errorMessage", exception.getMessage());
            return "request-print";
        }
    }

    @GetMapping("/add-request-form")
    public String addRequestForm(Model model) {
        model.addAttribute("accountRequest", new AccountRequest());
        model.addAttribute("policemanRequest", new PolicemanRequest());
        model.addAttribute("ranks", rankService.getAllRanks());
        model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
        return "add-request";
    }

    @PostMapping("/add-request")
    public String addRequest(@ModelAttribute AccountRequest accountRequest, Model model) {
        CompletableFuture<AsyncResponse<Void>> asyncResponse = requestService.addRequest(accountRequest);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }

        if (response.getError() != null) {
            model.addAttribute("accountRequest", accountRequest);
            model.addAttribute("policemanRequest", new PolicemanRequest());
            model.addAttribute("ranks", rankService.getAllRanks());
            model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
            model.addAttribute("errorMessage", response.getError().getMessage());
            return "add-request";
        }

        return "redirect:/request";
    }


    @GetMapping("/search-by-name")
    public String searchByName(@RequestParam String name, Model model) {
        List<RequestResponse> allRequestsByPolicemanName = requestService.getAllRequestsByPolicemanName(name.strip());
        model.addAttribute("requests", allRequestsByPolicemanName);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/show-requests-for-policeman/{policemanId}")
    public String searchByPolicemanId(@PathVariable("policemanId") Long policemanId, Model model) {
        List<RequestResponse> allRequestsByPolicemanId = requestService.getAllRequestsByPolicemanId(policemanId);
        model.addAttribute("requests", allRequestsByPolicemanId);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }


    @GetMapping("/show-requests-for-police-structure/{policeStructureId}")
    public String searchByPoliceStructure(@PathVariable("policeStructureId") Long policeStructureId, Model model) {
        List<RequestResponse> allRequestsByPoliceStructure = requestService.getAllRequestsByPoliceStructure(policeStructureId);
        model.addAttribute("requests", allRequestsByPoliceStructure);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/show-requests-for-police-subunit/{subunitId}")
    public String searchByPoliceSubunit(@PathVariable("subunitId") Long subunitId, Model model) {
        List<RequestResponse> allRequestsByPoliceSubunit = requestService.getAllRequestsByPoliceSubunit(subunitId);
        model.addAttribute("requests", allRequestsByPoliceSubunit);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }


    // POLICE STRUCTURE
    @PostMapping("/structure-chief-decision/{requestId}")
    public String structureChiefDecision(@PathVariable("requestId") Long requestId,
                                         @RequestParam("decision") String decision,
                                         @RequestParam(value = "observation", required = false) String observation,
                                         Model model,
                                         HttpServletRequest request) {
        if ("approve".equals(decision)) {
            requestService.structureChiefApprove(requestId);
        } else if ("reject".equals(decision)) {
            requestService.structureChiefReject(requestId, observation);
        }
        return "redirect:" + getReferer(request);
    }

    // SECURITY STRUCTURE

    @PostMapping("/security-decision/{requestId}")
    public String securityDecision(@PathVariable("requestId") Long requestId,
                                   @RequestParam("decision") String decision,
                                   @RequestParam(value = "observation", required = false) String observation,
                                   Model model,
                                   HttpServletRequest request) {
        if ("approve".equals(decision)) {
            requestService.securityApprove(requestId);
        } else if ("reject".equals(decision)) {
            requestService.securityReject(requestId, observation);
        }
        return "redirect:" + getReferer(request);
    }


// IT STRUCTURE

    @PostMapping("/it-decision/{requestId}")
    public String itDecision(@PathVariable("requestId") Long requestId,
                             @RequestParam("decision") String decision,
                             @RequestParam("itSpecialistId") Long itSpecialistId,
                             @RequestParam(value = "observation", required = false) String observation,
                             Model model,
                             HttpServletRequest request) {
        if ("approve".equals(decision)) {
            requestService.itApprove(requestId, itSpecialistId);
        } else if ("reject".equals(decision)) {
            requestService.itReject(requestId, observation);
        }
        return "redirect:" + getReferer(request);
    }


    // UTILS
    private boolean isFullyApproved(RequestResponse requestResponse) {
        return requestResponse.getIsApprovedByStructureChief() &&
                requestResponse.getIsApprovedBySecurityStructure() &&
                requestResponse.getIsApprovedByITChief();
    }

    private String getReferer(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        return (referer != null) ? referer : "/";
    }

}
