package com.api.Biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.Biblioteca.Livro.Livro;
import com.api.Biblioteca.Livro.LivroRepository;
import com.api.Biblioteca.Pessoa.Pessoa;
import com.api.Biblioteca.Pessoa.PessoaRepository;
import com.api.Biblioteca.emprestimo.DadosCadastroEmprestimo;
import com.api.Biblioteca.emprestimo.DadosListagemEmprestimo;
import com.api.Biblioteca.emprestimo.Emprestimo;
import com.api.Biblioteca.emprestimo.EmprestimoRepository;
import com.api.Biblioteca.emprestimo.dadosAlteracaoEmprestimo;
import com.api.Biblioteca.emprestimos.DadosListagemEmprestimoNoID;

import jakarta.transaction.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<?> detalhar(@PathVariable Long id){
    	if(!emprestimoRepository.existsById(id)) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emprestimo não encontrado");
    	}
    	var emprestimo = emprestimoRepository.getReferenceById(id);
    	DadosListagemEmprestimo dados = new DadosListagemEmprestimo(emprestimo);
    	return ResponseEntity.ok(dados);
    }
   

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroEmprestimo dados){
    	Livro livro = livroRepository.findById(dados.id_livro()).orElse(null);
    	if(livro == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
    	}	
    	Pessoa pessoa = pessoaRepository.findById(dados.id_pessoa()).orElse(null);
    	if(pessoa == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
    	}
    	var emprestimo = new Emprestimo(dados, livro, pessoa);
    	emprestimoRepository.save(emprestimo);
    	if (livro != null) {
    		livro.atualizaStatusLivro("EMPRESTADO");
    		livroRepository.save(livro);
    	}
    	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(emprestimo.getId())
    			.toUri();
    	return ResponseEntity.created(location).body(emprestimo);
    	}

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarEmprestimo(@RequestBody dadosAlteracaoEmprestimo dados) {
    	Livro livro = livroRepository.findById(dados.id_livro()).orElse(null);
    	if(livro == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
    	}
    	Pessoa pessoa = pessoaRepository.findById(dados.id_pessoa()).orElse(null);
    	if(pessoa == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
    	}
    	if(!emprestimoRepository.existsById(dados.id())) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emprestimo não encontrado");
    	}
    	var emprestimo = emprestimoRepository.getReferenceById(dados.id());
    	emprestimo.atualizaInformacoes(dados, livro, pessoa);
    	return ResponseEntity.ok(dados);
    	
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEmprestimo(@PathVariable Long id) {
    	if (!emprestimoRepository.existsById(id)) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empréstimo não encontrado");
    	}
    	var emprestimo = emprestimoRepository.getReferenceById(id);
    	Livro livro = livroRepository.findById(emprestimo.getId()).orElse(null);
    	if(livro != null) {
    		livro.atualizaStatusLivro("DISPONIVEL");
    		livroRepository.save(livro);
    	}
    	emprestimoRepository.deleteById(id);
    	return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverEmprestimo(@PathVariable Long id){
    	Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
    	if(emprestimo == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emprestimo não encontrado");
    	}
    	LocalDate data = LocalDate.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	String dataFormada = data.format(formatter);
    	emprestimo.atualizaDataDevolucao(dataFormada);
    	Livro livro = livroRepository.findById(emprestimo.getLivro().getId()).orElse(null);
    	if (livro != null) {
    		livro.atualizaStatusLivro("DISPONIVEL");
    		livroRepository.save(livro);
    	}
    	emprestimoRepository.save(emprestimo);
    	return ResponseEntity.ok("Devolução registrada!");
    }
  
    @GetMapping("/noid")
    public ResponseEntity<List<DadosListagemEmprestimoNoID>> listarNoID(){
    	var lista = emprestimoRepository.findAll().stream().map(DadosListagemEmprestimoNoID::new).toList();
    	return ResponseEntity.ok(lista);
    }
}
