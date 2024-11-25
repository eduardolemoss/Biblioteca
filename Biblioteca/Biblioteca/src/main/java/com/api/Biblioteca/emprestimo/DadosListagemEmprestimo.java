package com.api.Biblioteca.emprestimo;

public record DadosListagemEmprestimo(Long id,String data_emprestimo, String data_devolucao, Long id_pessoa, Long id_livro) {
	public DadosListagemEmprestimo(Emprestimo dados) {
		this(dados.getId(), dados.getData_emprestimo(), dados.getData_devolucao(),dados.getPessoa().getId(),dados.getLivro().getId());
	}

}
