package com.api.Biblioteca.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.api.Biblioteca.Livro.LivroRepository;
import com.api.Biblioteca.Pessoa.PessoaRepository;
import com.api.Biblioteca.Reserva.DadosCadastroReserva;
import com.api.Biblioteca.Reserva.DadosListagemReserva;
import com.api.Biblioteca.Reserva.Reserva;
import com.api.Biblioteca.Reserva.ReservaRepository;
import com.api.Biblioteca.Reserva.dadosAlteracaoReserva;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Reserva")
public class ReservaController {
	@Autowired 
	private ReservaRepository reservaRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody DadosCadastroReserva dados){
		var Reserva = new Reserva(dados);
		reservaRepository.save(Reserva);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id_pessoa}/{id_livro}")
				.buildAndExpand(Reserva.getId_pessoa(),Reserva.getId_livro()).toUri();
		return ResponseEntity.created(location).body(Reserva);
	}
@GetMapping
public ResponseEntity<List<DadosListagemReserva>> listar(){
	var lista = reservaRepository.findAll().stream().map(DadosListagemReserva::new).toList();
	return ResponseEntity.ok(lista);
}
@PutMapping
@Transactional
public ResponseEntity<?> excluir(@PathVariable dadosAlteracaoReserva dados){
	if (!reservaRepository.existsById(dados.id_pessoa())) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
	}
	if(!reservaRepository.existsById(dados.id_livro())) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
	}
	if(!reservaRepository.existsById(dados.Id())) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id não encontrado");
	}
	var Reserva = reservaRepository.getReferenceById(dados.Id());
	Reserva.atualizaInformacoes(dados);
	return ResponseEntity.ok(dados);
}
@DeleteMapping("/{id}")
@Transactional
public ResponseEntity<?> excluir(@PathVariable Long Id){
	if(!reservaRepository.existsById(Id)) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva não encontrada");
	}
	reservaRepository.deleteById(Id);
	return ResponseEntity.noContent().build();
}
}
