package com.gotruck.truckcategoryservice.repository;


import com.gotruck.truckcategoryservice.model.TruckName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TruckNameRepository extends JpaRepository<TruckName, UUID> {
    // Save a new TruckName object
    TruckName save(TruckName truckName);

    // Find a TruckName object by its ID
    Optional<TruckName> findById(UUID id);

    // Get all TruckName objects
    List<TruckName> findAll();

    // Delete a TruckName object by its ID
    void deleteById(UUID id);

}
