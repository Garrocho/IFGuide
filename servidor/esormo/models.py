from django.db import models


class Periodo(models.Model):
    curso = models.CharField(max_length=50)
    serie = models.IntegerField()


class Evento(models.Model):
    periodo = models.ForeignKey(Periodo, on_delete=models.CASCADE)
    data = models.DateTimeField()
    duracao = models.IntegerField()
    titulo = models.CharField(max_length=50)
    descricao = models.CharField(max_length=200)