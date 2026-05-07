package com.aastmt.renovation.service;

import com.aastmt.renovation.model.User;
import com.aastmt.renovation.model.Worker;
import com.aastmt.renovation.repository.UserRepository;
import com.aastmt.renovation.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerRepository workerRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }
}