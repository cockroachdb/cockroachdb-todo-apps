"""
Copyright [2020] [Abhilasha Sinha]

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, 
software distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and limitations under the License.
"""

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
