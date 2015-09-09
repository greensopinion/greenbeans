angular.module('greensopinionfinanceApp')
  .service('MockRest',['$q',function ($q) {
    function MockRest(payload) {
        var errorPayload;
        var requestOf = function (path) {
          payload.path = path;
          return $q(function(resolve,reject){
              if (errorPayload !== undefined) {
                reject(errorPayload);
              } else {
                resolve(payload);
              }
          });
        };
        this.payload = payload;
        this.get = function(path) {
          payload.method = 'GET';
          return requestOf(path);
        };
        this.put = function(path,entity) {
          payload.method = 'PUT';
          payload.entity = entity;
          return requestOf(path);
        };
        this.post = function(path,entity) {
          payload.method = 'POST';
          payload.entity = entity;
          return requestOf(path);
        };
        this.delete = function(path) {
          payload.method = 'DELETE';
          return requestOf(path);
        };
        this.setupError = function(message) {
          errorPayload = { errorCode: "Exception", message: message };
        };
    }
    return MockRest;
  }]);
