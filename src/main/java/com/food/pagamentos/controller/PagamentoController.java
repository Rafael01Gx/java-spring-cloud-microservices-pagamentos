package com.food.pagamentos.controller;

import com.food.pagamentos.dto.PagamentoDto;
import com.food.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoService service;

    public PagamentoController(PagamentoService pagamentoService) {
        this.service = pagamentoService;
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoDto>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(service.obterTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> detalhar(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok().body(service.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uriBuilder) {
        var pagamento = service.criarPagamento(dto);
        return ResponseEntity
                .created(uriBuilder.path("/pagamentos/{id}")
                        .buildAndExpand(pagamento.id())
                        .toUri())
                .body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id ,@RequestBody @Valid PagamentoDto dto) {
        return ResponseEntity.ok().body(service.atualizarPagamento(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable @NotNull Long id) {
        return ResponseEntity.noContent().build();
    }
}
