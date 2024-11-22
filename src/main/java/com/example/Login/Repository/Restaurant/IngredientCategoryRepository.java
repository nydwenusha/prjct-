package com.example.Login.Repository.Restaurant;


import com.example.Login.Entity.Restaurant.IngredientCategory;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory,Long> {

       List<IngredientCategory> findByRestaurantId(Long id);

    Optional<IngredientCategory> findById(SingularAttribute<AbstractPersistable, Serializable> id);
}
