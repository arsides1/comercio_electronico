package com.comercio.electronico.service;

import com.comercio.electronico.model.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {
    Mono<Cliente> crearCliente(Cliente cliente);
    Mono<Cliente> obtenerCliente(Long id);
    Flux<Cliente> listarClientes();
    Mono<Cliente> actualizarCliente(Long id, Cliente cliente);
    Mono<Void> eliminarCliente(Long id);
}
