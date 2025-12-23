package com.food.pagamentos.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "pedidos-ms")
public interface PedidoClient {

    @PutMapping("/pedidos/{id}/pago")
    void atualizaPagamento(@PathVariable("id") Long id);
}
