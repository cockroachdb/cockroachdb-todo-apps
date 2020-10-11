import { Request, Response } from "express";
import { Todo } from "../entity/Todo";
import { User } from "../entity/User";
import { getRepository } from "typeorm";

export const getTodos = async (req: Request, res: Response): Promise<void> => {
  if (req.user) {
    const todos = await getRepository(Todo).find({
      where: { user: req.user },
    });
    return res.render("todos/list", { todos });
  }
  return res.redirect("/users/signin");
};

export const renderCreateTodo = (req: Request, res: Response) => {
  res.render("todos/create");
};

export const createTodo = async (req: Request, res: Response) => {
  const user = await getRepository(User).findOne(req.user);
  if (user) {
    const todo = new Todo();
    todo.title = req.body.title;
    todo.body = req.body.body;
    todo.tag = req.body.tag;
    todo.user = user;

    await getRepository(Todo).save(todo);
    return res.redirect("/todos");
  }
  return res.redirect("users/signin");
};

export const renderEditTodo = async (
  req: Request,
  res: Response
): Promise<void | Response> => {
  const todo = await getRepository(Todo).findOne(req.params.todoId);
  if (todo) {
    return res.render("todos/edit", { todo });
  }
  return res.send("TODO NOT FOUND");
};

export const updateTodo = async (req: Request, res: Response) => {
  console.log("req.params", req.params.todoId);
  const todo = await getRepository(Todo).findOne(req.params.todoId);
  console.log(todo);
  if (todo) {
    console.log("body", req.body);
    todo.title = req.body.title;
    todo.body = req.body.body;
    todo.tag = req.body.tag;
    await getRepository(Todo).save(todo);
  }
  res.redirect("/todos");
};

export const deleteTodo = async (
  req: Request,
  res: Response
): Promise<void | Response> => {
  const todo = await getRepository(Todo).findOne(req.params.todoId);
  if (todo) {
    await getRepository(Todo).remove(todo);
    return res.redirect("/todos");
  }
  return res.send("404 todo not found");
};
