RFIDScannerPlugin
=================

RFID Scanner plugin for PhoneGap

This PhoneGap plugin allows third-party software developers to easily add UHF RFID reading features using MTI’s RFID ME&#153; mobile readers.

It enables two main features: Scan and Radar

Scan makes RIFD tags reading, retrieving its EPCs.

`success` and `fail` are callback functions. Success is passed an object with data and cancelled properties. Data is the json array with a list of epc tag data and cancelled is whether or not the user cancelled the scan.

A full example to scan could be:
...

    function callScan() {
        try {
          rfidscanner.scan(winScan, failScan);
        } catch(err) {
          alert("Error: " + err);
        }
    }

    var winScan = function (result) {
                      if (!result.cancelled) {
                          var v = "";
                          for(var i = 0; i < result.tags.length; i++) {
                              v += result.tags[i] + "\n";
                          }
                          document.getElementById('epcs').value = v;
                      } else {
                          alert("Leitura Cancelada.");
                      }
                  };

    var failScan = function (error) {
                       alert("Scanning failed: " + error);
                   };
                   
...

Radar lets user search for the items in a list

`success` and `fail` are callback functions. Success is passed an object with cancelled properties. Cancelled is whether or not the user cancelled the scan.

A full example to radar could be:
...

    function callRadar() {
        try {
          var tags = document.getElementById('epcs').value.split("\n");
          rfidscanner.radar(winScan, failScan, tags);
        } catch(err) {
          alert("Error: " + err);
        }
    }

    var winRadar = function (result) {
                      if (result.cancelled) {
                          alert("Leitura Cancelada.");
                      }
                  };

    var failRadar = function (error) {
                       alert("Scanning failed: " + error);
                   };
 ...

Install
========
Assuming the PhoneGap CLI is installed, from the command line run:

phonegap local plugin add https://github.com/eficid/RFIDScannerPlugin
