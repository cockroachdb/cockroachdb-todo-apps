# Generated by Django 3.0 on 2020-10-01 20:12

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Category',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=100)),
            ],
            options={
                'verbose_name': 'Category',
                'verbose_name_plural': 'Categories',
            },
        ),
        migrations.CreateModel(
            name='TodoList',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=250)),
                ('content', models.TextField(blank=True)),
                ('created_at', models.DateField(default='2020-10-01')),
                ('due_date', models.DateField(default='2020-10-01')),
                ('category', models.ForeignKey(default='general', null=True, on_delete=django.db.models.deletion.SET_NULL, to='todolist.Category')),
            ],
            options={
                'ordering': ['-created_at'],
            },
        ),
    ]