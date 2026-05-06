package com.aastmt.renovation.controller;

import com.aastmt.renovation.model.Worker;
import com.aastmt.renovation.service.WorkerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<List<Worker>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Worker>> getAvailableWorkers() {
        return ResponseEntity.ok(workerService.getAvailableWorkers());
    }

    @GetMapping("/available/{profession}")
    public ResponseEntity<List<Worker>> getAvailableWorkersByProfession(@PathVariable String profession) {
        return ResponseEntity.ok(workerService.getAvailableWorkersByProfession(profession));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Worker>> getTopRatedWorkers() {
        return ResponseEntity.ok(workerService.getWorkersSortedByRating());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkerProfile(@PathVariable Long id) {
        try {
            Worker worker = workerService.getWorkerProfile(id);
            return ResponseEntity.ok(worker);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Worker> createWorker(@RequestBody Worker worker) {
        Worker savedWorker = workerService.saveWorker(worker);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorker);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Worker> updateProfile(@PathVariable Long id, @RequestBody Worker updatedWorker) {
        try {
            updatedWorker.setId(id);
            Worker saved = workerService.updateWorkerProfile(updatedWorker);
            return ResponseEntity.ok(saved);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

