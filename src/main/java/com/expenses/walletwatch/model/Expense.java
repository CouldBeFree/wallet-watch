package com.expenses.walletwatch.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "expenses_category")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "expenses_category_name", nullable = false)
    private String expenses_category_name;
}
