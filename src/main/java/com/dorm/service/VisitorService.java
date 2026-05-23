package com.dorm.service;

import com.dorm.domain.entity.VisitorRecord;
import com.dorm.repository.VisitorRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {

    private final VisitorRecordRepository repository;

    public VisitorService(VisitorRecordRepository repository) {
        this.repository = repository;
    }

    public List<VisitorRecord> findAll() {
        return repository.findAll();
    }

    public VisitorRecord create(VisitorRecord record) {
        return repository.save(record);
    }

    public List<VisitorRecord> findByStudentNo(String studentNo) {
        return repository.findByStudentNoOrderByCreatedAtDesc(studentNo);
    }

    public VisitorRecord findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public VisitorRecord updateStatus(Long id, String status) {
        return updateStatus(id, status, null);
    }

    public VisitorRecord updateStatus(Long id, String status, String note) {
        VisitorRecord r = findById(id);
        if (r != null) {
            r.setStatus(status);
            if (note != null) r.setNote(note);
            return repository.save(r);
        }
        return null;
    }
}