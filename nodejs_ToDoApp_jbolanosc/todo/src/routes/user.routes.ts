import { Router } from "express";
const router = Router();

import {
  renderSignUp,
  renderSignIn,
  signin,
  logout,
  signup,
} from "../controllers/user.controller";

router.get("/signup", renderSignUp);
router.post("/signup", signup);

router.get("/signin", renderSignIn);
router.post("/signin", signin);

router.get("logout", logout);

export default router;
