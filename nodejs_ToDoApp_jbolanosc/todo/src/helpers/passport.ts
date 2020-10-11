import { Request, Response, NextFunction } from "express";

export const isAuthenticaded = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (req.isAuthenticated()) {
    return next();
  }
  res.redirect("/users/signin");
};
