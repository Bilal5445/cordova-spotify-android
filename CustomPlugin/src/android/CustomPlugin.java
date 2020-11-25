package info.androidabcd.plugins.custom;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

/**
 * This class echoes a string called from JavaScript.
 */
public class CustomPlugin extends CordovaPlugin {
    private static String CLIENT_ID = "1d1b155c59cd4a719f1e1be5b28a4b83";
    private static String REDIRECT_URI = "https://www.crymzee.com";
    private SpotifyAppRemote mSpotifyAppRemote;
    private ConnectionParams connectionParams;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("ConnectionRequest")) {
            JSONObject obj = args.getJSONObject(0);
            String client_id = obj.getString("client_id");
            String redirection_url = obj.getString("redirection_url");
            ConnectionRequest(client_id, redirection_url, callbackContext);
            return true;
        }
        if (action.equals("PlayRequest")) {
            JSONObject obj = args.getJSONObject(0);
            String track = obj.getString("track");
            mSpotifyAppRemote.getPlayerApi().play(track);
            callbackContext.success("playing");
            return true;
        }
        if (action.equals("PauseRequest")) {
            mSpotifyAppRemote.getPlayerApi().pause();
            callbackContext.success("pausing");
            return true;
        }
        return false;
    }

    private void ConnectionRequest(String client_id, String redirection_url, CallbackContext callbackContext) {
        Context context = this.cordova.getActivity().getApplicationContext();

        connectionParams = new ConnectionParams.Builder(client_id)
                .setRedirectUri(redirection_url)
                .showAuthView(true)
                .build();
        SpotifyAppRemote.connect(context, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote;
                callbackContext.success("App connected");
            }

            @Override
            public void onFailure(Throwable throwable) {
                try {
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String msg = object.getString("message");
                    callbackContext.error(msg);
                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Context context = this.cordova.getActivity().getApplicationContext();
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void setConnectionParams() {
        connectionParams = new ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();
    }

    private void connected() {
        Context context = this.cordova.getActivity().getApplicationContext();
        Toast.makeText(context, "Spotify is connected", Toast.LENGTH_SHORT).show();
        mSpotifyAppRemote.getPlayerApi().play("spotify:track:3qRNQHagYiiDLdWMSOkPGG");
    }
}