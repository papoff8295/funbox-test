package ru.popov.funboxtest.service;

import org.openapitools.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> getProducts();
    Mono<Product> getProduct(Integer id);
    Mono<Product> createProduct(Mono<Product> product);
    Mono<Product> updateProduct(Mono<Product> product, Integer id);
    Mono<Void> removeProduct(Integer id);
}
