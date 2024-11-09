package com.api.Biblioteca.Pessoa;

public record DadosListagemPessoa(Long Id, String nome, String email, String telefone ) {
	public DadosListagemPessoa(Pessoa dados) {
		this(dados.getId(),dados.getNome(),dados.getEmail(),dados.getTelefone());
	}

}
