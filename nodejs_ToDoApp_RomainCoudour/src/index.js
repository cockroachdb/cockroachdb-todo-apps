/** Copyright 2020 - Romain Coudour
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
const env = require("dotenv").config();
if (env.error) {
	console.error("ERROR: Unable to parse .env file");
	throw env.error;
}

const { models } = require("./sequelize/sequelize");
const express = require("express");
const app = express();
app.use(express.json());

app.get("/tasks", function(req, res) {
	models.taskModel
		.findAll()
		.then((data) => {
			if (Array.isArray(data)) res.send(data);
			else throw Error("Data not received");
		})
		.catch(() => res.sendStatus(500));
});

app.post("/tasks", function(req, res) {
	models.taskModel
		.create(req.body)
		.then(({ dataValues }) => res.send(dataValues))
		.catch(() => res.sendStatus(500));
});

app.get("/tasks/:taskId", function(req, res) {
	const taskId = req.params.taskId;
	models.taskModel
		.findOne({ where: { id: taskId } })
		.then(({ dataValues }) => res.send(dataValues))
		.catch(() => res.sendStatus(500));
});

app.patch("/tasks/:taskId", function(req, res) {
	const taskId = req.params.taskId;
	models.taskModel
		.update({ where: { id: taskId } })
		.then(({ dataValues }) => res.send(dataValues))
		.catch(() => res.sendStatus(500));
});

app.delete("/tasks/:taskId", function(req, res) {
	const taskId = req.params.taskId;
	models.taskModel
		.destroy({ where: { id: taskId } })
		.then(() => res.send("Deletion successful"))
		.catch(() => res.sendStatus(500));
});

app.listen(process.env.APP_PORT, () => console.info("INFO: Server is listening on port", process.env.APP_PORT));
