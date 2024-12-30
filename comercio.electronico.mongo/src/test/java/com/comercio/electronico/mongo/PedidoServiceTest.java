package com.comercio.electronico.mongo;

import com.comercio.electronico.mongo.exception.RecursoNoEncontradoException;
import com.comercio.electronico.mongo.model.PedidoDocument;
import com.comercio.electronico.mongo.repo.PedidoRepository;
import com.comercio.electronico.mongo.service.impl.PedidoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;



@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Test
    void crearPedido_Exitoso() {
        // Arrange
        PedidoDocument pedido = crearPedidoMock();
        when(pedidoRepository.save(any(PedidoDocument.class))).thenReturn(Mono.just(pedido));

        // Act
        Mono<PedidoDocument> resultado = pedidoService.crearPedido(pedido);

        // Assert
        StepVerifier.create(resultado)
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepository).save(any(PedidoDocument.class));
    }

    @Test
    void obtenerPedido_Existente() {
        // Arrange
        String id = "1";
        PedidoDocument pedido = crearPedidoMock();
        when(pedidoRepository.findById(id)).thenReturn(Mono.just(pedido));

        // Act
        Mono<PedidoDocument> resultado = pedidoService.obtenerPedido(id);

        // Assert
        StepVerifier.create(resultado)
                .expectNext(pedido)
                .verifyComplete();
    }

    @Test
    void obtenerPedido_NoExistente() {
        // Arrange
        String id = "999";
        when(pedidoRepository.findById(id)).thenReturn(Mono.empty());

        // Act
        Mono<PedidoDocument> resultado = pedidoService.obtenerPedido(id);

        // Assert
        StepVerifier.create(resultado)
                .expectError(RecursoNoEncontradoException.class)
                .verify();
    }

    @Test
    void listarPedidos_Exitoso() {
        // Arrange
        List<PedidoDocument> pedidos = Arrays.asList(
                crearPedidoMock(),
                crearPedidoMock()
        );
        when(pedidoRepository.findAll()).thenReturn(Flux.fromIterable(pedidos));

        // Act
        Flux<PedidoDocument> resultado = pedidoService.listarPedidos();

        // Assert
        StepVerifier.create(resultado)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void actualizarPedido_Exitoso() {
        // Arrange
        String id = "1";
        PedidoDocument pedidoExistente = crearPedidoMock();
        PedidoDocument pedidoActualizado = crearPedidoMock();

        when(pedidoRepository.findById(id)).thenReturn(Mono.just(pedidoExistente));
        when(pedidoRepository.save(any(PedidoDocument.class))).thenReturn(Mono.just(pedidoActualizado));

        // Act
        Mono<PedidoDocument> resultado = pedidoService.actualizarPedido(id, pedidoActualizado);

        // Assert
        StepVerifier.create(resultado)
                .expectNext(pedidoActualizado)
                .verifyComplete();
    }

    @Test
    void eliminarPedido_Exitoso() {
        // Arrange
        String id = "1";
        PedidoDocument pedido = crearPedidoMock();
        when(pedidoRepository.findById(id)).thenReturn(Mono.just(pedido));
        when(pedidoRepository.delete(pedido)).thenReturn(Mono.empty());

        // Act
        Mono<Void> resultado = pedidoService.eliminarPedido(id);

        // Assert
        StepVerifier.create(resultado)
                .verifyComplete();
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

