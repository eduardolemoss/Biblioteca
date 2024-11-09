package com.api.Biblioteca.Autor;

public record DadosListagemAutor(Long id, String nome) {
	public DadosListagemAutor(Autor dados) {
		this(dados.getId(),dados.getNome());
	}

}
