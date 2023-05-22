package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.sci.requestservice.dto.DepartmentResponse;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.mapper.DepartmentMapper;
import ro.sci.requestservice.model.PoliceStructure;
import ro.sci.requestservice.repository.DepartmentRepo;
import ro.sci.requestservice.repository.PoliceStructureRepo;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final PoliceStructureRepo policeStructureRepo;
    private final DepartmentMapper departmentMapper;


    public List<DepartmentResponse> getBySubunitId(Long subunitId) {
        return departmentMapper.map(departmentRepo.findByPoliceStructureSubunitId(subunitId));
    }


    private PoliceStructure getPoliceStructureById(Long id) {
        return policeStructureRepo.findById(id).orElseThrow(() ->
                new NotFoundException("The ticket with id:" + id + " not exists")
        );
    }

}
