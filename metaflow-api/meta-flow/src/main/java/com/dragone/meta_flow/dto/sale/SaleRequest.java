package com.dragone.meta_flow.dto.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleRequest(

        @NotNull
        BigDecimal amount,

        @NotNull
        LocalDate saleDate,

        @NotBlank
        String description,

        @NotNull
        Integer goalId

) {
}
