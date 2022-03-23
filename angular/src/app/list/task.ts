export interface Task {
  readonly id?: number;
  title: string;
  dueDate?: Date;
  isDone: boolean;
}
