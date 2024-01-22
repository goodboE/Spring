package com.ko.tricount.repository;

import com.ko.tricount.entity.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
