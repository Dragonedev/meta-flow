package com.dragone.meta_flow.dto.goal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalRequest(

        @NotBlank
        String title,

        @NotNull
        BigDecimal targetAmount,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate

) {
}
