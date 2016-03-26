/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
