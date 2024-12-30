package com.comercio.electronico.repo;

import com.comercio.electronico.model.Cliente;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ClienteRepository extends R2dbcRepository<Cliente, Long> {
    Mono<Cliente> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);
}
