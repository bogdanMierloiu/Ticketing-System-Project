package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.mapper.RequestTypeMapper;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;
    private final RequestTypeMapper requestTypeMapper;


    public CompletableFuture<List<RequestTypeResponse>> findAll() {
        List<RequestType> requestTypes = requestTypeRepo.findAll();
        return CompletableFuture.completedFuture(requestTypeMapper.map(requestTypes));
    }

}
