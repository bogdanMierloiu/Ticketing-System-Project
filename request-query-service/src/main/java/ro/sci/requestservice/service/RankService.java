package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.repository.RankRepo;


@Service
@RequiredArgsConstructor
public class RankService {

    private final RankRepo rankRepo;



}
