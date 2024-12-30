package com.comercio.electronico.service.impl;

import com.comercio.electronico.exception.RecursoNoEncontradoException;
import com.comercio.electronico.model.Producto;
import com.comercio.electronico.repo.ProductoRepository;
import com.comercio.electronico.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public Mono<Producto> crearProducto(Producto producto) {
        producto.setFechaCreacion(LocalDateTime.now());
        return productoRepository.save(producto);
    }

    @Override
    public Mono<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Producto no encontrado")));
    }

    @Override
    public Flux<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Flux<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional
    public Mono<Producto> actualizarProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Producto no encontrado")))
                .flatMap(productoExistente -> {
                    producto.setId(id);
                    producto.setFechaCreacion(productoExistente.getFechaCreacion());
                    producto.setFechaActualizacion(LocalDateTime.now());
                    return productoRepository.save(producto);
                });
    }

    @Override
    @Transactional
    public Mono<Producto> actualizarStock(Long id, Integer cantidad) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Producto no encontrado")))
                .flatMap(producto -> {
                    producto.setStock(producto.getStock() + cantidad);
                    producto.setFechaActualizacion(LocalDateTime.now());
                    return productoRepository.save(producto);
                });
    }

    @Override
    @Transactional
    public Mono<Void> eliminarProducto(Long id) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Producto no encontrado")))
                .flatMap(producto -> productoRepository.delete(producto));
    }
}
