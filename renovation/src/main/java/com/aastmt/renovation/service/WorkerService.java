package com.aastmt.renovation.service;

import com.aastmt.renovation.model.Worker;
import com.aastmt.renovation.repository.WorkerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Worker getWorkerProfile(int id) {
        return workerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Worker not found with id: " + id));
    }

    public List<Worker> getAvailableWorkers() {
        return workerRepository.findByAvailableTrue();
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public List<Worker> getAvailableWorkersByProfession(String profession) {
        return workerRepository.findByProfessionAndAvailableTrue(profession);
    }

    public List<Worker> getWorkersSortedByRating() {
        return workerRepository.findAllByOrderByRatingDesc();
    }

    public Worker updateAvailability(int  workerId, boolean available) {
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new EntityNotFoundException("Worker not found with id: " + workerId));
        worker.setAvailable(available);
        return workerRepository.save(worker);
    }

    public Worker updateRating(int workerId, Double newRating) {
        if (newRating == null || newRating < 0.0 || newRating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");
        }
        Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new EntityNotFoundException("Worker not found with id: " + workerId));
        worker.setRating(newRating);
        return workerRepository.save(worker);
    }

    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public void deleteWorker( int workerId) {
        workerRepository.deleteById(workerId);
    }

    public Worker updateWorkerProfile(Worker updatedWorker) {
        if (!workerRepository.existsById(updatedWorker.getId())) {
            throw new EntityNotFoundException("Worker not found with id: " + updatedWorker.getId());
        }
        return workerRepository.save(updatedWorker);
    }
}
