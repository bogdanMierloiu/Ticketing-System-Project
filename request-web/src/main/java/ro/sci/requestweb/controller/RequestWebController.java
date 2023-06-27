package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestweb.dto.*;
import ro.sci.requestweb.exception.NotAuthorizedForThisActionException;
import ro.sci.requestweb.mapper.AccountRequestMapper;
import ro.sci.requestweb.service.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@SessionAttributes("sessionUser")
@RequestMapping("/request")
public class RequestWebController {

    private final RequestService requestService;
    private final RankService rankService;
    private final RequestTypeService requestTypeService;
    private final ItSpecialistService itSpecialistService;
    private final PoliceStructureService policeStructureService;
    private final PoliceStructureSubunitService subunitService;
    private final DepartmentService departmentService;
    private final AccountRequestMapper accountRequestMapper;


    @GetMapping
    public String indexPage(Model model, HttpSession session) {

        UserInSession userSession = HomeController.getUserSession(session);
        model.addAttribute("sessionUser", userSession);
        model.addAttribute("requests", requestService.getAllRequests());
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/find/{requestId}")
    public String viewRequest(@PathVariable("requestId") Long requestId, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);

        RequestResponse requestResponse = requestService.findById(requestId);
        assert requestResponse != null;
        boolean isApproved = isFullyApproved(requestResponse);

        model.addAttribute("sessionUser", userSession);
        model.addAttribute("isApproved", isApproved);
        model.addAttribute("request", requestResponse);
        model.addAttribute("requestName", requestNameForHeader(requestResponse.getRequestTypeResponse().getId()));
        return "request-print";
    }

    @PostMapping("/finalize/{requestId}")
    public String finalizeRequest(@PathVariable("requestId") Long requestId, Model model, HttpSession session) {

        UserInSession userSession = HomeController.getUserSession(session);
        RequestResponse request = requestService.findById(requestId);

        try {
            requestService.finalize(requestId);
            model.addAttribute("sessionUser", userSession);
            model.addAttribute("request", request);
            model.addAttribute("requestName", requestNameForHeader(request.getRequestTypeResponse().getId()));
            return "request-print";
        } catch (Exception exception) {
            model.addAttribute("sessionUser", userSession);
            model.addAttribute("request", requestService.findById(requestId));
            model.addAttribute("errorMessage", exception.getMessage());
            model.addAttribute("requestName", requestNameForHeader(request.getRequestTypeResponse().getId()));
            return "request-print";
        }
    }

    @GetMapping("/add-request-form")
    public String addRequestForm(Model model, HttpSession session) {

        UserInSession userSession = HomeController.getUserSession(session);
        AccountRequest accountRequest = new AccountRequest();

        session.setAttribute("accountRequest", accountRequest);
        model.addAttribute("accountRequest", accountRequest);
        model.addAttribute("userSession", userSession);
        model.addAttribute("policemanRequest", new PolicemanRequest());
        model.addAttribute("ranks", rankService.getAllRanks());
        model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
        return "add-request";
    }

    @GetMapping("/add-request-form-second")
    public String addRequestFormSecond(@ModelAttribute("accountRequest") AccountRequest accountRequest,
                                       HttpSession session,
                                       Model model) {
        UserInSession userSession = HomeController.getUserSession(session);
        session.setAttribute("accountRequest", accountRequest);
        model.addAttribute("structure", policeStructureService.getById(accountRequest.getPolicemanRequest().getPoliceStructureId()));
        model.addAttribute("subunit", subunitService.findById(accountRequest.getPolicemanRequest().getPoliceStructureSubunitId()));
        model.addAttribute("department", departmentService.getById(accountRequest.getPolicemanRequest().getDepartmentId()));
        model.addAttribute("requestTypeName", requestNameForHeader(accountRequest.getRequestTypeId()));
        model.addAttribute("rank", rankService.findById(accountRequest.getPolicemanRequest().getRankId()));
        model.addAttribute("userSession", userSession);
        model.addAttribute("accountRequest", accountRequest);
        return "add-request-second";
    }

    @PostMapping("/add-request")
    public String addRequest(@ModelAttribute("accountRequest") AccountRequest accountRequest, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);

        AccountRequestToSend accountRequestToSend = accountRequestMapper.mapToAccountRequestToSend(accountRequest);

        CompletableFuture<AsyncResponse<Void>> asyncResponse = requestService.addRequest(accountRequestToSend);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }

        if (response.getError() != null) {
            model.addAttribute("userSession", userSession);
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
    public String searchByName(@RequestParam String name, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        List<RequestResponse> allRequestsByPolicemanName = requestService.getAllRequestsByPolicemanName(name.strip());
        model.addAttribute("userSession", userSession);
        model.addAttribute("requests", allRequestsByPolicemanName);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/show-requests-for-policeman/{policemanId}")
    public String searchByPolicemanId(@PathVariable("policemanId") Long policemanId, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        List<RequestResponse> allRequestsByPolicemanId = requestService.getAllRequestsByPolicemanId(policemanId);
        model.addAttribute("userSession", userSession);
        model.addAttribute("requests", allRequestsByPolicemanId);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }


    @GetMapping("/show-requests-for-police-structure/{policeStructureId}")
    public String searchByPoliceStructure(@PathVariable("policeStructureId") Long policeStructureId, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        List<RequestResponse> allRequestsByPoliceStructure = requestService.getAllRequestsByPoliceStructure(policeStructureId);
        model.addAttribute("userSession", userSession);
        model.addAttribute("requests", allRequestsByPoliceStructure);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }

    @GetMapping("/show-requests-for-police-subunit/{subunitId}")
    public String searchByPoliceSubunit(@PathVariable("subunitId") Long subunitId, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        List<RequestResponse> allRequestsByPoliceSubunit = requestService.getAllRequestsByPoliceSubunit(subunitId);
        model.addAttribute("userSession", userSession);
        model.addAttribute("requests", allRequestsByPoliceSubunit);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }


    // POLICE STRUCTURE
    @PostMapping("/structure-chief-decision/{requestId}")
    public String structureChiefDecision(@PathVariable("requestId") Long requestId,
                                         @RequestParam("decision") String decision,
                                         @RequestParam(value = "observation", required = false) String observation,
                                         HttpServletRequest request,
                                         HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        checkIsPoliceStructureChief(userSession.getMemberOf());
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
                                   HttpServletRequest request, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        checkIsFromSecurityStructure(userSession.getMemberOf());
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
                             HttpServletRequest request, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);
        checkIsFromSCI(userSession.getMemberOf());
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


    private String requestNameForHeader(Long requestTypeId) {
        RequestTypeResponse requestType = requestTypeService.getById(requestTypeId);
        String requestName = requestType.getRequestName();
        int indexOfAccess = requestName.indexOf("acces");
        return requestName.substring(indexOfAccess + 5).trim();
    }

    private void checkIsPoliceStructureChief(String memberOf) {
        if (!(memberOf.equals("sef_structura"))) {
            throw new NotAuthorizedForThisActionException("User not authorized for this action");
        }
    }

    private void checkIsFromSecurityStructure(String memberOf) {
        if (!(memberOf.equals("structura_securitate"))) {
            throw new NotAuthorizedForThisActionException("User not authorized for this action");
        }
    }

    private void checkIsFromSCI(String memberOf) {
        if (!(memberOf.equals("admin"))) {
            throw new NotAuthorizedForThisActionException("User not authorized for this action");
        }
    }

}
