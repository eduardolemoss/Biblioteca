CREATE TABLE Genero(
	id int primary key auto_increment,
	nome varchar(100)
);

CREATE TABLE Autor(
	id int primary key auto_increment,
	nome varchar(100)
);

CREATE TABLE Livro(
	id int primary key auto_increment,
	titulo varchar(100),
	ISBN varchar(100),
	ano_publicacao varchar(100),
	id_genero int,
	id_autor int,
	foreign key (id_genero) references Genero(id),
	foreign key (id_autor) references Autor(id)
);

CREATE TABLE Pessoa(
	id int primary key auto_increment,
	nome varchar(200),
	email varchar(500),
	telefone varchar(40)
);

CREATE TABLE Emprestimo(
	id int primary key auto_increment,
	data_emprestimo varchar(50),
	data_devolucao varchar(50),
	id_livro int,
	id_pessoa int,
	foreign key (id_livro) references Livro(id),
	foreign key (id_pessoa) references Pessoa(id)
);

CREATE TABLE Reserva(
	id int primary key auto_increment,
	data_reserva varchar(50),
	data_validade varchar(50),
	id_livro int,
	id_pessoa int,
	foreign key (id_livro) references Livro(id),
	foreign key (id_pessoa) references Pessoa(id)
);
