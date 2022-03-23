import { Task } from '../list/task';
import {
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-task',
  templateUrl:
    './task.component.html',
  styleUrls: ['./task.component.css'],
})
export class TaskComponent {
  @Input() task!: Task;
  @Output() edit =
    new EventEmitter<Task>();
  @Output() toggle =
    new EventEmitter<Task>();
  @Output() delete =
    new EventEmitter<Task>();

  editClicked() {
    this.edit.next(this.task);
  }

  deleteClicked() {
    this.delete.next(this.task);
  }

  doneClicked() {
    this.task.isDone =
      !this.task.isDone;
    this.toggle.next(this.task);
  }
}
