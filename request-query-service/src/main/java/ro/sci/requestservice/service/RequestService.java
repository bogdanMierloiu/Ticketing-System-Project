package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.AccountResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.PolicemanMapper;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.Policeman;
import ro.sci.requestservice.model.Request;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.model.Status;
import ro.sci.requestservice.repository.RequestRepo;
import ro.sci.requestservice.repository.RequestTypeRepo;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final RequestTypeRepo requestTypeRepo;
    private final PolicemanService policemanService;
    private final RequestMapper requestMapper;
    private final PolicemanMapper policemanMapper;

    @Transactional
    public AccountResponse add(AccountRequest accountRequest) {
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
        AccountResponse accountResponse = requestMapper.mapWithRequestType(savedRequest);
        accountResponse.setPolicemanResponse(policemanMapper.mapPolicemanToResponse(policeman));

        return accountResponse;
    }

    private RequestType getRequestTypeById(Long requestTypeId) {
        return requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException("The request type with id " + requestTypeId + " not found")
        );
    }


}
