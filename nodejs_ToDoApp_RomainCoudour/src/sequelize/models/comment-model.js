var Sequelize = require("sequelize-cockroachdb");
module.exports = (sequelize, taskModel) => {
	return sequelize.define("comments", {
		id: {
			type: Sequelize.INTEGER,
			primaryKey: true,
			allowNull: false,
			autoIncrement: true,
		},
		content: {
			type: Sequelize.TEXT,
			allowNull: false,
		},
		username: {
			type: Sequelize.TEXT,
			allowNull: false,
		},
		task_id: {
			type: Sequelize.INTEGER,
			allowNull: false,
			references: {
				model: taskModel,
				key: "id",
			},
		},
	});
};
