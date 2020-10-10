// Require the framework and instantiate it
const fastify = require("fastify")({ logger: true });
const { Todo } = require("./model");

// Declare a route
fastify.get("/todos", async () => {
  return { todos: await Todo.findAll() };
});

fastify.post("/todos", async (request) => {
  const todo = Todo.build(request.body);
  await todo.save();
  return todo;
});

fastify.delete("/todos/:id", async (request, reply) => {
  await Todo.destroy({
    where: { id: request.params.id },
  });
  reply.code(204);
  reply.send();
});

fastify.patch("/todos/:id", async (request, reply) => {
  await Todo.update(
    { isDone: true },
    {
      where: { id: request.params.id },
    }
  );
  reply.code(204);
  reply.send();
});

// Run the server!
const start = async () => {
  try {
    await fastify.listen(3000);
    fastify.log.info(`server listening on ${fastify.server.address().port}`);
  } catch (err) {
    fastify.log.error(err);
    process.exit(1);
  }
};
start();
