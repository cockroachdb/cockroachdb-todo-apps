import "reflect-metadata";
import { createConnection } from "typeorm";
import app from "./app";

async function main() {
  app.listen(app.get("port"));
  console.log("Server on port ", app.get("port"));
  const connection = await createConnection();

  console.log("Database Connected");
}

main();
