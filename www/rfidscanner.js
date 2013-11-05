var rfidscanner = {
    scan: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, 'RfidScanner', 'scan', []);
    }
}
module.exports = rfidscanner;