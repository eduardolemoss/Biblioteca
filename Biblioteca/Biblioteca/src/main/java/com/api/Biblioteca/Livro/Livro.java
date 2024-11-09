package com.api.Biblioteca.Livro;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Livro")
@Entity(name = "Livros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
//String titulo, String ISBN, String ano_publicacao, Long id_genero, Long id_autor

public class Livro {
	public Livro(DadosCadastroLivro dados) {
		
		this.titulo = dados.titulo();
		this.ISBN = dados.ISBN();
		this.ano_publicacao = dados.ano_publicacao();
		this.id_autor = dados.id_autor();
		this.id_genero = dados.id_genero();
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String ISBN;
	private String ano_publicacao;
	private Long id_genero;
	private Long id_autor;
	
	
	
	public void atualizaInformacoes(dadosAlteracaoLivro dados) {
		if(dados.titulo() != null) {
			this.titulo = dados.titulo();
		}
		if (dados.ISBN() != null) {
			this.ISBN = dados.ISBN();
		
		}
		if (dados.ano_publicacao() != null) {
			this.ano_publicacao = dados.ano_publicacao();
			
		}
		if(dados.id_autor() != null && dados.id_autor() != 0) {
			this.id_autor = dados.id_autor();
		}
		if(dados.id_genero() != null && dados.id_genero() != 0) {
			this.id_genero = dados.id_genero();
		}
		
	}

	
}
