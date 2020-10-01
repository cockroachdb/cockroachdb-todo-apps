# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, redirect
from .models import TodoList, Category


def index(request): 
	todos = TodoList.objects.filter(is_active=True)
	categories = Category.objects.all() 
	if request.method == "POST": 
		if "taskAdd" in request.POST:
			print(request.POST.data)
			title = request.POST.get("description")
			date = str(request.POST.get("date"))
			category = request.POST.get("category_select")
			content = title + " -- " + date + " " + category
			todo = TodoList(title=title, content=content, due_date=date, category=Category.objects.get(name=category))
			todo.save()
			return redirect("/")
		
		if "taskDelete" in request.POST:
			checkedlist = request.POST.getlist("checkedbox")
			for todo_id in checkedlist:
				todo = TodoList.objects.get(id=int(todo_id))
				todo.is_active = False
				todo.save()

		if "taskComplete" in request.POST:
			checkedlist = request.POST.getlist("checkedbox")
			for todo_id in checkedlist:
				todo = TodoList.objects.get(id=int(todo_id))
				todo.completed = True
				todo.save()

	return render(request, "index.html", {"todos": todos, "categories": categories})
