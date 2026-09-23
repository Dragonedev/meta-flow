package com.dragone.meta_flow.service;

import com.dragone.meta_flow.database.model.GoalEntity;
import com.dragone.meta_flow.database.model.UserEntity;
import com.dragone.meta_flow.database.model.enums.GoalStatus;
import com.dragone.meta_flow.database.repository.IGoalRepository;
import com.dragone.meta_flow.database.repository.IUserRepository;
import com.dragone.meta_flow.dto.goal.GoalRequest;
import com.dragone.meta_flow.dto.goal.GoalResponse;
import com.dragone.meta_flow.exception.GoalAlreadyExistsException;
import com.dragone.meta_flow.exception.GoalNotFoundException;
import com.dragone.meta_flow.exception.GoalOperationNotAllowedException;
import com.dragone.meta_flow.exception.UserNotFoundException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final IGoalRepository goalRepository;
    private final IUserRepository userRepository;

    public GoalResponse createGoal(GoalRequest goalRequest){

        UserEntity user = userRepository.findById(goalRequest.userId())
                .orElseThrow(() ->
                        new UserNotFoundException("user not found"));

        if(goalRepository.existsByTitleAndUser(
                goalRequest.title(),
                goalRequest.userId())){

            throw new GoalAlreadyExistsException("goal already exists");
        }

        if(!goalRequest.startDate().isBefore(goalRequest.endDate())){
            throw new GoalOperationNotAllowedException(
                    "start date must be before end date");
        }

        if(goalRequest.endDate().isBefore(LocalDate.now())){
            throw new GoalOperationNotAllowedException(
                    "goal end date cannot be in the past");
        }

        GoalEntity goal = GoalEntity.builder()
                .title(goalRequest.title())
                .targetAmount(goalRequest.targetAmount())
                .startDate(goalRequest.startDate())
                .endDate(goalRequest.endDate())
                .status(GoalStatus.ACTIVE)
                .user(user)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public Page<GoalResponse> getGoals(Pageable pageable){
        return goalRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public GoalResponse getGoalById(Integer id){
        GoalEntity goal = goalRepository.findById(id)
                .orElseThrow(()-> new GoalNotFoundException("goal not found"));

        return toResponse(goal);
    }

    public GoalResponse updateGoal(GoalRequest goalRequest, Integer id) {

        GoalEntity goal = goalRepository.findById(id)
                .orElseThrow(() ->
                        new GoalNotFoundException("goal not found"));

        // 1. Encontrar o usuário
        UserEntity user = userRepository.findById(goalRequest.userId())
                .orElseThrow(() ->
                        new UserNotFoundException("user not found"));

        // 2. Verificar se existe outra meta com o mesmo título para esse usuário
        if (goalRepository.existsByTitleAndUserIdAndIdNot(goalRequest.title(), goalRequest.userId(), id)) {
            throw new GoalAlreadyExistsException("goal already exists");
        }

        // 3. Validar se a data inicial é anterior à data final
        if (!goalRequest.startDate().isBefore(goalRequest.endDate())) {
            throw new GoalOperationNotAllowedException(
                    "start date must be before end date");
        }

        // 4. Validar se a data final não está no passado
        if (goalRequest.endDate().isBefore(LocalDate.now())) {
            throw new GoalOperationNotAllowedException(
                    "goal end date cannot be in the past");
        }

        // 5. Atualizar a entidade existente
        goal.setTitle(goalRequest.title());
        goal.setTargetAmount(goalRequest.targetAmount());
        goal.setStartDate(goalRequest.startDate());
        goal.setEndDate(goalRequest.endDate());
        goal.setUpdatedAt(LocalDate.now());

        GoalEntity savedGoal = goalRepository.save(goal);

        return toResponse(savedGoal);
    }

    public void deleteGoal(Integer id) {

        GoalEntity goal = goalRepository.findById(id)
                .orElseThrow(() -> new GoalNotFoundException("goal bot found"));

        goalRepository.delete(goal);
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
