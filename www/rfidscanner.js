var rfidscanner = {
    scan: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, 'RfidScanner', 'scan', []);
    }
    radar: function(successCallback, errorCallback, tags) {
         cordova.exec(successCallback, errorCallback, 'RfidScanner', 'scan', tags);
    }
}
module.exports = rfidscanner;