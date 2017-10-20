import os
import sqlite3
from flask import Flask, request, session, g, redirect, url_for, abort, \
     render_template, flash, jsonify, Response, send_from_directory
import json


# Configuracoes do Aplicacao Flask
app = Flask(__name__)
app.config['DEBUG'] = True
app.config.from_object(__name__)

# Configuracoes do Banco de Dados
app.config.update(dict(
    DATABASE=os.path.join(app.root_path, 'ifguide.db'),
    SECRET_KEY='development key',
    USERNAME='admin',
    PASSWORD='default'
))
app.config.from_envvar('FLASKR_SETTINGS', silent=True)

#########################################################
##### INICIO DO BLOCO DAS FUNCOES DO BANCO DE DADOS #####
#########################################################
def bd_conecta():
    """Conecta ao banco de dados especificado."""
    if not hasattr(g, 'sqlite_db'):
        rv = sqlite3.connect(app.config['DATABASE'])
        rv.row_factory = sqlite3.Row
        g.sqlite_db = rv
    return g.sqlite_db

@app.teardown_appcontext
def bd_fechar(error):
    """Fecha o Banco de Dados ao Fim da Requisicao."""
    if hasattr(g, 'sqlite_db'):
        g.sqlite_db.close()

@app.cli.command('initdb')
def bd_iniciar():
    """Inicia a Conexao com o Banco de Dados."""
    db = bd_conecta()
    with app.open_resource('schema.sql', mode='r') as f:
        db.cursor().executescript(f.read())
    db.commit()

def bd_obter_periodos():
    db = bd_conecta()
    cur = db.execute('select * from periodos')
    column_names = [d[0] for d in cur.description]
    mensagens = []
    for row in cur:
      info = dict(zip(column_names, row))
      mensagens.append(info)
    return mensagens


def bd_obter_periodos_id(curso, serie):
    db = bd_conecta()
    cur = db.execute('select id from periodos where (curso = ? and serie = ?)',
                        [curso, serie])
    column_names = [d[0] for d in cur.description]
    mensagens = []
    for row in cur:
      info = dict(zip(column_names, row))
      mensagens.append(info)
    return mensagens


def bd_adicionar_periodo(curso, serie):
    db = bd_conecta()
    db.execute('insert into periodos (curso, serie) values (?, ?)',
                 [curso, serie])
    db.commit()

def bd_deletar_periodo(id):
    db = bd_conecta()
    db.execute('DELETE FROM periodos WHERE id = ?;',
                 [id])
    db.commit()
#################################################################################
def bd_obter_atualizacoes():
    db = bd_conecta()
    cur = db.execute('select * from atualizacoes')
    column_names = [d[0] for d in cur.description]
    mensagens = []
    for row in cur:
      info = dict(zip(column_names, row))
      mensagens.append(info)
    return mensagens

#################################################################################
def bd_obter_eventos():
    db = bd_conecta()
    cur = db.execute('select * from eventos')
    column_names = [d[0] for d in cur.description]
    mensagens = []
    for row in cur:
      info = dict(zip(column_names, row))
      mensagens.append(info)
    return mensagens

def bd_obter_eventos_id(id):
    db = bd_conecta()
    cur = db.execute('select * from eventos where id = ?;',[id])
    column_names = [d[0] for d in cur.description]
    mensagens = []
    for row in cur:
      info = dict(zip(column_names, row))
      mensagens.append(info)
    return mensagens

def bd_obter_eventos_id_periodos(id_periodo):
    db = bd_conecta()
    cur = db.execute('select * from eventos where id_periodo = ?;',[id_periodo])
    column_names = [d[0] for d in cur.description]
    mensagens = []
    for row in cur:
      info = dict(zip(column_names, row))
      mensagens.append(info)
    return mensagens

def bd_adicionar_evento(tipo, data, titulo, descricao, hora, duracao, id_periodo):
    db = bd_conecta()
    db.execute('insert into eventos (tipo, data, titulo, descricao, hora, duracao, id_periodo) values (?, ?, ?, ?, ?, ?, ?)',
                 [int(tipo), data, titulo, descricao, hora, duracao, id_periodo])
    db.execute('update atualizacoes set hora=?,data=? where id=1',
                 [ data, hora])
    db.commit()

def bd_deletar_evento(id):
    db = bd_conecta()
    db.execute('DELETE FROM eventos WHERE id = ?;',
                 [id])
    db.commit()

###################################################
##### INICIO DO BLOCO DAS FUNCOES DO REST API #####
###################################################
@app.route('/')
def pagina_inicial():
    return render_template("index.html", title = 'Inicio')

@app.route('/periodos')
def listar_periodos():
    if 'curso' in request.args and 'serie' in request.args:
        periodos = bd_obter_periodos_id(request.args['curso'], request.args['serie'])
    else:
        periodos = bd_obter_periodos()
    return jsonify(periodos)

@app.route('/add_periodo')
def adiciona_periodo():
    if 'curso' in request.args and 'serie' in request.args:
        bd_adicionar_periodo(request.args['curso'], request.args['serie'])
        return "Periodo Adicionado!"
    else:
        return "Erro: Falta os Parametros: Curso e Serie."

@app.route('/del_periodo')
def deleta_periodo():
    if 'id' in request.args:
        bd_deletar_periodo(request.args['id'])
        return "Periodo Removido."
    else:
        return "Erro: Falta os Parametros: Id."
#############################################################
@app.route('/atualizacoes')
def listar_atualizacoes():
    atualizacoes = bd_obter_atualizacoes()
    return jsonify(atualizacoes)
#############################################################
@app.route('/eventos')
def listar_eventos():
    if 'id' in request.args:
        eventos = bd_obter_eventos_id(request.args['id'])
    elif 'id_periodo' in request.args:
        eventos = bd_obter_eventos_id_periodos(request.args['id_periodo'])
    else:
        eventos = bd_obter_eventos()
    return jsonify(eventos)


@app.route('/add_evento')
def adiciona_evento():
    if 'tipo' in request.args and 'data' in request.args and 'titulo' in request.args and 'descricao' in request.args and 'hora' in request.args and 'duracao' in request.args and 'id_periodo' in request.args:
        bd_adicionar_evento(request.args['tipo'], request.args['data'], request.args['titulo'], request.args['descricao'], request.args['hora'], request.args['duracao'], request.args['id_periodo'])
        return "Evento Adicionado!"
    else:
        return "Erro: Falta os Parametros: tipo, data, titulo, descricao."

@app.route('/del_evento')
def deleta_evento():
    if 'id' in request.args:
        bd_deletar_evento(request.args['id'])
        return "Evento Removido."
    else:
        return "Erro: Falta os Parametros: Id."

@app.errorhandler(404)
def page_not_found(e):
    return "Pagina Nao Encontrada!"

if __name__ == "__main__":
    app.run(host='127.0.0.1', port=8080)
