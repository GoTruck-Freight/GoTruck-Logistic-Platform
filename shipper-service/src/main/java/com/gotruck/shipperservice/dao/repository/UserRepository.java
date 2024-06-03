package com.gotruck.shipperservice.dao.repository;

import com.gotruck.shipperservice.dao.entity.UserEntity;
import com.gotruck.shipperservice.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity,Long> {
  Optional<UserEntity> findByEmail(String email);

  List<UserEntity> findByCompanyNameIgnoreCaseContaining(String companyName);

  List<UserEntity> findByAccountStatus(AccountStatus accountStatus);

  boolean existsByEmail(String email);

}
