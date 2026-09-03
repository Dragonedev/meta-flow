package com.dragone.meta_flow.database.model;

import com.dragone.meta_flow.database.model.enums.GoalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "goal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;

    @NotNull
    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status;

    @NotNull
    @Column(name = "created_at",nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "update_at",nullable = false)
    private LocalDate updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
