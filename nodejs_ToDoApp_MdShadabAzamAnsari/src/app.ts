import express from "express";
import path from "path";
import exphbs from "express-handlebars";
import session from "express-session";
import passport from "passport";
import flash from "connect-flash";
import * as helpers from "./helpers/handlebars";
import "./config/passport";
// Routes
import indexRoutes from "./routes/index.routes";
import todoRoutes from "./routes/todo.routes";
import userRoutes from "./routes/user.routes";

const app = express();

// Settings
app.set("port", process.env.PORT || 3000);
app.set("views", path.join(__dirname, "views"));
app.engine(
  ".hbs",
  exphbs({
    defaultLayout: "main",
    layoutsDir: path.join(app.get("views"), "layouts"),
    partialsDir: path.join(app.get("views"), "partials"),
    extname: ".hbs",
    helpers,
  })
);

app.set("view engine", ".hbs");

// Middlewares
app.use(express.urlencoded({ extended: false }));
app.use(express.json());
app.use(
  session({
    secret: "asecretkey",
    resave: true,
    saveUninitialized: true,
  })
);
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());
app.use((req, res, next) => {
  res.locals.error = req.flash("error");
  res.locals.user = req.user;
  next();
});

// Routes
app.use(indexRoutes);
app.use("/todos", todoRoutes);
app.use("/users", userRoutes);

export default app;
