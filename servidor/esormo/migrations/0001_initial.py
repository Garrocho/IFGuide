# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Eventos',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('data', models.DateTimeField()),
                ('duracao', models.IntegerField()),
                ('titulo', models.CharField(max_length=50)),
                ('descricao', models.CharField(max_length=200)),
            ],
        ),
        migrations.CreateModel(
            name='Periodos',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('curso', models.CharField(max_length=50)),
                ('serie', models.IntegerField()),
            ],
        ),
        migrations.AddField(
            model_name='eventos',
            name='periodo',
            field=models.ForeignKey(to='esormo.Periodos'),
        ),
    ]
