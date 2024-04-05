package com.gotruck.truckcategoryservice.repository;

import com.gotruck.truckcategoryservice.model.TruckCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TruckCategoryRepository extends JpaRepository<TruckCategory, Long> {

    // Find a TruckCategory object by its ID
    Optional<TruckCategory> findById(Long id);

    // Get all TruckCategory objects
    List<TruckCategory> findAll();

    // Delete a TruckCategory object by its ID
    void deleteById(Long id);
}
