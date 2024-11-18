package com.api.Biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.Biblioteca.Livro.LivroRepository;
import com.api.Biblioteca.Pessoa.PessoaRepository;
import com.api.Biblioteca.emprestimo.DadosCadastroEmprestimo;
import com.api.Biblioteca.emprestimo.DadosListagemEmprestimo;
import com.api.Biblioteca.emprestimo.Emprestimo;
import com.api.Biblioteca.emprestimo.EmprestimoRepository;
import com.api.Biblioteca.emprestimo.dadosAlteracaoEmprestimo;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
	
	@Autowired
    private  EmprestimoRepository emprestimoRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemEmprestimo>> listar(){
    	var lista = emprestimoRepository.findAll().stream().map(DadosListagemEmprestimo::new).toList();
    	return ResponseEntity.ok(lista);
    	
    	
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> obterEmprestimoPorId(@PathVariable Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        return emprestimo.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Emprestimo> criarEmprestimo(@RequestBody Emprestimo novoEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.save(novoEmprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarEmprestimo(@PathVariable Long id, @RequestBody dadosAlteracaoEmprestimo dados) {
    	if(!emprestimoRepository.existsById(id)) {
    		return ResponseEntity.badRequest().body("emprestimo nao encontrado");
    	}
    	if(!livroRepository.existsById(id)) {
    		return ResponseEntity.badRequest().body("Livro não encontrado");
    	}
    	if(!pessoaRepository.existsById(id)) {
    		return ResponseEntity.badRequest().body("Pessoa não encontrada");
    	}
    	var emprestimo = emprestimoRepository.getReferenceById(id);
    	emprestimo.atualizaInformacoes(dados);
    	return ResponseEntity.ok(dados);
    	
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
