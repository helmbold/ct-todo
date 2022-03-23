import { Component, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { ListOverview } from './list-overview';
import { OverviewService } from './overview.service';
import {
  tap,
  mergeMap,
  takeUntil,
} from 'rxjs/operators';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl:
    './navigation.component.html',
  styleUrls: [
    './navigation.component.css',
  ],
})
export class NavigationComponent implements OnDestroy {
  isVisible = true;
  title = '';
  readonly lists$ = new Subject<
    ListOverview[]
  >();
  private terminator =
    new Subject<void>();

  constructor(
    private readonly service: OverviewService,
    router: Router
  ) {
    service
      .findAll()
      .pipe(
        tap((lists) =>
          this.lists$.next(lists)
        )
      )
      .subscribe();
    router.events.subscribe(
      () => (this.isVisible = false)
    );
  }

  ngOnDestroy(): void {
    this.terminator.next(undefined);
    this.terminator.complete();
  }

  toggle(): void {
    this.isVisible = !this.isVisible;
  }

  create(form: NgForm) {
    this.service
      .create(this.title)
      .pipe(
        tap(() => form.resetForm()),
        mergeMap(() =>
          this.service.findAll()
        ),
        tap((lists) =>
          this.lists$.next(lists)
        ),
        takeUntil(this.terminator)
      )
      .subscribe();
  }
}
