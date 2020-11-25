/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready
document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');

    document.getElementById("connect_spotify").addEventListener("click", connect);
    document.getElementById("play_spotify").addEventListener("click", play);
    document.getElementById("pause_spotify").addEventListener("click", pause);

    function connect() {
    	var CLIENT_ID = "1d1b155c59cd4a719f1e1be5b28a4b83";
        var REDIRECT_URI = "https://www.crymzee.com";
        var request = {"client_id": CLIENT_ID, "redirection_url": REDIRECT_URI}
		Cordova.exec(connectionSuccessCallback, error, "CustomPlugin", "ConnectionRequest", [request]);
	};

	function play() {
	    var request = {"track": "spotify:track:3qRNQHagYiiDLdWMSOkPGG"};
	    Cordova.exec(generalSuccess, error, "CustomPlugin", "PlayRequest", [request]);
	}

	function pause() {
        Cordova.exec(generalSuccess, error, "CustomPlugin", "PauseRequest", []);
	}

	function connectionSuccessCallback(result) {
		alert(result);
	}
	function error(result) {
		alert(result);
	}
	function generalSuccess(result) {
	    alert(result);
	}
}