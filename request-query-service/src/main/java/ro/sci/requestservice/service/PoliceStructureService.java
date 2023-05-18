package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.repository.PoliceStructureRepo;


@Service
@RequiredArgsConstructor
public class PoliceStructureService {

    private final PoliceStructureRepo policeStructureRepo;


}
