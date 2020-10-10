const Sequelize = require("sequelize-cockroachdb");

const sequelize = new Sequelize("defaultdb", "root", "", {
  dialect: "postgres",
  port: 26257,
  logging: false,
});

const Todo = sequelize.define("todos", {
  id: {
    type: Sequelize.INTEGER,
    primaryKey: true,
    autoIncrement: true,
  },
  title: {
    type: Sequelize.STRING,
  },
  description: {
    type: Sequelize.TEXT,
  },
  isDone: {
    type: Sequelize.BOOLEAN,
    defaultValue: false,
  },
});

Todo.sync();

module.exports = {
  Todo,
};
