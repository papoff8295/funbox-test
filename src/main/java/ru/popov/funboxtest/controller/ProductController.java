package ru.popov.funboxtest.controller;

import lombok.RequiredArgsConstructor;
import org.openapitools.api.ProductsApi;
import org.openapitools.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.popov.funboxtest.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    private final ProductService productService;
    @Override
    public Mono<ResponseEntity<Flux<Product>>> getProducts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(productService.getProducts()));
    }

    @Override
    public Mono<ResponseEntity<Product>> getProduct(Integer id, ServerWebExchange exchange) {
        return productService.getProduct(id)
                .map(product -> ResponseEntity.status(HttpStatus.OK).body(product));
    }

    @Override
    public Mono<ResponseEntity<Product>> createProduct(Mono<Product> product, ServerWebExchange exchange) {
        return productService
                .createProduct(product)
                .map(productDto -> ResponseEntity.status(HttpStatus.OK).body(productDto));
    }

    @Override
    public Mono<ResponseEntity<Product>> updateProduct(Integer id, Mono<Product> product, ServerWebExchange exchange) {
        return productService
                .updateProduct(product, id)
                .map(productDto -> ResponseEntity.status(HttpStatus.OK).body(productDto));
    }

    @Override
    public Mono<ResponseEntity<Void>> removeProduct(Integer id, ServerWebExchange exchange) {
        return productService
                .removeProduct(id)
                .then(Mono.empty());
    }
}
