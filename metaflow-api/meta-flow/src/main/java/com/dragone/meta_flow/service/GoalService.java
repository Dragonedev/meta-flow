package com.dragone.meta_flow.service;

import com.dragone.meta_flow.database.model.GoalEntity;
import com.dragone.meta_flow.database.model.UserEntity;
import com.dragone.meta_flow.database.model.enums.GoalStatus;
import com.dragone.meta_flow.database.repository.IGoalRepository;
import com.dragone.meta_flow.database.repository.IUserRepository;
import com.dragone.meta_flow.dto.goal.GoalRequest;
import com.dragone.meta_flow.dto.goal.GoalResponse;
import com.dragone.meta_flow.exception.GoalAlreadyExistsException;
import com.dragone.meta_flow.exception.GoalOperationNotAllowedException;
import com.dragone.meta_flow.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final IGoalRepository goalRepository;
    private final IUserRepository userRepository;

    public GoalResponse createGoal(GoalRequest goalRequest){
        // 1. Encontrar o usuário
        UserEntity user = userRepository.findById(goalRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        // 3. Verificar se já existe uma meta com o mesmo nome para esse usuário
        if(goalRepository.existsByTitleAndUser(goalRequest.title(), goalRequest.userId())){
            throw new GoalAlreadyExistsException("goal already exists");
        }
        // 4. Validar se a data inicial não é posterior à data final
        if(!goalRequest.startDate().isBefore(goalRequest.endDate())){
            throw new GoalOperationNotAllowedException("start date must be before end date");
        }
        // 5. Validar se a meta ainda é válida
        if(!goalRequest.endDate().isBefore(LocalDate.now())){
            throw new GoalOperationNotAllowedException("goal end date cannot be in the past");
        }
        // 6. Transformar DTO em Entity
        GoalEntity goal = GoalEntity.builder()
                .title(goalRequest.title())
                .targetAmount(goalRequest.targetAmount())
                .startDate(goalRequest.startDate())
                .endDate(goalRequest.endDate())
                .status(GoalStatus.ACTIVE)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    private GoalResponse toResponse(GoalEntity goal){
        return new GoalResponse(
                goal.getId(),
                goal.getTitle(),
                goal.getTargetAmount(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getStatus(),
                goal.getCreatedAt(),
                goal.getUpdatedAt(),
                goal.getUser().getId()
        );
    }
}
