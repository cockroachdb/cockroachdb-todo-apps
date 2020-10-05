create database todoapp;

create table todoapp.item (id UUID NOT NULL, description string, is_done boolean , update_dt timestamp , constraint "primary" PRIMARY KEY (id asc));
