package ro.sci.ticketweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.sci.ticketweb.service.DepartmentService;
import ro.sci.ticketweb.service.PoliceStructureService;
import ro.sci.ticketweb.service.RequestService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request/admin")
public class AdminWebController {

    private final RequestService requestService;
    private final PoliceStructureService policeStructureService;
    private final DepartmentService departmentService;

    @GetMapping
    public String indexPage(Model model) {
        return "admin";
    }

    @GetMapping("/open-tickets")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "index";
    }

    @GetMapping("/all-structures")
    public String viewAllStructures(Model model) {
        model.addAttribute("structures", policeStructureService.getAllStructures());
        return "structures";
    }

    @GetMapping("/show-departments/{policeStructureId}")
    public String viewDepartmentsForStructure(@PathVariable("policeStructureId") Long policeStructureId, Model model) {
        model.addAttribute("departments", departmentService.getByPoliceStructure(policeStructureId));
        return "departments";
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
