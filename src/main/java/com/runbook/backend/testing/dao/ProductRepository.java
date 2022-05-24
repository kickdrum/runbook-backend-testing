package com.runbook.backend.testing.dao;

import com.runbook.backend.testing.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    ProductEntity findProductEntityByDate(String date);

    ProductEntity findProductEntityByName(String name);
}
