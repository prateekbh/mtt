angular.module('myApp.resources', ['ngResource']).factory('Api', Api)

Api.$inject = ['$resource', 'API_BASE_URL'];

function Api($resource, API_BASE_URL) {
  return {
    Login: $resource(API_BASE_URL + '/login', null, {
             'login': {'method': 'POST'}
           }),

    Register: $resource(API_BASE_URL + '/signup', null, {
                'register': {'method': 'POST'}
              }),

    Customers: $resource(API_BASE_URL + '/customers/:customerId',
                   { customerId: '@id'}, {
                      'post' : {'method': 'POST'}
                      }),

    Students: $resource(API_BASE_URL + '/students/:studentId',
                   { studentId: '@id'}, {
                      'query' : {'method': 'GET'},
                      'post' : {'method': 'POST'}
                   }),            
    Volunteers: $resource(API_BASE_URL + '/volunteers/:volunteerId',
                       { volunteerId: '@id'}, {
                          'post' : {'method': 'POST'}
                       })
  };
}
