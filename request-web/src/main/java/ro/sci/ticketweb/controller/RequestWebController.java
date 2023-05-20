package ro.sci.ticketweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.sci.ticketweb.dto.AccountRequest;
import ro.sci.ticketweb.dto.DepartmentResponse;
import ro.sci.ticketweb.dto.PoliceStructureRequest;
import ro.sci.ticketweb.dto.PolicemanRequest;
import ro.sci.ticketweb.service.*;

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
        model.addAttribute("requestTypes", requestService.getAllRequests());
        return "add-request";
    }

    @PostMapping("/add-request")
    public String addStructure(@ModelAttribute AccountRequest accountRequest, Model model) {
        requestService.addRequest(accountRequest);
        model.addAttribute("requests", requestService.getAllRequests());
        return "index";
    }




//    @GetMapping("/add-ticket-form")
//    public String ticketForm(Model model) {
//        return "add-ticket";
//    }
//
//    @PostMapping("/add")
//    public String addTicket(@ModelAttribute TicketRequest request, Model model) {
//        requestService.addTicket(request);
//        model.addAttribute("tickets", requestService.getAllTicketsNotResolved());
//        return "index";
//    }
//
//    @PostMapping("/view-ticket")
//    public String viewTicket(@RequestParam("id") Long ticketId, Model model) {
//        TicketResponse ticketResponse = requestService.getTicketById(ticketId);
//        model.addAttribute("ticket", ticketResponse);
//        return "ticket";
//    }
//
//    @GetMapping("/view-tickets-for-it-specialist")
//    public String viewTicketsForItSpecialist(@RequestParam("id") Long itSpecialistId, Model model) {
//        model.addAttribute("tickets", requestService.getTicketsByItSpecialistId(itSpecialistId));
//        return "tickets-for-itSpecialist";
//    }
//
//    @PostMapping("/assign-ticket")
//    public String assignTicket(@RequestParam("id") Long ticketId, Model model) {
//        AssignTicketRequest request = AssignTicketRequest.builder()
//                .ticketId(ticketId)
//                .itSpecialistsId(List.of(1L))
//                .build();
//        requestService.assignTicket(request);
//        model.addAttribute("tickets", requestService.getAllTicketsNotResolved());
//        return "index";
//    }


}
