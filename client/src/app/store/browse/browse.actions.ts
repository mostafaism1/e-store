import { createAction, props } from '@ngrx/store';

export const loadBrowses = createAction(
  '[Browse] Load Browses'
);

export const loadBrowsesSuccess = createAction(
  '[Browse] Load Browses Success',
  props<{ data: any }>()
);

export const loadBrowsesFailure = createAction(
  '[Browse] Load Browses Failure',
  props<{ error: any }>()
);
