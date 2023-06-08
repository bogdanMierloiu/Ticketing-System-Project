package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.ItSpecialistResponse;
import ro.sci.requestservice.mapper.ItSpecialistMapper;
import ro.sci.requestservice.repository.ItSpecialistRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class ItSpecialistService {

    private final ItSpecialistRepo itSpecialistRepo;
    private final ItSpecialistMapper itSpecialistMapper;

    @Async
    public CompletableFuture<List<ItSpecialistResponse>> findAll() {
        return CompletableFuture.completedFuture(itSpecialistMapper.map(itSpecialistRepo.findAll()));
    }


}
