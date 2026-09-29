package com.dragone.meta_flow.service;

import com.dragone.meta_flow.database.model.GoalEntity;
import com.dragone.meta_flow.database.model.SaleEntity;
import com.dragone.meta_flow.database.model.UserEntity;
import com.dragone.meta_flow.database.repository.IGoalRepository;
import com.dragone.meta_flow.database.repository.ISaleRepository;
import com.dragone.meta_flow.database.repository.IUserRepository;
import com.dragone.meta_flow.dto.sale.SaleRequest;
import com.dragone.meta_flow.dto.sale.SaleResponse;
import com.dragone.meta_flow.exception.GoalNotFoundException;
import com.dragone.meta_flow.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final ISaleRepository saleRepository;
    private final IGoalRepository goalRepository;
    private final IUserRepository userRepository;

    public SaleResponse createSale(SaleRequest saleRequest) {

        GoalEntity goal = goalRepository.findById(saleRequest.goalId())
                .orElseThrow(() -> new GoalNotFoundException("goal not found"));

        UserEntity user = userRepository.findById(saleRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if (saleRequest.saleDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("sale date cannot be in the future");
        }

        SaleEntity sale = SaleEntity.builder()
                .amount(saleRequest.amount())
                .saleDate(saleRequest.saleDate())
                .description(saleRequest.description())
                .createdAt(LocalDate.now())
                .user(user)
                .goal(goal)
                .build();

        SaleEntity savedSale = saleRepository.save(sale);

        return toResponse(savedSale);
    }

    private SaleResponse toResponse(SaleEntity sale){
        return new SaleResponse(
                sale.getId(),
                sale.getAmount(),
                sale.getSaleDate(),
                sale.getDescription(),
                sale.getCreatedAt(),
                sale.getUser().getId(),
                sale.getGoal().getId()
        );

    }
}
