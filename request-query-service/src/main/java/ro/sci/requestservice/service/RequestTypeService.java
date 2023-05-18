package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.mapper.RequestTypeMapper;
import ro.sci.requestservice.repository.RequestTypeRepo;


@Service
@RequiredArgsConstructor
public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;
    private final RequestTypeMapper requestTypeMapper;


}
