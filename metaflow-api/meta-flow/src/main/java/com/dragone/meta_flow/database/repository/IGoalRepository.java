package com.dragone.meta_flow.database.repository;

import com.dragone.meta_flow.database.model.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGoalRepository extends JpaRepository<GoalEntity, Integer> {

    boolean existsByTitleAndUser(String title, Integer userId);
}
