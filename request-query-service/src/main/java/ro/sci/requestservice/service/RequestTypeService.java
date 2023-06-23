package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RequestTypeResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.RequestTypeMapper;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestTypeRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Transactional
public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;
    private final RequestTypeMapper requestTypeMapper;

    @Async
    public CompletableFuture<List<RequestTypeResponse>> findAll() {
        List<RequestType> requestTypes = requestTypeRepo.findAll();
        return CompletableFuture.completedFuture(requestTypeMapper.map(requestTypes));
    }

    @Async
    public CompletableFuture<RequestTypeResponse> findById(Long requestTypeId) {
        RequestType requestType = requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException("The request type with id " + requestTypeId + " not found!")
        );
        return CompletableFuture.completedFuture(requestTypeMapper.map(requestType));
    }

}
