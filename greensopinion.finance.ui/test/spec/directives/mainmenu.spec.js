'use strict';

describe('Directive: mainmenu', function () {

  // load the directive's module
  beforeEach(module('greensopinionfinanceApp'));

  var $rootScope, $location, element,
    scope;

  beforeEach(inject(function (_$rootScope_,_$location_) {
    $rootScope = _$rootScope_;
    $location = _$location_;
    scope = $rootScope.$new();
  }));

  it('should expose getClass',inject(function ($compile) {
    element = angular.element('<mainmenu></mainmenu>');
    element = $compile(element)(scope);
    $rootScope.$digest();

    expect(scope.getClass).toBeDefined();
    expect(scope.getClass('/not-here')).toBe('');

    $location.path('/');
    expect(scope.getClass('/')).toBe('active');
    expect(scope.getClass('/about')).toBe('');

    $location.path('/about');
    expect(scope.getClass('/')).toBe('');
    expect(scope.getClass('/about')).toBe('active');
  }));

  it('should have menu elements', inject(function ($compile) {
    element = angular.element('<mainmenu></mainmenu>');
    element = $compile(element)(scope);
    $rootScope.$digest();

    var homeNav = element.find('a:contains("Home")');
    expect(homeNav.attr('href')).toBe('#/');

    var aboutNav = element.find('a:contains("About")');
    expect(aboutNav.attr('href')).toBe('#/about');
  }));

});
