package com.gotruck.shipperservice.repository;

import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);

  List<User> findByCompanyNameIgnoreCaseContaining(String companyName);

  List<User> findByAccountStatus(AccountStatus accountStatus);

  boolean existsByEmail(String email);

}
