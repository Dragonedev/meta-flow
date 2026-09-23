package com.dragone.meta_flow.controller;

import com.dragone.meta_flow.dto.goal.GoalRequest;
import com.dragone.meta_flow.dto.goal.GoalResponse;
import com.dragone.meta_flow.service.GoalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1/goal")
@RequiredArgsConstructor
@Validated
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse createGoal(@Valid @RequestBody GoalRequest goalRequest){
        return goalService.createGoal(goalRequest);
    }

    @GetMapping
    public Page<GoalResponse> getGoals(Pageable pageable){
        return goalService.getGoals(pageable);
    }

    @GetMapping("/{id}")
    public GoalResponse getGoalById(@PathVariable @Positive Integer id){
        return goalService.getGoalById(id);
    }

    @PutMapping("/{id}")
    public GoalResponse updateGoal(@Valid @RequestBody GoalRequest goalRequest, @PathVariable @Positive Integer id) {
        return goalService.updateGoal(goalRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGoal(@PathVariable @Positive Integer id) {
        goalService.deleteGoal(id);
    }




}
