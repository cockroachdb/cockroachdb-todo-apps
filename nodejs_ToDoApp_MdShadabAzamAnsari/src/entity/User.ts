import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  OneToMany,
  BeforeInsert,
} from "typeorm";
import { Todo } from "./Todo";
import bcrypt from "bcrypt";

@Entity()
export class User {
  @PrimaryGeneratedColumn()
  id: string;

  @Column()
  username: string;

  @Column()
  password: string;

  @OneToMany((type) => Todo, (todo) => todo.user)
  todos: Todo[];

  @BeforeInsert()
  async encryptPassword() {
    this.password = bcrypt.hashSync(this.password, 10);
  }

  async checkPassword(password: string) {
    return await bcrypt.compareSync(password, this.password);
  }
}
