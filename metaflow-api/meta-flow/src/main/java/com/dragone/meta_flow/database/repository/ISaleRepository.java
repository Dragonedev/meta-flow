package com.dragone.meta_flow.database.repository;

import com.dragone.meta_flow.database.model.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository<SaleEntity, Integer> {
}
