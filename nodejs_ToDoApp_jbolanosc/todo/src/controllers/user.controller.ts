import passport from "passport";
import { Request, Response } from "express";

export const renderSignUp = (req: Request, res: Response) => {
  res.render("users/signup");
};

export const renderSignIn = (req: Request, res: Response) => {
  res.render("users/signin");
};

export const signin = passport.authenticate("signin", {
  successRedirect: "/todos",
  failureRedirect: "/users/signin",
  failureFlash: true,
});

export const signup = passport.authenticate("signup", {
  successRedirect: "/todos",
  failureRedirect: "/users/signup",
  failureFlash: true,
});

export const logout = (req: Request, res: Response) => {
  req.logout();
  res.redirect("/users/signin");
};
