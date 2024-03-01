package com.gotruck.shipperservice.repository;

import com.gotruck.shipperservice.model.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ComponentScan

public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findById(Long id);

  List<User> findByCompanyName(String companyName);

  boolean existsByEmail(String email);

  void deleteByEmail(String email);

//  Optional<User> findByRole(Role role);
}
