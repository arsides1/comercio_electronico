package com.comercio.electronico.mongo.repo;

import com.comercio.electronico.mongo.model.PedidoDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PedidoRepository extends ReactiveMongoRepository<PedidoDocument, String> {
    // Métodos personalizados
    Flux<PedidoDocument> findByClienteId(Long clienteId);
    Flux<PedidoDocument> findByEstado(String estado);
    Mono<PedidoDocument> findByNumeroSeguimiento(String numeroSeguimiento);
}
