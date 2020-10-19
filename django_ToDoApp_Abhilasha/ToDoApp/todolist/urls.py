from django.urls import path
from . import views

urlpatterns = [
    path('new_task', views.new_task, name="new_task"),
    path('<int:id>', views.update_task, name="update_task"),
]