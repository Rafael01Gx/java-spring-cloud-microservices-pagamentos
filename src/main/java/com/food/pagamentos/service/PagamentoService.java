package com.food.pagamentos.service;

import com.food.pagamentos.dto.PagamentoDto;
import com.food.pagamentos.model.Pagamento;
import com.food.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.repository = pagamentoRepository;
    }

    public Page<PagamentoDto> obterTodos(Pageable pageable) {
        return repository.findAll(pageable).map(PagamentoDto::new);
    }

    public PagamentoDto obterPorId(long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Pagamento não encontrado!"));
        return new PagamentoDto(pagamento);
    }

    @Transactional
    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = new Pagamento(dto);
        repository.save(pagamento);
        return new PagamentoDto(pagamento);
    }

    @Transactional
    public PagamentoDto atualizarPagamento(long id, PagamentoDto dto) {
        Pagamento pagamento = repository.getReferenceById(id);
        pagamento.atualizar(dto);
        return new PagamentoDto(pagamento);
    }

    @Transactional
    public void excluirPagamento(long id) {
        repository.deleteById(id);
    }
}
