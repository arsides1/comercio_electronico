package com.comercio.electronico.repo;

import com.comercio.electronico.model.Producto;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProductoRepository extends R2dbcRepository<Producto, Long> {
    Flux<Producto> findByNombreContainingIgnoreCase(String nombre);
}
