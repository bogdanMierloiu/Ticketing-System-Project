package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ro.sci.requestweb.dto.AccountRequest;
import ro.sci.requestweb.dto.ItSpecialistResponse;
import ro.sci.requestweb.dto.PolicemanRequest;
import ro.sci.requestweb.dto.RequestResponse;
import ro.sci.requestweb.exception.AlreadyHaveThisRequestException;
import ro.sci.requestweb.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("user")
@RequestMapping("/request")
public class RequestWebController {

    private final RequestService requestService;
    private final RankService rankService;
    private final PoliceStructureService policeStructureService;
    private final RequestTypeService requestTypeService;
    private final ItSpecialistService itSpecialistService;

    @GetMapping("/")
    public String indexPage(Model model, HttpSession session) {
        Flux<RequestResponse> requestsFlux = requestService.getAllRequests();
        List<RequestResponse> requests = requestsFlux.collectList().block();

        Flux<ItSpecialistResponse> specialistFlux = itSpecialistService.getAllSpecialists();
        List<ItSpecialistResponse> specialists = specialistFlux.collectList().block();

        model.addAttribute("requests", requests);
        model.addAttribute("specialists", specialists);

        // Obține utilizatorul din sesiune
        Object user = session.getAttribute("user");
        if (user != null) {
            // Preia informațiile despre utilizator și adaugă-le în model
            model.addAttribute("user", user);
        }

        return "index";
    }

    @GetMapping("/find/{requestId}")
    public String viewRequest(@PathVariable("requestId") Long requestId, Model model) {
        RequestResponse requestResponse = requestService.findById(requestId).block();
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
        try {
            requestService.addRequest(accountRequest);
            return "redirect:/request";
        } catch (AlreadyHaveThisRequestException exception) {
            model.addAttribute("accountRequest", accountRequest);
            model.addAttribute("policemanRequest", new PolicemanRequest());
            model.addAttribute("ranks", rankService.getAllRanks());
            model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
            model.addAttribute("errorMessage", "Pentru acest politist, exista deja o solicitare" +
                    " de acelasi tip in lucru. Va rugam asteptati solutionarea acesteia!");
            return "add-request";
        }
    }

    @GetMapping("/search-by-name")
    public String searchByName(@RequestParam String name, Model model) {
        Flux<RequestResponse> allRequestsByPolicemanName = requestService.getAllRequestsByPolicemanName(name.strip());
        model.addAttribute("requests", allRequestsByPolicemanName);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/show-requests-for-policeman/{policemanId}")
    public String searchByPolicemanId(@PathVariable("policemanId") Long policemanId, Model model) {
        Flux<RequestResponse> allRequestsByPolicemanId = requestService.getAllRequestsByPolicemanId(policemanId);
        model.addAttribute("requests", allRequestsByPolicemanId);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }


    @GetMapping("/show-requests-for-police-structure/{policeStructureId}")
    public String searchByPoliceStructure(@PathVariable("policeStructureId") Long policeStructureId, Model model) {
        Flux<RequestResponse> allRequestsByPoliceStructure = requestService.getAllRequestsByPoliceStructure(policeStructureId);
        model.addAttribute("requests", allRequestsByPoliceStructure);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/show-requests-for-police-subunit/{subunitId}")
    public String searchByPoliceSubunit(@PathVariable("subunitId") Long subunitId, Model model) {
        Flux<RequestResponse> allRequestsByPoliceSubunit = requestService.getAllRequestsByPoliceSubunit(subunitId);
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
