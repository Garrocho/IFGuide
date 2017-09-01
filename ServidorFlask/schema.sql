drop table if exists eventos;
drop table if exists periodos;

create table periodos (
  id integer primary key autoincrement,
  curso varchar,
  serie integer
);

create table eventos (
  id integer primary key autoincrement,
  tipo integer,
  hora varchar,
  data varchar,
  duracao varchar,
  titulo varchar,
  descricao text
);
