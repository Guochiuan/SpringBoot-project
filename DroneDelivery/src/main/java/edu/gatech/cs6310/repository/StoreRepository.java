package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
