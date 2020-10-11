import { Request, Response } from "express";

export const renderIndex = (req: Request, res: Response) => {
  res.render("index");
};
