import { createAction, props } from '@ngrx/store';
import { HttpError } from '..';

export const signUp = createAction(
  '[Auth] Sign Up',
  props<{ email: string; password: string; passwordRepeat: string }>()
);

export const signUpSuccess = createAction(
  '[Auth] Sign Up Success',
  props<{ effect: string }>()
);

export const signIn = createAction(
  '[Auth] Sign In',
  props<{ email: string; password: string }>()
);

export const signInSuccess = createAction(
  '[Auth] Sign In Success',
  props<{ effect: string }>()
);

export const signOut = createAction('[Auth] Sign Out', props<{ error: any }>());

export const signOutSuccess = createAction(
  '[Auth] Sign Out Success',
  props<{ error: any }>()
);

export const checkIfLoggedIn = createAction(
  '[Auth] Check If Logged In',
  props<{ error: any }>()
);

export const authError = createAction(
  '[Auth] Auth Error',
  props<{ error: HttpError }>()
);

export const fetchVerificationStatus = createAction(
  '[Auth] Fetch Verification Status',
  props<{ error: any }>()
);

export const fetchVerificationStatusSuccess = createAction(
  '[Auth] Fetch Verification Status Success',
  props<{ isActive: boolean }>()
);
