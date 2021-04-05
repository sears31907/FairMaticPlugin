var exec = require('cordova/exec');

exports.nativeToast = function (arg0, success, error) {
                 exec(
                    success,
                    error,
                   'FairMaticPlugin',
                   'nativeToast',
                    [arg0]
                    );
};

exports.setupZendrive = function (arg0, success, error) {
                 exec(
                    success,
                    error,
                   'FairMaticPlugin',
                   'setupZendrive',
                    [arg0]
                    );
};