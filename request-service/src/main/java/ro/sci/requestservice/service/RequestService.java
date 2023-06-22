package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.AddRequestResult;
import ro.sci.requestservice.dto.RequestResponse;
import ro.sci.requestservice.exception.AlreadyHaveThisRequestException;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.exception.UnsupportedOperationException;
import ro.sci.requestservice.mapper.PolicemanMapper;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.*;
import ro.sci.requestservice.repository.ItSpecialistRepo;
import ro.sci.requestservice.repository.RequestRepo;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;


@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final RequestTypeRepo requestTypeRepo;
    private final RequestMapper requestMapper;
    private final PolicemanMapper policemanMapper;
    private final ItSpecialistRepo itSpecialistRepo;
    private final PolicemanService policemanService;
    private final CommitmentService commitmentService;


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy / HH:mm");

    @Async
    public CompletableFuture<AddRequestResult> add(AccountRequest accountRequest) throws AlreadyHaveThisRequestException {
        CompletableFuture<AddRequestResult> future = new CompletableFuture<>();
        try {
            Request request = requestMapper.map(accountRequest);
            CompletableFuture<Policeman> futurePoliceman = policemanService.add(accountRequest.getPolicemanRequest());
            Policeman policeman = futurePoliceman.join();
            hasAlreadyThisRequestType(accountRequest.getRequestTypeId(), policeman);

            request.setPoliceman(policeman);
            request.setStatus(Status.Deschisa);
            request.setObservation(accountRequest.getObservation());
            request.setRequestType(getRequestTypeById(accountRequest.getRequestTypeId()));
            request.setRequestStructRegNo(accountRequest.getRequestStructRegNo());
            request.setRegDateFromRequestStruct(accountRequest.getRegDateFromRequestStruct());
            request.setIsApprovedByStructureChief(false);
            request.setIsApprovedBySecurityStructure(false);
            request.setIsApprovedByITChief(false);

            // Commitment
            Commitment commitment = commitmentService.add(accountRequest.getCommitmentRequest());
            commitment.setDocumentName("Angajament " + policemanNameCompose(policeman) + " pt. " + request.getRequestType().getRequestName());
            commitment.setIsFromAdmin(false);

            request.setCommitment(commitment);

            request.setCreatedAt(LocalDateTime.now());
            String obvToAdd = "Solicitare creata la data de: " + request.getCreatedAt().format(dateTimeFormatter);
            request.setObservation(!StringUtils.isEmpty(accountRequest.getObservation()) ? request.getObservation() + "\n" + obvToAdd :
                    obvToAdd);

            Request savedRequest = requestRepo.save(request);

            policeman.getRequests().add(savedRequest);
            commitment.setRequest(savedRequest);

            RequestResponse requestResponse = requestMapper.mapWithRequestType(savedRequest);
            requestResponse.setPolicemanResponse(policemanMapper.mapPolicemanToResponse(policeman));


            future.complete(new AddRequestResult(true, null));
        } catch (Exception e) {
            future.complete(new AddRequestResult(false, e.getMessage()));
        }
        return future;
    }

    private Request findById(Long requestId) {
        return requestRepo.findById(requestId).orElseThrow(
                () -> new NotFoundException("The request with id " + requestId + " not found!")
        );
    }


    // Structure Chief Decisions

    public synchronized void structureChiefApprove(Long requestId) {
        Request requestToApprove = findById(requestId);
        requestToApprove.setIsApprovedByStructureChief(true);
        requestToApprove.setStatus(Status.In_lucru);
        requestToApprove.setStructureChiefAppAt(LocalDateTime.now());
        requestToApprove.setObservation(requestToApprove.getObservation() + "\n" +
                "Aprobat de seful structurii de politie emitente la data de " +
                requestToApprove.getStructureChiefAppAt().format(dateTimeFormatter));
        requestRepo.save(requestToApprove);
    }

    public synchronized void structureChiefReject(Long requestId, String observation) {
        Request requestToReject = findById(requestId);
        requestToReject.setIsApprovedByStructureChief(false);
        requestToReject.setStatus(Status.Respinsa);
        requestToReject.setObservation(requestToReject.getObservation() + "\n" +
                "Respinsa de sefulul structurii de politie emitente la data de " +
                LocalDateTime.now().format(dateTimeFormatter) + " din motivul: " + observation);
        requestRepo.save(requestToReject);
    }

    // Security Structure Decisions

    public synchronized void securityStructureApprove(Long requestId) throws UnsupportedOperationException {
        Request requestToApprove = findById(requestId);
        if (requestToApprove.getIsApprovedByStructureChief()) {
            requestToApprove.setIsApprovedBySecurityStructure(true);
            requestToApprove.setStatus(Status.In_lucru);
            requestToApprove.setSecurityStructAppAt(LocalDateTime.now());
            requestToApprove.setObservation(requestToApprove.getObservation() + "\n" +
                    "Aprobat de strucutra de securitate la data de " +
                    requestToApprove.getSecurityStructAppAt().format(dateTimeFormatter));
            requestRepo.save(requestToApprove);
        } else {
            throw new UnsupportedOperationException("The request is not approved by chief of police structure");
        }
    }

    public synchronized void securityStructureReject(Long requestId, String observation) throws
            UnsupportedOperationException {
        Request requestToReject = findById(requestId);
        if (requestToReject.getIsApprovedByStructureChief()) {
            requestToReject.setIsApprovedBySecurityStructure(false);
            requestToReject.setStatus(Status.Respinsa);
            requestToReject.setObservation(requestToReject.getObservation() + "\n" +
                    "Respinsa de structura de securitate la data de " +
                    LocalDateTime.now().format(dateTimeFormatter) + " din motivul: " + observation);
            requestRepo.save(requestToReject);
        } else {
            throw new UnsupportedOperationException("The request is not approved by chief of police structure");
        }
    }

    // IT STRUCTURE

    public synchronized void assignSpecialist(Long requestId, Long itSpecialistId) throws
            UnsupportedOperationException {
        Request requestToAssign = findById(requestId);
        ItSpecialist itSpecialistToAssign = getItSpecialistById(itSpecialistId);
        if (requestToAssign.getIsApprovedBySecurityStructure()) {
            requestToAssign.setItSpecialist(itSpecialistToAssign);
            requestToAssign.setIsApprovedByITChief(true);
            requestToAssign.setItChiefAppAt(LocalDateTime.now());
            requestToAssign.setStatus(Status.In_lucru);
            requestToAssign.setObservation(requestToAssign.getObservation() + "\n" +
                    "Aprobat de Serviciul Comunicatii si Informatica la data de " +
                    requestToAssign.getItChiefAppAt().format(dateTimeFormatter) + "\n"
                    + " si repartizata catre " +
                    requestToAssign.getItSpecialist().getLastName() + " " +
                    requestToAssign.getItSpecialist().getFirstName());
            requestToAssign.setItStructRegNo(NumberGeneratorSCI.getNextNumber());
            requestToAssign.setRegDateFromITStruct(LocalDate.now());
            requestRepo.save(requestToAssign);
        } else {
            throw new UnsupportedOperationException("The request is not approved by security structure");
        }
    }

    public synchronized void itReject(Long requestId, String observation) {
        Request requestToReject = findById(requestId);
        requestToReject.setIsApprovedByITChief(false);
        requestToReject.setStatus(Status.Respinsa);
        requestToReject.setObservation(requestToReject.getObservation() + "\n" +
                "Respinsa de Serviciul Comunicatii si Informatica la data de " +
                LocalDateTime.now().format(dateTimeFormatter) + " din motivul: " + observation);
        requestRepo.save(requestToReject);
    }

    // FINALIZE

    public synchronized void finalize(Long requestId) throws UnsupportedOperationException {
        Request requestToFinalize = findById(requestId);
        checkApprovalBeforeFinalize(requestToFinalize);
        requestToFinalize.setStatus(Status.Finalizata);
        requestToFinalize.setSolvedAt(LocalDateTime.now());
        requestToFinalize.setObservation(requestToFinalize.getObservation() + "\n" +
                "Finalizata la data de " + requestToFinalize.getSolvedAt().format(dateTimeFormatter) +
                " de catre " +
                requestToFinalize.getItSpecialist().getLastName() + " " +
                requestToFinalize.getItSpecialist().getFirstName());
        requestRepo.save(requestToFinalize);
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

    @Async
    private void hasAlreadyThisRequestType(Long requestTypeId, Policeman policeman) throws
            AlreadyHaveThisRequestException {
        RequestType requestTypeRequested = getRequestTypeById(requestTypeId);
        for (Request request : policeman.getRequests()) {
            if (request.getStatus() == Status.In_lucru || request.getStatus() == Status.Deschisa) {
                if (requestTypeRequested.equals(request.getRequestType())) {
                    throw new AlreadyHaveThisRequestException("Already have this request type in progress!");
                }
            }
        }
    }

    @Async
    private void checkApprovalBeforeFinalize(Request requestToCheck) throws UnsupportedOperationException {
        StringBuilder errorMessage = new StringBuilder("Solicitarea nu este aprobata de:");

        if (!requestToCheck.getIsApprovedByStructureChief()) {
            errorMessage.append(" Seful structurii de Politie emitente,");
        }
        if (!requestToCheck.getIsApprovedBySecurityStructure()) {
            errorMessage.append(" Structura de securitate,");
        }
        if (!requestToCheck.getIsApprovedByITChief()) {
            errorMessage.append(" Serviciul Comunicatii si Informatica,");
        }

        // Eliminăm ultima virgulă și adăugăm semnul de exclamare la final
        if (errorMessage.charAt(errorMessage.length() - 1) == ',') {
            errorMessage.deleteCharAt(errorMessage.length() - 1);
            errorMessage.append("!");
            throw new UnsupportedOperationException(errorMessage.toString());
        }
    }

    private String policemanNameCompose(Policeman policeman) {
        String firstNameSecondary = policeman.getFirstNameSecondary() != null ? policeman.getFirstNameSecondary() : "";
        return (policeman.getLastName() + " " + policeman.getFirstName() + " " + firstNameSecondary).trim();

    }


}
