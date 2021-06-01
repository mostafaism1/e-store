import { createReducer, on } from '@ngrx/store';
import { HttpError } from '..';
import {
  authError,
  checkIfLoggedIn,
  fetchVerificationStatus,
  fetchVerificationStatusSuccess,
  signIn,
  signInSuccess,
  signOut,
  signOutSuccess,
  signUp,
  signUpSuccess,
} from './auth.actions';

export const authFeatureKey = 'auth';

export interface AuthState {
  authenticated: boolean;
  isActive: boolean;
  errors: HttpError[];
  loading: boolean;
}

export const initialState: AuthState = {
  authenticated: false,
  isActive: false,
  errors: [],
  loading: false,
};

export const reducer = createReducer(
  initialState,
  on(signUp, signIn, signOut, (authState) => {
    return { ...authState, loading: true };
  }),
  on(signUpSuccess, (authState, { effect }) => {
    return {
      ...authState,
      errors: authState.errors.filter((e) => e.errorEffect != effect),
      loading: false,
    };
  }),
  on(signInSuccess, (authState, { effect }) => {
    return {
      ...authState,
      authenticated: true,
      errors: authState.errors.filter((e) => e.errorEffect != effect),
      loading: false,
    };
  }),
  on(authError, (authState, { error }) => {
    return {
      ...authState,
      loading: false,
      errors: [
        ...authState.errors.filter((e) => e.errorEffect != error.errorEffect),
        error,
      ],
    };
  }),
  on(signOutSuccess, (authState) => initialState),
  on(fetchVerificationStatusSuccess, (authState, { isActive }) => {
    return {
      ...authState,
      isActive: isActive,
    };
  }),
  on(
    signUpSuccess,
    checkIfLoggedIn,
    fetchVerificationStatus,
    (authState) => authState
  )
);
