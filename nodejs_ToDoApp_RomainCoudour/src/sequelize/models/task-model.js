var Sequelize = require("sequelize-cockroachdb");
module.exports = (sequelize) => {
	return sequelize.define("tasks", {
		id: {
			type: Sequelize.INTEGER,
			primaryKey: true,
			allowNull: false,
			autoIncrement: true,
		},
		title: {
			type: Sequelize.STRING,
			allowNull: false,
		},
		description: {
			type: Sequelize.TEXT,
			allowNull: true,
		},
		status: {
			type: Sequelize.ENUM("PENDING", "IN_PROGRESS", "DONE"),
			defaultValue: "PENDING",
			allowNull: false,
		},
		archived: {
			type: Sequelize.BOOLEAN,
			defaultValue: false,
			allowNull: false,
		},
	});
};
