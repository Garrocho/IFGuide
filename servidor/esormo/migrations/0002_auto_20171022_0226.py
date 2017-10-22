# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('esormo', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Evento',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('data', models.DateTimeField()),
                ('duracao', models.IntegerField()),
                ('titulo', models.CharField(max_length=50)),
                ('descricao', models.CharField(max_length=200)),
            ],
        ),
        migrations.RenameModel(
            old_name='Periodos',
            new_name='Periodo',
        ),
        migrations.RemoveField(
            model_name='eventos',
            name='periodo',
        ),
        migrations.DeleteModel(
            name='Eventos',
        ),
        migrations.AddField(
            model_name='evento',
            name='periodo',
            field=models.ForeignKey(to='esormo.Periodo'),
        ),
    ]
