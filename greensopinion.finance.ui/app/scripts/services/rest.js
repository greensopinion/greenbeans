'use strict';

angular.module('greensopinionfinanceApp')
  .service('rest',['$window','$q','$http',function($window,$q,$http) {
    var respondWith = function(resolve,reject,path,responseCode,entity) {
        // console.log('HTTP '+responseCode+' '+path+' '+entity);

        if (responseCode === 200) {
            resolve(JSON.parse(entity));
        } else if (responseCode === 204) {
            resolve(undefined);
        } else {
            reject(JSON.parse(entity));
        }
    };
    var restRequest = function(method,path,entity) {
        return $q(function(resolve,reject) {
            // console.log('-> HTTP '+method+' '+path+ ' entity '+JSON.stringify(entity));

            if ($window.appServiceLocator === undefined) {
                $http(
                  {
                    method: method,
                    url: 'http://localhost:8080'+path,
                    data: entity
                  }
                ).then(function(response) {
                   respondWith(resolve,reject,path,response.status,JSON.stringify(response.data));
                },function(response) {
                   respondWith(resolve,reject,path,response.status,JSON.stringify(response.data));
                });
            } else {
              var webInvoker = $window.appServiceLocator.getWebInvoker();
              var result = webInvoker.invoke(method,path,JSON.stringify(entity));

              respondWith(resolve,reject,path,result.getResponseCode(),result.getEntity());
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
