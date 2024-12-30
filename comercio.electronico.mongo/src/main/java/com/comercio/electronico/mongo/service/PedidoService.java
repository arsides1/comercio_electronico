package com.comercio.electronico.mongo.service;

import com.comercio.electronico.mongo.model.PedidoDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PedidoService {
    Mono<PedidoDocument> crearPedido(PedidoDocument pedido);
    Mono<PedidoDocument> actualizarPedido(String id, PedidoDocument pedido);
    Mono<PedidoDocument> obtenerPedido(String id);
    Flux<PedidoDocument> listarPedidos();
    Mono<Void> eliminarPedido(String id);
    Flux<PedidoDocument> obtenerPedidosPorCliente(Long clienteId);
    Flux<PedidoDocument> obtenerPedidosPorEstado(String estado);
}
