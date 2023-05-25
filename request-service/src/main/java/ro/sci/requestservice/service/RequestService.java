package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PolicemanMapper;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.*;
import ro.sci.requestservice.repository.ItSpecialistRepo;
import ro.sci.requestservice.repository.RequestRepo;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final RequestTypeRepo requestTypeRepo;
    private final PolicemanService policemanService;
    private final RequestMapper requestMapper;
    private final PolicemanMapper policemanMapper;
    private final ItSpecialistRepo itSpecialistRepo;


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy / HH:mm");

    @Transactional
    public RequestResponse add(AccountRequest accountRequest) {
        Request request = requestMapper.map(accountRequest);
        Policeman policeman = policemanService.add(accountRequest.getPolicemanRequest());
        request.setPoliceman(policeman);
        request.setStatus(Status.Deschisa);
        request.setObservation(accountRequest.getObservation());
        request.setRequestType(getRequestTypeById(accountRequest.getRequestTypeId()));
        request.setRequestStructRegNo(accountRequest.getRequestStructRegNo());
        request.setRegDateFromRequestStruct(accountRequest.getRegDateFromRequestStruct());
        request.setIsApprovedByStructureChief(false);
        request.setIsApprovedBySecurityStructure(false);
        request.setIsApprovedByITChief(false);

        request.setCreatedAt(LocalDateTime.now());
        String obvToAdd = "Solicitare creata la data de: " + request.getCreatedAt().format(dateTimeFormatter);
        request.setObservation(!StringUtils.isEmpty(accountRequest.getObservation()) ? request.getObservation() + "\n" + obvToAdd :
                obvToAdd);

        Request savedRequest = requestRepo.save(request);
        policeman.getRequests().add(savedRequest);
        RequestResponse requestResponse = requestMapper.mapWithRequestType(savedRequest);
        requestResponse.setPolicemanResponse(policemanMapper.mapPolicemanToResponse(policeman));

        return requestResponse;
    }

    private Request findById(Long requestId) {
        return requestRepo.findById(requestId).orElseThrow(
                () -> new NotFoundException("The request with id " + requestId + " not found!")
        );
    }


    // Structure Chief Decisions
    public void structureChiefApprove(Long requestId) {
        Request requestToApprove = findById(requestId);
        requestToApprove.setIsApprovedByStructureChief(true);
        requestToApprove.setStatus(Status.In_lucru);
        requestToApprove.setStructureChiefAppAt(LocalDateTime.now());
        requestToApprove.setObservation(requestToApprove.getObservation() + "\n" +
                "Aprobat de seful structurii de politie emitente la data de " +
                requestToApprove.getStructureChiefAppAt().format(dateTimeFormatter));
        requestRepo.save(requestToApprove);
    }

    public void structureChiefReject(Long requestId, String observation) {
        Request requestToReject = findById(requestId);
        requestToReject.setIsApprovedByStructureChief(false);
        requestToReject.setStatus(Status.Respinsa);
        requestToReject.setObservation(requestToReject.getObservation() + "\n" +
                "Respinsa de sefulul structurii de politie emitente la data de " +
                LocalDateTime.now().format(dateTimeFormatter) + " din motivul: " + observation);
        requestRepo.save(requestToReject);
    }

    // Security Structure Decisions

    public void securityStructureApprove(Long requestId) {
        Request requestToApprove = findById(requestId);
        if (requestToApprove.getIsApprovedByStructureChief()) {
            requestToApprove.setIsApprovedBySecurityStructure(true);
            requestToApprove.setSecurityStructAppAt(LocalDateTime.now());
            requestToApprove.setObservation(requestToApprove.getObservation() + "\n" +
                    "Aprobat de strucutra de securitate la data de " +
                    requestToApprove.getSecurityStructAppAt().format(dateTimeFormatter));
            requestRepo.save(requestToApprove);
        } else {
            throw new UnsupportedOperationException("The request is not approved by chief of police structure");
        }
    }

    public void securityStructureReject(Long requestId, String observation) {
        Request requestToReject = findById(requestId);
        requestToReject.setIsApprovedBySecurityStructure(false);
        requestToReject.setStatus(Status.Respinsa);
        requestToReject.setObservation(requestToReject.getObservation() + "\n" +
                "Respinsa de structura de securitate la data de " +
                LocalDateTime.now().format(dateTimeFormatter) + " din motivul: " + observation);
        requestRepo.save(requestToReject);
    }

    // IT STRUCTURE

    public void assignSpecialist(Long requestId, Long itSpecialistId) {
        Request requestToAssign = findById(requestId);
        ItSpecialist itSpecialistToAssign = getItSpecialistById(itSpecialistId);
        if(requestToAssign.getIsApprovedBySecurityStructure()){
            requestToAssign.setItSpecialist(itSpecialistToAssign);
            requestRepo.save(requestToAssign);
        } else{
            throw new UnsupportedOperationException("The request is not approved by security structure");
        }
    }


    // UTILS

    private RequestType getRequestTypeById(Long requestTypeId) {
        return requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException("The request type with id " + requestTypeId + " not found")
        );
    }

    private ItSpecialist getItSpecialistById(Long itSpecialistId) {
        return itSpecialistRepo.findById(itSpecialistId).orElseThrow(
                () -> new NotFoundException("The IT Specialist with id " + itSpecialistId + " not found")
        );
    }

}
