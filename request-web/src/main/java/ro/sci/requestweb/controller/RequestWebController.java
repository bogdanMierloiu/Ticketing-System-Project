package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.sci.requestweb.dto.AccountRequest;
import ro.sci.requestweb.dto.PolicemanRequest;
import ro.sci.requestweb.dto.RequestResponse;
import ro.sci.requestweb.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestWebController {

    private final RequestService requestService;
    private final RankService rankService;
    private final PoliceStructureService policeStructureService;
    private final RequestTypeService requestTypeService;
    private final ItSpecialistService itSpecialistService;

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/find/{requestId}")
    public String viewRequest(@PathVariable("requestId") Long requestId, Model model) {
        model.addAttribute("request", requestService.findById(requestId));
        return "request-print";
    }

    @GetMapping("/add-request-form")
    public String addRequestForm(Model model) {
        model.addAttribute("accountRequest", new AccountRequest());
        model.addAttribute("policemanRequest", new PolicemanRequest());
        model.addAttribute("ranks", rankService.getAllRanks());
        model.addAttribute("structures", policeStructureService.getAllStructures());
        model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
        return "add-request";
    }

    @PostMapping("/add-request")
    public RedirectView addStructure(@ModelAttribute AccountRequest accountRequest, Model model) {
        requestService.addRequest(accountRequest);
        return new RedirectView("/request");
    }

    @GetMapping("/search-by-name")
    public String searchByName(@RequestParam String name, Model model) {
        RequestResponse[] allRequestsByPolicemanName = requestService.getAllRequestsByPolicemanName(name.strip());
        model.addAttribute("requests", allRequestsByPolicemanName);
        return "requests-for-policeman";
    }

    @GetMapping("/show-requests-for-policeman/{policemanId}")
    public String searchByPolicemanId(@PathVariable("policemanId") Long policemanId, Model model) {
        RequestResponse[] allRequestsByPolicemanId = requestService.getAllRequestsByPolicemanId(policemanId);
        model.addAttribute("requests", allRequestsByPolicemanId);
        return "requests-for-policeman";
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
        String referer = request.getHeader("referer");
        model.addAttribute("requests", requestService.getAllRequests());
        return "redirect:" + referer;
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
        String referer = request.getHeader("referer");
        model.addAttribute("requests", requestService.getAllRequests());
        return "redirect:" + referer;
    }

    // IT STRUCTURE

    @PostMapping("/it-decision/{requestId}")
    public String securityDecision(@PathVariable("requestId") Long requestId,
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
        String referer = request.getHeader("referer");
        model.addAttribute("requests", requestService.getAllRequests());
        return "redirect:" + referer;
    }


}
