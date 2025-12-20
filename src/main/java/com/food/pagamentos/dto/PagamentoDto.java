package com.food.pagamentos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.pagamentos.model.Pagamento;
import com.food.pagamentos.model.Status;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PagamentoDto(
        Long id,
        BigDecimal valor,
        String nome,
        String numero,
        String expiracao,
        String codigo,
        Status status,
        Long formaDePagamentoId,
        Long pedidoId
) {

    public PagamentoDto(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getNumero(),
                pagamento.getExpiracao(),
                pagamento.getCodigo(),
                pagamento.getStatus(),
                pagamento.getFormaPagamentoId(),
                pagamento.getPedidoId()
        );
    }
}
