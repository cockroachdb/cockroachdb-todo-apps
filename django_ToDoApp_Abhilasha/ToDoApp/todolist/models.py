from django.db import models

# Create your models here.
class Task(models.Model):
    id = models.AutoField(primary_key=True)
    title = models.CharField(max_length=250)
    date = models.DateField()
    status = models.CharField(max_length=1)