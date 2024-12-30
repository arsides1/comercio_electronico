package com.comercio.electronico.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedido")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDocument {
    @Id
    private String id;

    @Field("cliente_id")
    private Long clienteId;

    @Field("items")
    private List<ItemPedidoDocument> items;

    @Field("total")
    private BigDecimal total;

    @Field("registro_pedido")
    private LocalDateTime registroPedido;

    @Field("update_pedido")
    private LocalDateTime updatePedido;

    @Field("estado")
    private String estado;

    @Field("info_envio_facturacion")
    private String infoEnvioFacturacion;

    @Field("info_facturacion")
    private String infoFacturacion;

    @Field("metodo_pago")
    private String metodoPago;

    @Field("numero_seguimiento")
    private String numeroSeguimiento;

    @Field("notas_pedido")
    private String notasPedido;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemPedidoDocument {
        @Field("product_id")
        private Long productId;

        @Field("cantidad")
        private Integer cantidad;

        @Field("precio_unitario")
        private BigDecimal precioUnitario;

        @Field("sub_total")
        private BigDecimal subTotal;

        @Field("descuento")
        private BigDecimal descuento;

        @Field("notas_item")
        private String notasItem;
    }
}
