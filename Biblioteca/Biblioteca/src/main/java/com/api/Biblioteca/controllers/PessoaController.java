package com.api.Biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.Biblioteca.Genero.DadosListagemGenero;
import com.api.Biblioteca.Pessoa.DadosCadastroPessoa;
import com.api.Biblioteca.Pessoa.DadosListagemPessoa;
import com.api.Biblioteca.Pessoa.Pessoa;
import com.api.Biblioteca.Pessoa.PessoaRepository;
import com.api.Biblioteca.Pessoa.dadosAlteracaoPessoa;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Pessoa")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroPessoa dados){
		var Pessoa = new Pessoa(dados);
		pessoaRepository.save(Pessoa);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(Pessoa.getId()).toUri();
		return ResponseEntity.created(location).body(Pessoa);
		
	}
	@GetMapping
	public ResponseEntity<List<DadosListagemPessoa>> listar(){
		var lista = pessoaRepository.findAll().stream().map(DadosListagemPessoa::new).toList();
		return ResponseEntity.ok(lista);
	}
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody dadosAlteracaoPessoa dados){
		var Pessoa = pessoaRepository.getReferenceById(id);
		Pessoa.atualizaInformacoes(dados);
		pessoaRepository.save(Pessoa);
		return ResponseEntity.ok(dados);
	}
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id){
		if(!pessoaRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
		}
		pessoaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
    	if (!pessoaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("pessoa não encontrada");
        } 
    	var pessoa = pessoaRepository.getReferenceById(id);
		DadosListagemPessoa dados = new DadosListagemPessoa(pessoa);
		return ResponseEntity.ok(dados);
    }
	
	
}
