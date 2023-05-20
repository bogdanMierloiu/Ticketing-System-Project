package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PolicemanMapper;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.Policeman;
import ro.sci.requestservice.model.Request;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.model.Status;
import ro.sci.requestservice.repository.RequestRepo;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final RequestTypeRepo requestTypeRepo;
    private final PolicemanService policemanService;
    private final RequestMapper requestMapper;
    private final PolicemanMapper policemanMapper;

    @Transactional
    public RequestResponse add(AccountRequest accountRequest) {
        Request request = requestMapper.map(accountRequest);
        Policeman policeman = policemanService.add(accountRequest.getPolicemanRequest());
        request.setPoliceman(policeman);
        request.setStatus(Status.OPEN);
        request.setObservation(accountRequest.getObservation());
        request.setRequestType(getRequestTypeById(accountRequest.getRequestTypeId()));
        request.setRequestStructRegNo(accountRequest.getRequestStructRegNo());
        request.setIsApprovedByStructureChief(false);
        request.setIsApprovedBySecurityStructure(false);
        request.setIsApprovedByITChief(false);

        Request savedRequest = requestRepo.save(request);
        RequestResponse requestResponse = requestMapper.mapWithRequestType(savedRequest);
        requestResponse.setPolicemanResponse(policemanMapper.mapPolicemanToResponse(policeman));

        return requestResponse;
    }

    private Request findById(Long requestId) {
        return requestRepo.findById(requestId).orElseThrow(
                () -> new NotFoundException("The request with id " + requestId + " not found!")
        );
    }

    public void structureChiefApprove(Long requestId) {
        Request requestToApprove = findById(requestId);
        requestToApprove.setIsApprovedByStructureChief(true);
        requestToApprove.setObservation("Aprobat de seful structurii de politie emitente la data de " +
                LocalDateTime.now());
        requestRepo.save(requestToApprove);
    }


    private RequestType getRequestTypeById(Long requestTypeId) {
        return requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException("The request type with id " + requestTypeId + " not found")
        );
    }

}
