package com.dragone.meta_flow.database.repository;

import com.dragone.meta_flow.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
}
