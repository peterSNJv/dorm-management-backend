package com.dorm.service;

import com.dorm.domain.entity.RepairRequest;
import com.dorm.repository.RepairRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairService {

    private final RepairRequestRepository repository;

    public RepairService(RepairRequestRepository repository) {
        this.repository = repository;
    }

    public List<RepairRequest> findAll() {
        return repository.findAll();
    }

    public RepairRequest create(RepairRequest request) {
        return repository.save(request);
    }

    public List<RepairRequest> findByStudentNo(String studentNo) {
        return repository.findByStudentNoOrderByCreatedAtDesc(studentNo);
    }

    public RepairRequest findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public RepairRequest updateStatus(Long id, String status, String note) {
        RepairRequest r = findById(id);
        if (r != null) {
            r.setStatus(status);
            if (note != null) r.setProcessorNote(note);
            return repository.save(r);
        }
        return null;
    }
}