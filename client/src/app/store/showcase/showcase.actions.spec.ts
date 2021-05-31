import * as fromShowcase from './showcase.actions';

describe('loadShowcases', () => {
  it('should return an action', () => {
    expect(fromShowcase.loadShowcases().type).toBe('[Showcase] Load Showcases');
  });
});
