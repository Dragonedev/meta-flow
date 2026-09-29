package com.dragone.meta_flow.database.repository;

import com.dragone.meta_flow.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.openmbean.OpenMBeanInfo;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
