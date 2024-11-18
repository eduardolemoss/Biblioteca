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

import com.api.Biblioteca.Autor.Autor;
import com.api.Biblioteca.Autor.AutorRepository;
import com.api.Biblioteca.Autor.DadosCadastroAutor;
import com.api.Biblioteca.Autor.DadosListagemAutor;
import com.api.Biblioteca.Autor.dadosAlteracaoAutor;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Autor")
public class AutorController {

	@Autowired
	private AutorRepository autorRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroAutor dados){
		var Autor = new Autor(dados);
		autorRepository.save(Autor);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(Autor.getId()).toUri();
		return ResponseEntity.created(location).body(Autor);
	}
	
	@GetMapping
	public ResponseEntity<List<DadosListagemAutor>> listar(){
		var lista = autorRepository.findAll().stream().map(DadosListagemAutor::new).toList();
		return ResponseEntity.ok(lista);
	}
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody dadosAlteracaoAutor dados){
		var Autor = autorRepository.getReferenceById(id);
		Autor.atualizaInformacoes(dados);
		autorRepository.save(Autor);
		return ResponseEntity.ok(dados);
	}
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable long id){
		if(!autorRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor nao encontrado");
		}
		autorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id){
		if(!autorRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor nao encontrado");
		}
		{
			var autor = autorRepository.getReferenceById(id);
			DadosListagemAutor dados = new DadosListagemAutor(autor);
			return ResponseEntity.ok(dados);
		}
	}
	
}
