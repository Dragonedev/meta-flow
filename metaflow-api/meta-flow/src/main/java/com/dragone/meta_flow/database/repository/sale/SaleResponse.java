package com.dragone.meta_flow.database.repository.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleResponse(

        Integer id,
        BigDecimal amount,
        LocalDate saleDate,
        String description,
        LocalDate cratedAt,
        Integer userId,
        Integer goalId
) {
}
