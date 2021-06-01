import { HttpErrorResponse } from '@angular/common/http';
import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector,
  createSelector,
  MetaReducer
} from '@ngrx/store';
import { environment } from '../../environments/environment';
import { AuthEffects } from './auth/auth.effects';
import { BrowseEffects } from './browse/browse.effects';
import { CartEffects } from './cart/cart.effects';
import { OrderEffects } from './order/order.effects';
import { ShowcaseEffects } from './showcase/showcase.effects';

export interface HttpError {
  error: HttpErrorResponse;
  errorEffect: string;
}

export interface State {

}

export const reducers: ActionReducerMap<State> = {

};


export const metaReducers: MetaReducer<State>[] = !environment.production ? [] : [];

export const effects = [AuthEffects, BrowseEffects, CartEffects, OrderEffects, ShowcaseEffects];
