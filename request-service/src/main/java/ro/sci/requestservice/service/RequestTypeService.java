package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RequestTypeReq;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.mapper.RequestTypeMapper;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestTypeRepo;


@Service
@RequiredArgsConstructor
public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;
    private final RequestTypeMapper requestTypeMapper;

    @Transactional
    public RequestTypeResponse add(RequestTypeReq requestTypeReq) {
        RequestType requestType = new RequestType();
        requestType.setRequestName(requestTypeReq.getRequestName());
        return requestTypeMapper.map(requestTypeRepo.save(requestType));
    }


}
