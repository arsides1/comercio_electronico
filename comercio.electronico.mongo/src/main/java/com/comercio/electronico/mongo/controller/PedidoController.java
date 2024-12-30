package com.comercio.electronico.mongo.controller;

import com.comercio.electronico.mongo.model.PedidoDocument;
import com.comercio.electronico.mongo.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PedidoDocument> crearPedido(@Valid @RequestBody PedidoDocument pedido) {
        return pedidoService.crearPedido(pedido);
    }

    @GetMapping
    public Flux<PedidoDocument> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public Mono<PedidoDocument> obtenerPedido(@PathVariable String id) {
        return pedidoService.obtenerPedido(id);
    }

    @PutMapping("/{id}")
    public Mono<PedidoDocument> actualizarPedido(@PathVariable String id, @Valid @RequestBody PedidoDocument pedido) {
        return pedidoService.actualizarPedido(id, pedido);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarPedido(@PathVariable String id) {
        return pedidoService.eliminarPedido(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public Flux<PedidoDocument> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        return pedidoService.obtenerPedidosPorCliente(clienteId);
    }

    @GetMapping("/estado/{estado}")
    public Flux<PedidoDocument> obtenerPedidosPorEstado(@PathVariable String estado) {
        return pedidoService.obtenerPedidosPorEstado(estado);
    }
}
