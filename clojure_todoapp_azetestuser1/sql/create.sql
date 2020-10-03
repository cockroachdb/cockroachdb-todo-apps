create database todoapp;
create table todoapp.item (id UUID NOT NULL DEFAULT gen_random_uuid() , description string, is_done boolean , update_dt time , constraint "primary" PRIMARY KEY (update_dt desc,id asc));
