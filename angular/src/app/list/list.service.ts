import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Task } from './task';
import { TaskList } from './task-list';

@Injectable({
  providedIn: 'root',
})
export class ListService {
  constructor(
    private readonly httpClient: HttpClient
  ) {}

  findById(
    listId: number
  ): Observable<TaskList> {
    return this.httpClient.get<TaskList>(
      `${environment.baseUrl}${listId}`
    );
  }

  create(
    listId: number,
    task: Task
  ): Observable<void> {
    return this.httpClient.post<void>(
      `${environment.baseUrl}${listId}`,
      task
    );
  }

  update(
    listId: number,
    task: Task
  ): Observable<void> {
    return this.httpClient.put<void>(
      `${environment.baseUrl}${listId}/${task.id}`,
      task
    );
  }

  delete(
    listId: number,
    task: Task
  ): Observable<void> {
    return this.httpClient.delete<void>(
      `${environment.baseUrl}${listId}/${task.id}`
    );
  }
}
