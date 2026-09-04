package com.dragone.meta_flow.dto.goal;

import com.dragone.meta_flow.database.model.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalResponse(
        Integer id,
        String title,
        BigDecimal targetAmount,
        LocalDate startDate,
        LocalDate endDate,
        GoalStatus status
) {
}
