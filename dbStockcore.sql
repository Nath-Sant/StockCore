create database dbStockcore;

use dbStockcore;

create table tipoProduto(
	idTipoProduto int primary key auto_increment,
    nome varchar(250) not null
);

create table Produto(
	idProduto int primary key auto_increment,
    nome varchar(250) not null,
    idTipoProduto int,
    quantidade int,
    descricao varchar(250),
	foreign key(idTipoProduto) references tipoProduto(idTipoProduto)
);

create table movimentacao(
	idMovimentacao int primary key auto_increment,
    idProduto int,
    quantidade int,
    tipoMovimentacao varchar(20),
    dataHora datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    origem varchar(20),
	foreign key(idProduto) references Produto(idProduto)
);

create table usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);


INSERT INTO tipoProduto (nome) VALUES
('Eletrônico'),
('Roupas'),
('Alimentos'),
('Material de Escritório'),
('Brinquedos');

INSERT INTO Produto (nome, idTipoProduto, quantidade, descricao) VALUES
('Notebook Lenovo', 1, 10, 'Notebook com 8GB de RAM e 256GB SSD'),
('Camiseta Preta', 2, 50, 'Camiseta de algodão tamanho M'),
('Arroz Tipo 1', 3, 100, 'Pacote de 5kg de arroz branco'),
('Caneta Azul', 4, 200, 'Caneta esferográfica azul'),
('Boneco de Ação', 5, 30, 'Boneco articulado para crianças maiores de 5 anos');

INSERT INTO usuario(nome, senha, role) VALUES
('Admin', 'adm123', 'ADM')