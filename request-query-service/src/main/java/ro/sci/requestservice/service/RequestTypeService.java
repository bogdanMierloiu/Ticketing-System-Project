package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.mapper.RequestTypeMapper;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.util.List;


@Service
@RequiredArgsConstructor

public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;
    private final RequestTypeMapper requestTypeMapper;


    public List<RequestTypeResponse> findAll() {
        return requestTypeMapper.map(requestTypeRepo.findAll());
    }

}
