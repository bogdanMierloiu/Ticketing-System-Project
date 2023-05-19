package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.DepartmentRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.Department;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.repository.DepartmentRepo;
import ro.sci.requestservice.repository.PoliceStructureRepo;


@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final PoliceStructureRepo policeStructureRepo;

    @Transactional
    public void add(DepartmentRequest departmentRequest) {
        Department departmentToSave = new Department();
        departmentToSave.setDepartmentName(departmentRequest.getDepartmentName());
        departmentToSave.setPoliceStructure(getPoliceStructureById(departmentRequest.getPoliceStructureId()));
        departmentRepo.save(departmentToSave);
    }




    private PoliceStructure getPoliceStructureById(Long id) {
        return policeStructureRepo.findById(id).orElseThrow(() ->
                new NotFoundException("The police structure with id:" + id + " not exists")
        );
    }

}
