from django.shortcuts import render, redirect, get_object_or_404, HttpResponseRedirect
from .models import Task
from .forms import TaskForm
# Create your views here.
def new_task(request):
    if request.method == "POST":
        form = TaskForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('welcome')
    else:
        form = TaskForm()
    return render(request, 'todolist/new_task.html', {"form": form})

def update_task(request, id):
    context ={} 
    obj = get_object_or_404(Task, id = id) 
    form = TaskForm(request.POST or None, instance = obj) 
    if form.is_valid(): 
        form.save() 
        return redirect('welcome')
    context["form"] = form 
    return render(request, "todolist/update_task.html", context) 
