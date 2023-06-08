package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.PolicemanResponse;
import ro.sci.requestservice.mapper.PolicemanMapper;
import ro.sci.requestservice.repository.PolicemanRepo;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class PolicemanService {

    private final PolicemanRepo policemanRepo;
    private final PolicemanMapper policemanMapper;

    @Async
    public CompletableFuture<List<PolicemanResponse>> findAll() {
        return CompletableFuture.completedFuture(policemanMapper.map(policemanRepo.findAll()));
    }


}
