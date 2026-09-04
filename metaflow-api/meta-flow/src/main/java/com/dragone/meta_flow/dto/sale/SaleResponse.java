package com.dragone.meta_flow.dto.sale;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaleResponse(

        Integer id,
        BigDecimal amount,
        LocalDate saleDate,
        String description,
        LocalDate createdAt,
        Integer userId,
        Integer goalId
) {
}
