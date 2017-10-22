from django.shortcuts import render
from django.http import HttpResponse
from .models import Evento
from django.template import loader


def index(request):
    latest_evento_list = Evento.objects.all()
    template = loader.get_template('index.html')
    context = {
        'latest_evento_list': latest_evento_list,
    }
    return HttpResponse(template.render(context, request))