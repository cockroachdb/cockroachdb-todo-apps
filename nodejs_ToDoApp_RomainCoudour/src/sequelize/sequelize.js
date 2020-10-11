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
var Sequelize = require("sequelize-cockroachdb");
var fs = require("fs");
const sequelize = new Sequelize(process.env.DB_NAME, process.env.DB_USER, process.env.DB_PASSWORD, {
	dialect: "postgres",
	host: process.env.DB_URL,
	port: process.env.DB_PORT,
	logging: false,
	dialectOptions:
		process.env.DB_SSL_MODE === "ENABLED"
			? {
					ssl: {
						ca: fs.readFileSync(process.env.DB_CERT_LOCATION).toString(),
					},
			  }
			: {},
});
sequelize.authenticate();
console.info("INFO: Connection to the database has been established successfully.");
const taskModel = require("./models/task-model")(sequelize);
if (process.env.DB_INIT === "CREATE-DROP") {
	try {
		console.warn("WARN: Database init process is set to CREATE-DROP. Syncing models...");
		sequelize.sync({ force: true });
		console.info("INFO: Sync successful");
	} catch (err) {
		console.error("ERROR:", err);
	}
}
module.exports = { sequelize, models: { taskModel } };
