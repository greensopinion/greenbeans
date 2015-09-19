'use strict';

describe('Service: money', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var money;
  beforeEach(inject(function (_money_) {
    money = _money_;
  }));

  it('should format()', function () {
    expect(money.format(0)).toBe('0.00');
    expect(money.format(1)).toBe('0.01');
    expect(money.format(-10023)).toBe('-100.23');
    expect(money.format(12340)).toBe('123.40');
  });

});
