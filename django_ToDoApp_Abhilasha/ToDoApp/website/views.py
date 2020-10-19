from django.shortcuts import render
from django.http import HttpResponse
from todolist.models import Task

# Create your views here.
def welcome(request):
    return render(request, "website/welcome.html",
                  {"tasks": Task.objects.all()})
                  