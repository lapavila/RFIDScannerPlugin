cordova.define("cordova/plugin/rfidscanner", function(require, exports, module) {

    var exec = require('cordova/exec');

    function RfidScanner() {

    };

    RfidScanner.prototype.scan = function (successCallback, errorCallback) {
        if (errorCallback == null) {
            errorCallback = function () {
            };
        }

        if (typeof errorCallback != "function") {
            console.log("RfidScanner.scan failure: failure parameter not a function");
            return;
        }

        if (typeof successCallback != "function") {
            console.log("RfidScanner.scan failure: success callback parameter must be a function");
            return;
        }

        exec(successCallback, errorCallback, 'RfidScanner', 'scan', []);
    };

    var rfidScanner = new RfidScanner();
    module.exports = rfidScanner;

});