package com.crossover.trial.journals.repository;

import com.crossover.trial.journals.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
