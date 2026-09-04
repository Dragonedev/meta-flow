package com.dragone.meta_flow.dto.user;

import java.time.LocalDate;

public record UserResponse(
        Integer id,
        String name,
        String email,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
