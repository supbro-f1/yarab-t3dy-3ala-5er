package com.aastmt.renovation.repository;

import com.aastmt.renovation.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

    List<Worker> findByAvailableTrue(); // check if worker is available

    List<Worker> findByProfession(String profession); // find by prof

    List<Worker> findAllByOrderByRatingDesc(); // by rating of ppl

    List<Worker> findByProfessionAndAvailableTrue(String profession); //  both

    Optional<Worker> findByName(String name); 
}
