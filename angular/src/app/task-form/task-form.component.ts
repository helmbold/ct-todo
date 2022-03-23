import {
  Component,
  Inject,
} from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Task } from '../list/task';

@Component({
  selector: 'app-task-form',
  templateUrl:
    './task-form.component.html',
  styleUrls: [
    './task-form.component.css',
  ],
})
export class TaskFormComponent {
  task: Task;

  constructor(
    @Inject(MAT_DIALOG_DATA)
    task: Task
  ) {
    this.task = { ...task };
  }
}
