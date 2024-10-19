package com.api.controllers;

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

import com.api.Autor.AutorRepository;
import com.api.Livro.DadosCadastroLivro;
import com.api.Livro.DadosListagemLivro;
import com.api.Livro.Livro;
import com.api.Livro.LivroRepository;
import com.api.Livro.dadosAlteracaoLivro;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Livro")
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroLivro dados) {
		if (!autorRepository.existsById(dados.id_autor())) {
			return ResponseEntity.badRequest().body("Autor não encontrado");
		}
		var Livro = new Livro(dados);
		livroRepository.save(Livro);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(Livro.getId_autor()).toUri();
		return ResponseEntity.created(location).body(Livro);
	}

	@GetMapping
	public ResponseEntity<List<DadosListagemLivro>> listar() {
		var lista = livroRepository.findAll().stream().map(DadosListagemLivro::new).toList();
		return ResponseEntity.ok(lista);

	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> alterar(@RequestBody dadosAlteracaoLivro dados) {
		if (!autorRepository.existsById(dados.id_autor())) {
			return ResponseEntity.badRequest().body("Autor não encontrado");

		}
		if (!livroRepository.existsById(dados.id())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
		}
		var Livro = livroRepository.getReferenceById(dados.id());
		Livro.atualizaInformacoes(dados);
		return ResponseEntity.ok(Livro);
	} 

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		if (!livroRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
		}
		livroRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{/id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) {
		if (!livroRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
		}
		{
			var livro = livroRepository.getReferenceById(id);
			DadosListagemLivro dados = new DadosListagemLivro(livro);
			return ResponseEntity.ok(dados);
		}
	}
}
