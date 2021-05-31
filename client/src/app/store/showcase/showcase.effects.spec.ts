import { TestBed } from '@angular/core/testing';
import { provideMockActions } from '@ngrx/effects/testing';
import { Observable } from 'rxjs';

import { ShowcaseEffects } from './showcase.effects';

describe('ShowcaseEffects', () => {
  let actions$: Observable<any>;
  let effects: ShowcaseEffects;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ShowcaseEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.inject(ShowcaseEffects);
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
});
