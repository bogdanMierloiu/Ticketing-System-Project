package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.DepartmentResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.DepartmentMapper;
import ro.sci.requestservice.repository.DepartmentRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final DepartmentMapper departmentMapper;


    @Async
    public CompletableFuture<List<DepartmentResponse>> getBySubunitId(Long subunitId) {
        return CompletableFuture.completedFuture(departmentMapper.map(departmentRepo.findByPoliceStructureSubunitId(subunitId)));
    }

    @Async
    public CompletableFuture<DepartmentResponse> getById(Long departmentId) {
        return CompletableFuture.completedFuture(departmentMapper.map(departmentRepo.findById(departmentId)
                .orElseThrow(
                        () -> new NotFoundException("Deparment with id " + departmentId + " not found!")
                )));
    }

}

