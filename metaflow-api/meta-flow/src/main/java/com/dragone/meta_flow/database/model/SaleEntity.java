package com.dragone.meta_flow.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sale")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "sale_date",nullable = false)
    private LocalDate saleDate;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private GoalEntity goal;


}
