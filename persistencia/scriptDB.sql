create database projetoDB;

use projetoDB;

create table alunos(
    id int primary key auto_increment,
    matricula varchar(15) not null unique,
    nome varchar(150) not null,
    nota01 double,
    nota02 double
);
