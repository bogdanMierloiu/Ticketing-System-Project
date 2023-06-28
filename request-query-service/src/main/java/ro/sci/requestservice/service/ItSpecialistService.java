package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.mapper.ItSpecialistMapper;
import ro.sci.requestservice.model.Request;
import ro.sci.requestservice.repository.ItSpecialistRepo;
import ro.sci.requestservice.repository.RequestRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Transactional
public class ItSpecialistService {

    private final ItSpecialistRepo itSpecialistRepo;
    private final ItSpecialistMapper itSpecialistMapper;
    private final RequestRepo requestRepo;

    @Async
    public CompletableFuture<List<ItSpecialistResponse>> findAll() {
        return CompletableFuture.completedFuture(itSpecialistMapper.map(itSpecialistRepo.findAll()));
    }

    @Async
    public CompletableFuture<ItSpecialistResponse> findByName(String lastName) {
        return CompletableFuture.completedFuture(itSpecialistMapper.map(itSpecialistRepo.findByName(lastName)));
    }

    @Async
    public CompletableFuture<Long> countAllRequests(Long specialistId) {
        List<Request> requests = requestRepo.findAllByItSpecialistId(specialistId);
        return CompletableFuture.completedFuture((long) requests.size());
    }

    @Async
    public CompletableFuture<Long> findAllInProgressByItSpecialistId(Long specialistId) {
        List<Request> requests = requestRepo.findAllInProgressByItSpecialistId(specialistId);
        return CompletableFuture.completedFuture((long) requests.size());
    }

    @Async
    public CompletableFuture<Long> findAllFinalizedByItSpecialistId(Long specialistId) {
        List<Request> requests = requestRepo.findAllFinalizedByItSpecialistId(specialistId);
        return CompletableFuture.completedFuture((long) requests.size());
    }





}
