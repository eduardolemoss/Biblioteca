package com.api.Biblioteca.Livro;

public record DadosListagemLivro(String Titulo,String ISBN ,String Ano_publicacao, Long Id_genero, Long Id_autor, Long id, String status, String foto) {
	public DadosListagemLivro(Livro dados) {
		this(dados.getTitulo(), dados.getISBN(), dados.getAno_publicacao(),dados.getId_genero(), dados.getId_autor(), dados.getId(),dados.getStatus(),dados.getFoto());
	}

}
