import { getRepository } from "typeorm";
import passport, { use } from "passport";
import { Strategy } from "passport-local";

import { User } from "../entity/User";

passport.use(
  "signup",
  new Strategy(
    {
      usernameField: "username",
      passwordField: "password",
    },
    async (username, password, done) => {
      const userFound = await getRepository(User).findOne({ username });
      if (userFound) {
        return done(null, false, { message: "The Username is already Taken" });
      } else {
        const newUser = new User();
        newUser.username = username;
        newUser.password = password;
        const userSaved = await getRepository(User).save(newUser);
        return done(null, userSaved);
      }
    }
  )
);

passport.use(
  "signin",
  new Strategy(
    {
      usernameField: "username",
      passwordField: "password",
    },
    async (username: string, password: string, done: any) => {
      const user = await getRepository(User).findOne({ username });
      if (!user) {
        return done(null, false, { message: "Incorrect user or password" });
      } else {
        const checkPassword = await user.checkPassword(password);
        if (checkPassword) return done(null, user);
        return done(null, false, { message: "Incorrect user or password" });
      }
    }
  )
);

passport.serializeUser((user: User, done) => {
  done(null, user.id);
});

passport.deserializeUser(async (id: string, done) => {
  try {
    const user = await getRepository(User).findOne(id);
    if (user) return done(null, { username: user.username, id: user.id });
    return done(null, false);
  } catch (err) {
    return done(err, false);
  }
});
