package com.comercio.electronico.service.impl;


import com.comercio.electronico.exception.EmailDuplicadoException;
import com.comercio.electronico.exception.RecursoNoEncontradoException;
import com.comercio.electronico.model.Cliente;
import com.comercio.electronico.repo.ClienteRepository;
import com.comercio.electronico.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Mono<Cliente> crearCliente(Cliente cliente) {
        return clienteRepository.existsByEmail(cliente.getEmail())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new EmailDuplicadoException("El email ya está registrado"));
                    }
                    cliente.setFechaRegistro(LocalDateTime.now());
                    return clienteRepository.save(cliente);
                });
    }

    @Override
    public Mono<Cliente> obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Cliente no encontrado")));
    }

    @Override
    public Flux<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<Cliente> actualizarCliente(Long id, Cliente cliente) {
        return clienteRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Cliente no encontrado")))
                .flatMap(clienteExistente -> {
                    cliente.setId(id);
                    cliente.setFechaRegistro(clienteExistente.getFechaRegistro());
                    return clienteRepository.save(cliente);
                });
    }

    @Override
    @Transactional
    public Mono<Void> eliminarCliente(Long id) {
        return clienteRepository.findById(id)
                .switchIfEmpty(Mono.error(new RecursoNoEncontradoException("Cliente no encontrado")))
                .flatMap(cliente -> clienteRepository.delete(cliente));
    }
}
