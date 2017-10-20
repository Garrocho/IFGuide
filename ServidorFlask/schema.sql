drop table if exists eventos;
drop table if exists periodos;
drop table if exists atualizacoes;


create table periodos (
  id integer primary key autoincrement,
  curso varchar,
  serie integer
);

create table eventos (
  id integer primary key autoincrement,
  id_periodo integer,
  tipo integer,
  hora varchar,
  data varchar,
  duracao varchar,
  titulo varchar,
  descricao text
);

create table atualizacoes (
  id integer primary key,
  hora varchar,
  data varchar,
);

insert into atualizacoes (id,hora,data) values(1,'0','0');



