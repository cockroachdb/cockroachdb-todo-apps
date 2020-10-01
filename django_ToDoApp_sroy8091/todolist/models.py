# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone


# Create your models here.
class Category(models.Model):
	name = models.CharField(max_length=100)

	class Meta:
		verbose_name = "Category"
		verbose_name_plural = "Categories"

	def __str__(self):
		return self.name


class TodoList(models.Model):
	title = models.CharField(max_length=250)
	content = models.TextField(blank=True)
	created_at = models.DateField(default=timezone.now().strftime("%Y-%m-%d"))
	is_active = models.BooleanField(default=True)
	due_date = models.DateField(default=timezone.now().strftime("%Y-%m-%d"))
	category = models.ForeignKey(Category, default="general", null=True, on_delete=models.SET_NULL)
	completed = models.BooleanField(default=False)

	class Meta:
		ordering = ["-created_at"]

	def __str__(self):
		return self.title
