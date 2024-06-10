package com.gotruck.truckcategoryservice.dao.repository;


import com.gotruck.truckcategoryservice.dao.entity.TruckName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TruckNameRepository extends JpaRepository<TruckName, Long> {

    // Find a TruckName object by its ID
    Optional<TruckName> findById(Long id);

    // Get all TruckName objects
    List<TruckName> findAll();

    // Delete a TruckName object by its ID
    void deleteById(Long id);

}
