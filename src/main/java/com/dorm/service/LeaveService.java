package com.dorm.service;

import com.dorm.domain.entity.LeaveRequest;
import com.dorm.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    private final LeaveRequestRepository repository;

    public LeaveService(LeaveRequestRepository repository) {
        this.repository = repository;
    }

    public List<LeaveRequest> findAll() {
        return repository.findAll();
    }

    public LeaveRequest create(LeaveRequest request) {
        return repository.save(request);
    }

    public List<LeaveRequest> findByStudentNo(String studentNo) {
        return repository.findByStudentNoOrderByCreatedAtDesc(studentNo);
    }

    public LeaveRequest findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public LeaveRequest updateStatus(Long id, String status, String note) {
        LeaveRequest r = findById(id);
        if (r != null) {
            r.setStatus(status);
            if (note != null) r.setProcessorNote(note);
            return repository.save(r);
        }
        return null;
    }
}