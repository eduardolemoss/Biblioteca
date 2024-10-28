package com.api.Biblioteca.Livro;

public record DadosListagemLivro(String Titulo,String ISBN ,String Ano_publicacao, long Id_genero, long Id_autor,Long id) {
	public DadosListagemLivro(Livro dados) {
		this(dados.getTitulo(), dados.getISBN(), dados.getAno_publicacao(),dados.getId_genero(), dados.getId_autor(),dados.getId());
	}

		

}
