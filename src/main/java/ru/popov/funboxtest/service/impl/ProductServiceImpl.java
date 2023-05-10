package ru.popov.funboxtest.service.impl;

import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.popov.funboxtest.model.ProductEntity;
import ru.popov.funboxtest.repository.ProductRepository;
import ru.popov.funboxtest.service.ProductService;
import ru.popov.funboxtest.utils.JsonConverter;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final JsonConverter jsonConverter;

    @Override
    public Flux<Product> getProducts() {
        return productRepository.findAll()
                .map(this::entityToDto);
    }

    @Override
    public Mono<Product> getProduct(Integer id) {
        return productRepository.findById(id)
                .map(this::entityToDto);
    }

    @Override
    public Mono<Product> createProduct(Mono<Product> product) {
        return product
                .map(this::dtoToEntity)
                .flatMap(productRepository::save)
                .map(this::entityToDto);
    }

    @Override
    public Mono<Product> updateProduct(Mono<Product> product, Integer id) {
        return productRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Not found")))
                .then(product)
                .map(this::dtoToEntity)
                .flatMap(productRepository::save)
                .map(this::entityToDto);
    }

    @Override
    public Mono<Void> removeProduct(Integer id) {
        return productRepository.deleteById(id);
    }

    private ProductEntity dtoToEntity(Product dto) {
        return ProductEntity.builder()
                .id(dto.getId())
                .productJson(jsonConverter.toJson(dto))
                .build();
    }

    private Product entityToDto(ProductEntity entity) {
        return jsonConverter.fromJsonString(
                Optional.ofNullable(entity.getProductJson())
                        .map(Json::asString)
                        .orElse("{}"),
                Product.class
        ).id(entity.getId());
    }
}
