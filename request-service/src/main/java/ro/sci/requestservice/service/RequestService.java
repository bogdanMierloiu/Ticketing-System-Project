package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.AccountRequest;
import ro.sci.requestservice.dto.AccountResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.RequestMapper;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.model.Policeman;
import ro.sci.requestservice.model.Request;
import ro.sci.requestservice.model.Status;
import ro.sci.requestservice.repository.PoliceStructureRepo;
import ro.sci.requestservice.repository.RequestRepo;


@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final PoliceStructureRepo policeStructureRepo;
    private final PolicemanService policemanService;
    private final RequestMapper requestMapper;

    @Transactional
    public AccountResponse add(AccountRequest accountRequest) {
        Request request = requestMapper.map(accountRequest);
        Policeman policeman = policemanService.add(accountRequest.getPolicemanRequest());
        request.setPoliceman(policeman);
        request.setStatus(Status.OPEN);
        request.setObservation(accountRequest.getObservation());
        request.setIsApprovedByStructureChief(false);
        request.setIsApprovedBySecurityStructure(false);
        request.setIsApprovedByITChief(false);

        return requestMapper.map(requestRepo.save(request));

    }


    private PoliceStructure getPoliceStructureById(Long id) {
        return policeStructureRepo.findById(id).orElseThrow(() ->
                new NotFoundException("The ticket with id:" + id + " not exists")
        );
    }

}
