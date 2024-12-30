package com.comercio.electronico.service;

import com.comercio.electronico.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {
    Mono<Producto> crearProducto(Producto producto);
    Mono<Producto> obtenerProducto(Long id);
    Flux<Producto> listarProductos();
    Flux<Producto> buscarPorNombre(String nombre);
    Mono<Producto> actualizarProducto(Long id, Producto producto);
    Mono<Producto> actualizarStock(Long id, Integer cantidad);
    Mono<Void> eliminarProducto(Long id);
}
