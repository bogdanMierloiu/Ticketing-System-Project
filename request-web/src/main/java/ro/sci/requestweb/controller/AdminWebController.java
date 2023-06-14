package ro.sci.requestweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.sci.requestweb.dto.*;
import ro.sci.requestweb.service.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request/admin")
@CacheConfig(cacheManager = "CacheManager")
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
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "index";
    }


    // --------------------------------------  STRUCTURES ------------------------------------------------------

    @GetMapping("/all-structures")
    public String viewAllStructures(Model model) {
        model.addAttribute("structures", policeStructureService.getAllStructures());
        return "structures";
    }

    @GetMapping("/add-structure-form")
    public String addStructureForm(Model model) {
        return "add-structure";
    }


    @PostMapping("/add-structure")
    public String addStructure(@ModelAttribute PoliceStructureRequest policeStructureRequest, Model model) {
        CompletableFuture<AsyncResponse<Void>> asyncResponse = policeStructureService.addPoliceStructure(policeStructureRequest);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }
        if (response.getError() != null) {
            model.addAttribute("errorMessage", "A aparut o eroare in procesarea cererii dumneavoastra!");
            return "add-structure";
        }
        return "redirect:/request/admin/all-structures";

    }

    // --------------------------------------  SUBUNITS ------------------------------------------------------

    @GetMapping("/show-subunits/{policeStructureId}")
    public String showSubunitsForStructure(@PathVariable("policeStructureId") Long policeStructureId, Model model) {
        List<PoliceStructureSubunitResponse> subunits = policeStructureSubunitService.getStructuresByPoliceStation(policeStructureId);
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
    public String addSubunit(@ModelAttribute PoliceStructureSubunitRequest subunitRequest, Model model) {
        Long policeStructureId = subunitRequest.getPoliceStructureId();
        CompletableFuture<AsyncResponse<Void>> asyncResponse = policeStructureSubunitService.addSubunitStructure(subunitRequest);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }
        if (response.getError() != null) {
            model.addAttribute("structure", policeStructureService.getById(policeStructureId));
            model.addAttribute("errorMessage", "A aparut o problema in procesarea cererii dumnevoastra !!");
            return "add-subunit";
        }

        String redirectUrl = String.format("/request/admin/show-subunits/%d", policeStructureId);
        return "redirect:" + redirectUrl;
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
        Long subunitId = departmentRequest.getPoliceStructureSubunitId();
        CompletableFuture<AsyncResponse<Void>> asyncResponse = departmentService.addDepartment(departmentRequest);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }
        if (response.getError() != null) {
            model.addAttribute("departmentRequest", new DepartmentRequest());
            model.addAttribute("subunit", policeStructureSubunitService.findById(subunitId));
            model.addAttribute("errorMessage", "A aparut o problema in procesarea cererii dumnevoastra !!");
            return "add-department";
        }
        String redirectUrl = String.format("/request/admin/show-departments/%d", subunitId);
        return "redirect:" + redirectUrl;
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
    public String addRank(@ModelAttribute RankRequest request, Model model) {
        CompletableFuture<AsyncResponse<Void>> asyncResponse = rankService.addRank(request);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }
        if (response.getError() != null) {
            model.addAttribute("errorMessage", "A aparut o problema in procesarea cererii dumnevoastra !!");
            return "add-rank";
        }
        return "redirect:/request/admin/all-ranks";
    }


    // ----------------------------------  IT SPECIALISTS -------------------------------------------------


    @GetMapping("/all-specialists")
    public String viewAllSpecialists(Model model) {
        model.addAttribute("specialists", itSpecialistService.getAllSpecialists());
        return "it-specialists";
    }

    @GetMapping("/add-specialist-form")
    public String addSpecialistForm(Model model) {
        model.addAttribute("ranks", rankService.getAllRanks());
        return "add-specialist-form";
    }

    @PostMapping("add-specialist")
    public String addSpecialist(@ModelAttribute ItSpecialistRequest request, Model model) {
        CompletableFuture<AsyncResponse<Void>> asyncResponse = itSpecialistService.addSpecialist(request);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }
        if (response.getError() != null) {
            model.addAttribute("errorMessage", "A aparut o problema in procesarea cererii dumnevoastra !!");
            return "add-specialist-form";
        }
        return "redirect:/request/admin/all-specialists";
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
    public String addRequestType(@ModelAttribute RequestTypeReq request, Model model) {
        CompletableFuture<AsyncResponse<Void>> asyncResponse = requestTypeService.addRequestType(request);
        AsyncResponse<Void> response;
        try {
            response = asyncResponse.get();
        } catch (Exception e) {
            response = new AsyncResponse<>(null, e);
        }
        if (response.getError() != null) {
            model.addAttribute("errorMessage", "A aparut o problema in procesarea cererii dumnevoastra !!");
            return "add-request-type";
        }
        return "redirect:/request/admin/all-request-type";
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
        List<PoliceStructureSubunitResponse> subunits = policeStructureSubunitService.getStructuresByPoliceStation(policeStructureId);
        assert subunits != null;
        return ResponseEntity.ok(subunits);
    }

    @GetMapping("/show-departments-script/{subunitId}")
    public ResponseEntity<List<DepartmentResponse>> viewDepartmentsForStructureScript(@PathVariable("subunitId") Long subunitId) {
        List<DepartmentResponse> departments = departmentService.getBySubunit(subunitId);
        assert departments != null;
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/show-structures-script")
    public ResponseEntity<List<PoliceStructureResponse>> viewPoliceStructuresScript() {
        List<PoliceStructureResponse> structures = policeStructureService.getAllStructures();
        assert structures != null;
        return ResponseEntity.ok(structures);
    }


}
