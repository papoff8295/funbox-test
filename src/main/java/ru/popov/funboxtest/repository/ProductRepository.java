package ru.popov.funboxtest.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.popov.funboxtest.model.ProductEntity;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
}
