import { Task as Task } from './task';

export interface TaskList {
  readonly title: string;
  readonly tasks: Task[];
}
