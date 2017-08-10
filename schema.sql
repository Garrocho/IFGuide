drop table if exists eventos;
drop table if exists periodos;

create table eventos (
  id integer primary key autoincrement,
  curso varchar,
  serie integer
);

create table periodos (
  id integer primary key autoincrement,
  tipo integer,
  data date,
  titulo varchar,
  descricao text,
  hora time
);
