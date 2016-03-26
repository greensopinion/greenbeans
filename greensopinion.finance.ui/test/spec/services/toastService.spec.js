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

describe('Service: toastService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var toastService, timerCallback,timerDelay;

  beforeEach(module(function ($provide) {
    $provide.factory('$timeout', function() {
      return function(callback,delay) {
        timerCallback = callback;
        timerDelay = delay;
      };
    });
  }));
  beforeEach(inject(function (_toastService_) {
    toastService = _toastService_;
  }));

  it('should expose show() and message()', function () {
    expect(toastService.show).toBeDefined();
    expect(toastService.message).toBeDefined();
    expect(toastService.clearMessage).toBeDefined();
    expect(toastService.message()).toBe('');

    toastService.show('some text');
    expect(toastService.message()).toBe('some text');

    expect(timerCallback).toBeDefined();
    expect(timerDelay).toBe(3500);
    timerCallback();

    expect(toastService.message()).toBe('');
  });

});
