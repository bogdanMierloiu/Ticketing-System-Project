package ro.sci.ticketweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.sci.ticketweb.service.RequestService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestWebController {

    private final RequestService requestService;

    @GetMapping
    public String indexPage(Model model) {
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
