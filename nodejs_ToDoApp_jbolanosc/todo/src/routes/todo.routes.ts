import { Router } from "express";

const router = Router();

import {
  getTodos,
  createTodo,
  updateTodo,
  deleteTodo,
  renderCreateTodo,
  renderEditTodo,
} from "../controllers/todo.controller";

import { isAuthenticaded } from "../helpers/passport";

router.use(isAuthenticaded);

router.get("/", getTodos);

router.get("/create", renderCreateTodo);
router.post("/create", createTodo);

router.get("/edit/:todoId", renderEditTodo);
router.post("/edit/:todoId", updateTodo);

router.get("/delete/:todoId", deleteTodo);

export default router;
