import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateTodoDto } from './dto/create-todo.dto';
import { Todo } from './todo.entity';

@Injectable()
export class TodosService {
  constructor(
    @InjectRepository(Todo)
    private todosRepository: Repository<Todo>,
  ) {}

  findAll(): Promise<Todo[]> {
    return this.todosRepository.find();
  }

  async remove(id: string): Promise<void> {
    await this.todosRepository.delete(id);
  }

  async markAsDone(id: string): Promise<void> {
    await this.todosRepository.update(id, { isDone: true });
  }

  create(todo: CreateTodoDto): Promise<Todo> {
    return this.todosRepository.save(todo);
  }
}
