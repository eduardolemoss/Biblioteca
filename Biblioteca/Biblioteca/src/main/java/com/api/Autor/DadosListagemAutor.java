package com.api.Autor;

public record DadosListagemAutor(Long id, String nome) {
	public DadosListagemAutor(Autor dados) {
		this(dados.getId(),dados.getNome());
	}

}
