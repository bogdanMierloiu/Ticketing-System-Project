package ro.sci.requestweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestweb.dto.AccountRequest;
import ro.sci.requestweb.dto.PolicemanRequest;
import ro.sci.requestweb.service.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestWebController {

    private final RequestService requestService;
    private final RankService rankService;
    private final PoliceStructureService policeStructureService;
    private final RequestTypeService requestTypeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
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
    public String addStructure(@ModelAttribute AccountRequest accountRequest, Model model) {
        requestService.addRequest(accountRequest);
        model.addAttribute("requests", requestService.getAllRequests());
        return "index";
    }

    @GetMapping("/structure-chief-approve/{requestId}")
    public String structureChiefApprove(@PathVariable("requestId") Long requestId, Model model) {
        requestService.structureChiefApprove(requestId);
        model.addAttribute("requests", requestService.getAllRequests());
        return "index";
    }




}
