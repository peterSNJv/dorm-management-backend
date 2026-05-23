package com.dorm.service;

import com.dorm.domain.entity.Feedback;
import com.dorm.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public List<Feedback> findAll() {
        return repository.findAll();
    }

    public Feedback create(Feedback feedback) {
        return repository.save(feedback);
    }

    public List<Feedback> findByStudentNo(String studentNo) {
        return repository.findByStudentNoOrderByCreatedAtDesc(studentNo);
    }

    public Feedback findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Feedback reply(Long id, String reply) {
        Feedback f = findById(id);
        if (f != null) {
            f.setStatus("REPLIED");
            f.setReply(reply);
            return repository.save(f);
        }
        return null;
    }
}