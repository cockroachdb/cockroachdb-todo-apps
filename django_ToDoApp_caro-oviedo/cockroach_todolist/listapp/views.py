"""
Copyright [2020] [Carolina Oviedo]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
"""

from django.shortcuts import render, redirect 
from django.http import HttpResponse
from .models import *
from .forms import *

def index(request):
    taskstodo = TasksTodo.objects.order_by('-date_added')  #show tasks

    form = TasksTodoForm()

    if request.method == 'POST':
        form = TasksTodoForm(request.POST)
        if form.is_valid():
            form.save()
        return redirect('/')

    context ={'taskstodo': taskstodo, 'form':form}
    return render(request, 'listapp/indexapp.html', context)



def edit(request, taskstodo_id):
    taskstodo = TasksTodo.objects.get(id=taskstodo_id)

    form = TasksTodoForm(instance=taskstodo)
    if request.method =='POST':
        form = TasksTodoForm(request.POST, instance=taskstodo)
        if form.is_valid():
            form.save()
        return redirect('/')

    context = {'form': form}

    return render(request, 'listapp/edit.html', context)

 
def deletetask(request, taskstodo_id): 
    itemtodelete = TasksTodo.objects.get(id=taskstodo_id)
    itemtodelete.delete()
    form = TasksTodoForm()
         
    context = {'form':form}
    return redirect('/')

   
    return render(request, 'listapp/indexapp.html', context)
     



def completetask(request, taskstodo_id):
    completetask = TasksTodo.objects.get(id=taskstodo_id)
    completetask.complete = True
    completetask.save()
    

    return redirect('/')



    
    

   


