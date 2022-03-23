import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';

import { TaskFormComponent } from './task-form.component';

describe('TaskFormComponent', () => {
  let component: TaskFormComponent;
  let fixture: ComponentFixture<TaskFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule(
      {
        declarations: [
          TaskFormComponent,
        ],
      }
    ).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(
      TaskFormComponent
    );
    component =
      fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
