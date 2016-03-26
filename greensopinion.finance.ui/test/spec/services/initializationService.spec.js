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

describe('Service: initializationService', function () {

  // load the service's module
  beforeEach(module('greensopinionfinanceApp'));

  // instantiate service
  var initializationService;
  beforeEach(inject(function (_initializationService_) {
    initializationService = _initializationService_;
  }));

  it('should expose isInitialized()', function () {
    expect(initializationService.isInitialized()).toBe(false);
    initializationService.initialized(true);
    expect(initializationService.isInitialized()).toBe(true);
    initializationService.initialized(false);
    expect(initializationService.isInitialized()).toBe(false);
  });
});
