package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RequestTypeReq;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestTypeRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;

    public void add(RequestTypeReq requestTypeReq) {
        RequestType requestType = new RequestType();
        requestType.setRequestName(requestTypeReq.getRequestName());
        requestTypeRepo.save(requestType);
    }


}
