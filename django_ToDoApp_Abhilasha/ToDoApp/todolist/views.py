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
    # dictionary for initial data with  
    # field names as keys 
    context ={} 
  
    # fetch the object related to passed id 
    obj = get_object_or_404(Task, id = id) 
  
    # pass the object as instance in form 
    form = TaskForm(request.POST or None, instance = obj) 
  
    # save the data from the form and 
    # redirect to detail_view 
    if form.is_valid(): 
        form.save() 
        return redirect('welcome')
  
    # add form dictionary to context 
    context["form"] = form 
  
    return render(request, "todolist/update_task.html", context) 