package com.api.Biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.Biblioteca.emprestimo.Emprestimo;
import com.api.Biblioteca.emprestimo.EmprestimoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
	
	@Autowired
    private  EmprestimoRepository emprestimoRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @GetMapping
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> obterEmprestimoPorId(@PathVariable Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        return emprestimo.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Emprestimo> criarEmprestimo(@RequestBody Emprestimo novoEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.save(novoEmprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimoAtualizado) {
        return emprestimoRepository.findById(id)
                .map(emprestimo -> {
                    emprestimo.setData_emprestimo(emprestimoAtualizado.getData_emprestimo());
                    emprestimo.setData_devolucao(emprestimoAtualizado.getData_devolucao());
                    emprestimo.setId(emprestimoAtualizado.getId());
                    emprestimo.setId_pessoa(emprestimoAtualizado.getId_pessoa());
                    emprestimoRepository.save(emprestimo);
                    return ResponseEntity.ok(emprestimo);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirEmprestimo(@PathVariable Long id) {
        return emprestimoRepository.findById(id)
                .map(emprestimo -> {
                    emprestimoRepository.delete(emprestimo);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
