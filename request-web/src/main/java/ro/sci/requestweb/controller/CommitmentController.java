package ro.sci.requestweb.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ro.sci.requestweb.dto.CommitmentResponse;
import ro.sci.requestweb.dto.UserInSession;
import ro.sci.requestweb.mapper.CommitmentRequestMapper;
import ro.sci.requestweb.service.CommitmentService;

import java.util.Base64;

@Controller
@RequiredArgsConstructor
@SessionAttributes("sessionUser")
@RequestMapping("/commitment")
public class CommitmentController {

    private final CommitmentService commitmentService;

    private final CommitmentRequestMapper commitmentRequestMapper;

    @GetMapping("/request/{requestId}")
    public String viewCommitment(@PathVariable("requestId") Long requestId, Model model, HttpSession session) {
        UserInSession userSession = HomeController.getUserSession(session);

        CommitmentResponse commitmentResponse = commitmentService.findByRequest(requestId);
        assert commitmentResponse != null;

        String document = convertToBase64(commitmentResponse.getDocumentData());

        model.addAttribute("userSession", userSession);
        model.addAttribute("commitment", commitmentResponse);
        model.addAttribute("document", document);
        return "commitment-print";
    }

//    @GetMapping("/show-commitment/{commitmentId}")
//    public String showCommitment(@PathVariable("commitmentId") Long commitmentId, Model model, HttpSession session) {
//        UserInSession userSession = HomeController.getUserSession(session);
//
//        CommitmentResponse commitmentResponse = commitmentService.getCommitmentById(commitmentId);
//        assert commitmentResponse != null;
//
//        String document = convertToBase64(commitmentResponse.getDocumentData());
//
//        model.addAttribute("userSession", userSession);
//        model.addAttribute("commitment", commitmentResponse);
//        model.addAttribute("document", document);
//        return "commitment-print";
//    }
//    @GetMapping("/admin")
//    public String viewCommitmentsPage(Model model, HttpSession session) {
//        UserInSession userSession = HomeController.getUserSession(session);
//        model.addAttribute("userSession", userSession);
//        model.addAttribute("commitments", commitmentService.getAllCommitmentsFromAdmin());
//        return "commitments";
//    }

//    @GetMapping("/add-commitment-form")
//    public String addStructureForm(Model model, HttpSession session) {
//        UserInSession userSession = HomeController.getUserSession(session);
//        model.addAttribute("userSession", userSession);
//        model.addAttribute("commitmentRequest", new CommitmentRequest());
//        return "add-commitment";
//    }

//    @PostMapping("/add-commitment")
//    public String addCommitment(@ModelAttribute CommitmentRequest commitmentRequest, Model model, HttpSession session) {
//        UserInSession userSession = HomeController.getUserSession(session);
//
//        CommitmentRequestToSend commitmentRequestToSend = commitmentRequestMapper.mapToCommitmentRequestToSend(commitmentRequest);
//        CompletableFuture<AsyncResponse<Void>> asyncResponse = commitmentService.addCommitment(commitmentRequestToSend);
//        AsyncResponse<Void> response;
//        try {
//            response = asyncResponse.get();
//        } catch (Exception e) {
//            response = new AsyncResponse<>(null, e);
//        }
//        if (response.getError() != null) {
//            model.addAttribute("userSession", userSession);
//            model.addAttribute("errorMessage", "A aparut o eroare in procesarea cererii dumneavoastra!");
//            return "add-commitment";
//        }
//        return "redirect:/commitment/admin";
//    }

//    @GetMapping("/update-commitment/{commitmentId}")
//    public String updateCommitmentForm(@PathVariable("commitmentId") Long commitmentId, Model model, HttpSession session) {
//        UserInSession userSession = HomeController.getUserSession(session);
//        model.addAttribute("commitment", commitmentService.getCommitmentById(commitmentId));
//        model.addAttribute("commitmentId", commitmentId);
//        model.addAttribute("userSession", userSession);
//        return "update-commitment";
//    }

//    @PostMapping("/update-commitment")
//    public String updateCommitment(@ModelAttribute CommitmentRequest commitmentRequest, Model model, HttpSession session) {
//        UserInSession userSession = HomeController.getUserSession(session);
//
//        CommitmentRequestToSend commitmentRequestToSend = commitmentRequestMapper.mapToCommitmentRequestToSend(commitmentRequest);
//        CompletableFuture<AsyncResponse<Void>> asyncResponse = commitmentService.updateCommitment(commitmentRequestToSend);
//        AsyncResponse<Void> response;
//        try {
//            response = asyncResponse.get();
//        } catch (Exception e) {
//            response = new AsyncResponse<>(null, e);
//        }
//        if (response.getError() != null) {
//            model.addAttribute("userSession", userSession);
//            model.addAttribute("errorMessage", "A aparut o eroare in procesarea cererii dumneavoastra!");
//            return "add-commitment";
//        }
//        return "redirect:/commitment/admin";
//    }


    // UTILS
    private String convertToBase64(byte[] documentData) {
        return Base64.getEncoder().encodeToString(documentData);
    }


}
