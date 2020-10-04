from django.db import models

class Items(models.Model):
    id = models.AutoField(primary_key=True)
    description = models.CharField(max_length=50)