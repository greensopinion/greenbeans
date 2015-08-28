'use strict';

angular.module('greensopinionfinanceApp')
  .service('rest',['$window','$q',function($window,$q) {
    var restRequest = function(method,path,entity) {
        return $q(function(resolve,reject) {
            var webInvoker = $window.appServiceLocator.getWebInvoker();
            var result = webInvoker.invoke(method,path,JSON.stringify(entity));
            var responseCode = result.getResponseCode();

            //console.log('HTTP '+responseCode+' '+method+' '+path+' result: '+result.getEntity());

            if (responseCode === 200) {
                resolve(JSON.parse(result.getEntity()));
            } else if (responseCode === 204) {
                resolve(undefined);
            } else {
                reject(JSON.parse(result.getEntity()));
            }
          }
        );
    };
    return {
      get: function(path) {
        return restRequest('GET',path);
      },
      put: function(path,entity) {
        return restRequest('PUT',path,entity);
      },
      post: function(path,entity) {
        return restRequest('POST',path,entity);
      },
      delete: function(path) {
        return restRequest('DELETE',path);
      }
    };
  }]);
