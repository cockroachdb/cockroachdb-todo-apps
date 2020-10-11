const env = require("dotenv").config();
if (env.error) {
	console.error("ERROR: Unabled to parse .env file");
	throw env.error;
}

const express = require("express");
const app = express();

app.get("/", function(req, res) {
	res.send("Hello World");
});

app.listen(process.env.PORT, () => console.info("INFO: Server is listening on port", process.env.PORT));
