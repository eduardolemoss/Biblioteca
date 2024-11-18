package com.api.Biblioteca.emprestimo;

public record dadosAlteracaoEmprestimo(Long id, String data_emprestimo, String data_devolucao, Long id_pessoa, Long id_livro) {

}
