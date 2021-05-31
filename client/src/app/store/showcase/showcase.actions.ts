import { createAction, props } from '@ngrx/store';

export const loadShowcases = createAction(
  '[Showcase] Load Showcases'
);

export const loadShowcasesSuccess = createAction(
  '[Showcase] Load Showcases Success',
  props<{ data: any }>()
);

export const loadShowcasesFailure = createAction(
  '[Showcase] Load Showcases Failure',
  props<{ error: any }>()
);
