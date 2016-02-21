/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
'use strict';

angular.module('greensopinionfinanceApp')
  .service('ErrorModel',function () {
      function ErrorModel(message) {
          this.message = message;
      }
      ErrorModel.prototype = Object.create(Error.prototype);
      return ErrorModel;
  });
