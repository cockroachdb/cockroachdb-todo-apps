# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin

# Register your models here.
from . import models


class TodoListAdmin(admin.ModelAdmin):
	list_display = ("title", "created_at", "due_date")


class CategoryAdmin(admin.ModelAdmin):
	list_display = ("name",)


admin.site.register(models.TodoList, TodoListAdmin)
admin.site.register(models.Category, CategoryAdmin)
