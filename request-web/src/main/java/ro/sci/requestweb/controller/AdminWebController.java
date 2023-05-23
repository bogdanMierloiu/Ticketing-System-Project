package ro.sci.requestweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestweb.dto.*;
import ro.sci.requestweb.service.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request/admin")
public class AdminWebController {

    private final RequestService requestService;
    private final PoliceStructureService policeStructureService;
    private final PoliceStructureSubunitService policeStructureSubunitService;
    private final DepartmentService departmentService;
    private final RankService rankService;
    private final ItSpecialistService itSpecialistService;
    private final RequestTypeService requestTypeService;
    private final PolicemanService policemanService;

    @GetMapping
    public String indexPage(Model model) {
        return "admin";
    }

    @GetMapping("/open-tickets")
    public String getAllRequests(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        return "index";
    }

    // --------------------------------------  STRUCTURES ------------------------------------------------------

    @GetMapping("/all-structures")
    public String viewAllStructures(Model model) {
        model.addAttribute("structures", policeStructureService.getAllStructures());
        return "structures";
    }

    @GetMapping("/add-structure-form")
    public String addStructureForm() {
        return "add-structure";
    }

    @PostMapping("/add-structure")
    public String addStructure(@ModelAttribute PoliceStructureRequest policeStructureRequest, Model model) {
        policeStructureService.addPoliceStructure(policeStructureRequest);
        model.addAttribute("structures", policeStructureService.getAllStructures());
        return "structures";
    }

    // --------------------------------------  SUBUNITS ------------------------------------------------------

    @GetMapping("/show-subunits/{policeStructureId}")
    public String showSubunitsForStructure(@PathVariable("policeStructureId") Long policeStructureId, Model model) {
        PoliceStructureSubunitResponse[] subunits = policeStructureSubunitService.getStructuresByPoliceStation(policeStructureId);
        model.addAttribute("subunits", subunits);
        model.addAttribute("structure", policeStructureService.getById(policeStructureId));
        return "subunits";
    }

    @GetMapping("/add-subunit-form/{structureId}")
    public String addSubunitForm(@PathVariable("structureId") Long structureId, Model model) {
        model.addAttribute("structure", policeStructureService.getById(structureId));
        return "add-subunit";
    }

    @PostMapping("/add-subunit")
    public String addSubunit(@ModelAttribute PoliceStructureSubunitRequest structureRequest, Model model) {
        policeStructureSubunitService.addSubunitStructure(structureRequest);
        model.addAttribute("subunits", policeStructureSubunitService.getStructuresByPoliceStation(structureRequest.getPoliceStructureId()));
        model.addAttribute("structure", policeStructureService.getById(structureRequest.getPoliceStructureId()));
        return "subunits";
    }

    // --------------------------------------  DEPARTMENTS ------------------------------------------------------

    @GetMapping("/show-departments/{subunitId}")
    public String viewDepartmentsForSubunit(@PathVariable("subunitId") Long subunitId, Model model) {
        model.addAttribute("subunit", policeStructureSubunitService.findById(subunitId));
        model.addAttribute("departments", departmentService.getBySubunit(subunitId));
        return "departments";
    }

    @GetMapping("/add-department-form/{subunitId}")
    public String addDepartmentForm(@PathVariable("subunitId") Long subunitId, Model model) {
        model.addAttribute("departmentRequest", new DepartmentRequest());
        model.addAttribute("subunit", policeStructureSubunitService.findById(subunitId));
        return "add-department";
    }

    @PostMapping("/add-department")
    public String addDepartment(@ModelAttribute DepartmentRequest departmentRequest, Model model) {
        departmentService.addDepartment(departmentRequest);
        model.addAttribute("subunit", policeStructureSubunitService.findById(departmentRequest.getPoliceStructureSubunitId()));
        model.addAttribute("departments", departmentService.getBySubunit(departmentRequest.getPoliceStructureSubunitId()));
        return "departments";
    }

    // --------------------------------------  RANKS ------------------------------------------------------

    @GetMapping("/all-ranks")
    public String viewAllRanks(Model model) {
        model.addAttribute("ranks", rankService.getAllRanks());
        return "ranks";
    }

    @GetMapping("/add-rank-form")
    public String addRankFrom() {
        return "add-rank";
    }

    @PostMapping("/add-rank")
    public String addRank(@ModelAttribute RankRequest request, Model model){
        rankService.addRank(request);
        model.addAttribute("ranks", rankService.getAllRanks());
        return "ranks";
    }

    // ----------------------------------  IT SPECIALISTS -------------------------------------------------


    @GetMapping("/all-specialists")
    public String viewAllSpecialists(Model model) {
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "it-specialists";
    }
    @GetMapping("/add-specialist-form")
    public String addSpecialistForm(Model model){
        model.addAttribute("ranks", rankService.getAllRanks());
        return "add-specialist-form";
    }

    @PostMapping("add-specialist")
    public String addSpecialist(@ModelAttribute ItSpecialistRequest request, Model model){
        itSpecialistService.addSpecialist(request);
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "it-specialists";
    }

    // ----------------------------------  REQUEST TYPES --------------------------------------------------

    @GetMapping("/all-request-type")
    public String viewAllRequestType(Model model) {
        model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
        return "request-types";
    }

    @GetMapping("/add-request-type-form")
    public String addRequestTypeForm() {
        return "add-request-type";
    }

    @PostMapping("/add-request-type")
    public String addRequestType(@ModelAttribute RequestTypeReq request, Model model){
        requestTypeService.addRequestType(request);
        model.addAttribute("requestTypes", requestTypeService.getAllRequestTypes());
        return "request-types";
    }

    // ----------------------------------  POLICEMEN ------------------------------------------------------

    @GetMapping("/all-policemen")
    public String viewAllPolicemen(Model model) {
        model.addAttribute("policemen", policemanService.getAllPolicemen());
        return "policemen";
    }

    // ------------------------------------ SCRIPT UTILS --------------------------------------------------


    @GetMapping("/show-subunits-script/{policeStructureId}")
    public ResponseEntity<List<PoliceStructureSubunitResponse>> viewSubunitsForStructure(@PathVariable("policeStructureId") Long policeStructureId) {
        PoliceStructureSubunitResponse[] subunits = policeStructureSubunitService.getStructuresByPoliceStation(policeStructureId);
        return ResponseEntity.ok(Arrays.asList(subunits));
    }

    @GetMapping("/show-departments-script/{subunitId}")
    public ResponseEntity<List<DepartmentResponse>> viewDepartmentsForStructureScript(@PathVariable("subunitId") Long subunitId) {
        DepartmentResponse[] departments = departmentService.getBySubunit(subunitId);
        return ResponseEntity.ok(Arrays.asList(departments));
    }


}
