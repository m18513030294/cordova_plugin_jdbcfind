var exec = require('cordova/exec');

var myJdbc = function(){}; 

myJdbc.prototype.add = function(arg0, success, error) {
    exec(success, error, "jdbcfind", "add", arg0);
};

myJdbc.prototype.delete = function(arg0, success, error) {
    exec(success, error, "jdbcfind", "delete", arg0);
};

myJdbc.prototype.update = function(arg0, success, error) {
    exec(success, error, "jdbcfind", "update", arg0);
};

myJdbc.prototype.select = function(arg0, success, error) {
    exec(success, error, "jdbcfind", "select", arg0);
};

var myjdbc = new myJdbc();
module.exports = myjdbc; 

// exports.coolMethod = function(arg0, success, error) {
//     exec(success, error, "jdbcfind", "coolMethod", [arg0]);
// };
