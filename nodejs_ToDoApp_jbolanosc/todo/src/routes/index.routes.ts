import { Router } from "express";
const router = Router();

import { renderIndex } from "../controllers/index.controllers";

router.get("/", renderIndex);

export default router;
