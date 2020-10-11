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
const commentModel = require("./models/comment-model")(sequelize, taskModel);
if (process.env.DB_INIT === "CREATE-DROP") {
	try {
		console.warn("WARN: Database init process is set to CREATE-DROP. Syncing models...");
		sequelize.sync({ force: true });
		console.info("INFO: Sync successful");
	} catch (err) {
		console.error("ERROR:", err);
	}
}
module.exports = { sequelize, models: { taskModel, commentModel } };
