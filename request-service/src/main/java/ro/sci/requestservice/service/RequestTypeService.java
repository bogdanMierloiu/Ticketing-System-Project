package ro.sci.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.sci.requestservice.dto.RequestTypeReq;
import ro.sci.requestservice.exception.NotFoundException;
import ro.sci.requestservice.model.RequestType;
import ro.sci.requestservice.repository.RequestTypeRepo;


@Service
@RequiredArgsConstructor
@Transactional
public class RequestTypeService {

    private final RequestTypeRepo requestTypeRepo;

    public void add(RequestTypeReq requestTypeReq) {
        RequestType requestType = new RequestType();
        requestType.setRequestName(requestTypeReq.getRequestName());
        requestTypeRepo.save(requestType);
    }

    public void update(RequestTypeReq requestTypeReq) throws NotFoundException {
        RequestType requestTypeToUpdate = requestTypeRepo.findById(requestTypeReq.getId()).orElseThrow(
                () -> new NotFoundException(String.format("The specialist with  id %d not found", requestTypeReq.getId())));

        requestTypeToUpdate.setRequestName(requestTypeReq.getRequestName() != null ? requestTypeReq.getRequestName() : requestTypeToUpdate.getRequestName());
        requestTypeRepo.save(requestTypeToUpdate);
    }

    public void delete(Long requestTypeId) throws NotFoundException {
        RequestType requestTypeToDelete = requestTypeRepo.findById(requestTypeId).orElseThrow(
                () -> new NotFoundException(String.format("The specialist with  id %d not found", requestTypeId)));
        requestTypeRepo.delete(requestTypeToDelete);
    }



}
