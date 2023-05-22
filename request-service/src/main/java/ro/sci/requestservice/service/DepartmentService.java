package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.DepartmentRequest;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.Department;
import ro.sci.requestservice.model.PoliceStructureSubunit;
import ro.sci.requestservice.repository.DepartmentRepo;
import ro.sci.requestservice.repository.PoliceStructureSubunitRepo;


@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final PoliceStructureSubunitRepo policeStructureSubunitRepoRepo;

    @Transactional
    public void add(DepartmentRequest departmentRequest) {
        Department departmentToSave = new Department();
        departmentToSave.setDepartmentName(departmentRequest.getDepartmentName());
        departmentToSave.setPoliceStructureSubunit(getPoliceStructureSubunitById(departmentRequest.getPoliceStructureSubunitId()));
        departmentRepo.save(departmentToSave);
    }




    private PoliceStructureSubunit getPoliceStructureSubunitById(Long id) {
        return policeStructureSubunitRepoRepo.findById(id).orElseThrow(() ->
                new NotFoundException("The police structure subunit with id:" + id + " not exists")
        );
    }

}
