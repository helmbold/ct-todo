import { Task } from './task';
import { TaskFormComponent } from './../task-form/task-form.component';
import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  Observable,
  Subject,
} from 'rxjs';
import {
  map,
  take,
  tap,
  mergeMap,
  takeUntil,
} from 'rxjs/operators';
import { ListService } from './list.service';
import { TaskList } from './task-list';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-list',
  templateUrl:
    './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnDestroy {
  readonly list$: Subject<TaskList> =
    new Subject<TaskList>();
  private listId = -1;
  private terminator =
    new Subject<void>();

  private load: () => Observable<TaskList> =
    () =>
      this.service
        .findById(this.listId)
        .pipe(
          tap((list) =>
            this.list$.next(list)
          ),
          take(1),
          takeUntil(this.terminator)
        );

  constructor(
    route: ActivatedRoute,
    readonly service: ListService,
    readonly dialog: MatDialog
  ) {
    route.paramMap
      .pipe(
        map((params) =>
          Number(params.get('listId'))
        ),
        tap(
          (id) => (this.listId = id)
        ),
        mergeMap(this.load)
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    this.terminator.next(undefined);
    this.terminator.complete();
  }

  edit(task?: Task) {
    this.dialog
      .open<TaskFormComponent>(
        TaskFormComponent,
        { data: task }
      )
      .afterClosed()
      .pipe(
        mergeMap((task) => {
          if (task.id === undefined)
            return this.service.create(
              this.listId,
              task
            );
          else
            return this.service.update(
              this.listId,
              task
            );
        }),
        mergeMap(this.load),
        takeUntil(this.terminator)
      )
      .subscribe();
  }

  update(task: Task) {
    this.service
      .update(this.listId, task)
      .pipe(
        mergeMap(this.load),
        takeUntil(this.terminator)
      )
      .subscribe();
  }

  delete(task: Task) {
    this.service
      .delete(this.listId, task)
      .pipe(
        mergeMap(this.load),
        takeUntil(this.terminator)
      )
      .subscribe();
  }
}
