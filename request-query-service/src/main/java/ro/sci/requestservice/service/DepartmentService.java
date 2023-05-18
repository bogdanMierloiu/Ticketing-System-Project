package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.repository.DepartmentRepo;
import ro.sci.requestservice.repository.PoliceStructureRepo;


@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final PoliceStructureRepo policeStructureRepo;




    private PoliceStructure getPoliceStructureById(Long id) {
        return policeStructureRepo.findById(id).orElseThrow(() ->
                new NotFoundException("The ticket with id:" + id + " not exists")
        );
    }

}
