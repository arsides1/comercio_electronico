package com.comercio.electronico.mongo;

import com.comercio.electronico.mongo.controller.PedidoController;
import com.comercio.electronico.mongo.model.PedidoDocument;
import com.comercio.electronico.mongo.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;


import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@WebFluxTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private PedidoService pedidoService;

    @Test
    void crearPedido_Exitoso() {
        // Arrange
        PedidoDocument pedido = crearPedidoMock();
        when(pedidoService.crearPedido(any(PedidoDocument.class)))
                .thenReturn(Mono.just(pedido));

        // Act & Assert
        webTestClient.post()
                .uri("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(pedido), PedidoDocument.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(pedido.getId())
                .jsonPath("$.clienteId").isEqualTo(pedido.getClienteId());
    }

    @Test
    void obtenerPedido_Existente() {
        // Arrange
        String id = "1";
        PedidoDocument pedido = crearPedidoMock();
        when(pedidoService.obtenerPedido(id)).thenReturn(Mono.just(pedido));

        // Act & Assert
        webTestClient.get()
                .uri("/api/pedidos/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(pedido.getId());
    }

    @Test
    void listarPedidos_Exitoso() {
        // Arrange
        List<PedidoDocument> pedidos = Arrays.asList(
                crearPedidoMock(),
                crearPedidoMock()
        );
        when(pedidoService.listarPedidos()).thenReturn(Flux.fromIterable(pedidos));

        // Act & Assert
        webTestClient.get()
                .uri("/api/pedidos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PedidoDocument.class)
                .hasSize(2);
    }

    private PedidoDocument crearPedidoMock() {
        return PedidoDocument.builder()
                .id("1")
                .clienteId(1L)
                .items(Arrays.asList(
                        PedidoDocument.ItemPedidoDocument.builder()
                                .productId(1L)
                                .cantidad(2)
                                .precioUnitario(new BigDecimal("99.99"))
                                .subTotal(new BigDecimal("199.98"))
                                .build()
                ))
                .total(new BigDecimal("199.98"))
                .estado("PENDIENTE")
                .metodoPago("TARJETA")
                .registroPedido(LocalDateTime.now())
                .build();
    }
}
