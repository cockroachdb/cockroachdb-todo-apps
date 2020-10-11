const env = require("dotenv").config();
if (env.error) {
	console.error("ERROR: Unable to parse .env file");
	throw env.error;
}

const express = require("express");
const app = express();

const db = require("./sequelize/sequelize");
console.log(db);
app.get("/", function(req, res) {
	res.send("Hello World");
});

app.listen(process.env.APP_PORT, () => console.info("INFO: Server is listening on port", process.env.APP_PORT));
