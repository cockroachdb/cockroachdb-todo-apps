from datetime import date
from django.forms import ModelForm, DateInput, TextInput
from django.core.exceptions import ValidationError
from .models import Task

class TaskForm(ModelForm):
    class Meta:
        model = Task
        fields = '__all__'
        widgets = {
            'date': DateInput(attrs={'type': 'date'}),
            'status': TextInput(attrs={'type': 'character'})   
        }
 