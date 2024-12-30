package com.comercio.electronico.mongo.service.impl;

import com.comercio.electronico.mongo.exception.RecursoNoEncontradoException;
import com.comercio.electronico.mongo.model.PedidoDocument;
import com.comercio.electronico.mongo.repo.PedidoRepository;
import com.comercio.electronico.mongo.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;

    @Override
    public Mono<PedidoDocument> crearPedido(PedidoDocument pedido) {
        pedido.setRegistroPedido(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        calcularTotales(pedido);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Mono<PedidoDocument> actualizarPedido(String id, PedidoDocument pedido) {
        return pedidoRepository.findById(id)
                .flatMap(pedidoExistente -> {
                    pedido.setId(id);
                    pedido.setUpdatePedido(LocalDateTime.now());
                    calcularTotales(pedido);
                    return pedidoRepository.save(pedido);
                })
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id)));
    }

    @Override
    public Mono<PedidoDocument> obtenerPedido(String id) {
        return pedidoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id)));
    }

    @Override
    public Flux<PedidoDocument> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Mono<Void> eliminarPedido(String id) {
        return pedidoRepository.findById(id)
                .flatMap(pedidoRepository::delete)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id)));
    }

    @Override
    public Flux<PedidoDocument> obtenerPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    public Flux<PedidoDocument> obtenerPedidosPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    private void calcularTotales(PedidoDocument pedido) {
        BigDecimal total = pedido.getItems().stream()
                .map(item -> {
                    BigDecimal subtotal = item.getPrecioUnitario()
                            .multiply(BigDecimal.valueOf(item.getCantidad()));
                    item.setSubTotal(subtotal);
                    return subtotal.subtract(item.getDescuento() != null ? item.getDescuento() : BigDecimal.ZERO);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(total);
    }
}
