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

    // UTILS
    private String convertToBase64(byte[] documentData) {
        return Base64.getEncoder().encodeToString(documentData);
    }


}
