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

import com.api.Biblioteca.Genero.DadosAlteracaoGenero;
import com.api.Biblioteca.Genero.DadosCadastroGenero;
import com.api.Biblioteca.Genero.DadosListagemGenero;
import com.api.Biblioteca.Genero.Genero;
import com.api.Biblioteca.Genero.GeneroRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Genero")
public class GeneroController {
	@Autowired
	private GeneroRepository generoRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroGenero dados){
		var Genero = new Genero(dados);
		generoRepository.save(Genero);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(Genero.getId()).toUri();
		return ResponseEntity.created(location).body(Genero);
				}
	@GetMapping
	public ResponseEntity<List<DadosListagemGenero>> listar(){
		var lista = generoRepository.findAll().stream().map(DadosListagemGenero::new).toList();
		return ResponseEntity.ok(lista);
	}
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody DadosAlteracaoGenero dados){
		var genero = generoRepository.getReferenceById(id);
		genero.atualizaInformacoes(dados);
		generoRepository.save(genero);
		return ResponseEntity.ok(dados);
	}
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id){
		if(!generoRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero nao encontrado");
		}
		generoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/{id}")
    public ResponseEntity<?> detalhar(@PathVariable Long id) {
    	if (!generoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero não encontrada");
        } 
    	var genero = generoRepository.getReferenceById(id);
		DadosListagemGenero dados = new DadosListagemGenero(genero);
		return ResponseEntity.ok(dados);
    }


}
