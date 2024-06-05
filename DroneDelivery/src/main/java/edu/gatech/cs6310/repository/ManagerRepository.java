package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
