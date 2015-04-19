package edu.vanderbilt.locomapp;

import android.content.IntentSender;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends ActionBarActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;

    // Unique tag for the error dialog fragment
    //private static final String DIALOG_ERROR = "dialog_error";

    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    // TAG for logging
    private static final String TAG = "MainActivity";

    // Google play
    GoogleApiClient mGoogleApiClient = null;

    // store location, latitude, and longitude
    Location mLastLocation;
    String mLatitudeText = "";
    String mLongitudeText = "";

    // server to connect to (commented out - for testing with groupcast)
    protected static final int PORT = 2000; //20000;
    protected static final String SERVER = "52.11.228.217"; //"cs283.hopto.org";

    // networking
    Socket socket = null;
    BufferedReader in = null;
    PrintWriter out = null;
    boolean connected = false;

    // UI elements
    // any UI elements defined here
    // for example
    // Button button1 = null;
    // EditText et = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find UI elements defined in xml
        // for ex
        Button bConnect = (Button) this.findViewById(R.id.button1);

        // any other initial state
        // for example, hideBoard() from ttt

        // assign OnClickListener to button example

        bConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buildGoogleApiClient();

                mGoogleApiClient.connect();

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    mLatitudeText = String.valueOf(mLastLocation.getLatitude());
                    mLongitudeText = String.valueOf(mLastLocation.getLongitude());
                }
                Log.i(TAG, "Latitude " + mLatitudeText);
                Log.i(TAG, "Longitude " + mLongitudeText);

            }
        });


        buildGoogleApiClient();

        mGoogleApiClient.connect();

        connect();


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy called");
        disconnect();
        super.onDestroy();
    }


    /********* Networking ******************************************************/
    /**
     * Connect to the server. This method is safe to call from the UI thread.
     */
    void connect() {

        new AsyncTask<Void, Void, String>() {

            String errorMsg = null;

            @Override
            protected String doInBackground(Void... args) {
                Log.i(TAG, "Connect task started");
                try {
                    connected = false;
                    socket = new Socket(InetAddress.getByName(SERVER), PORT);
                    Log.i(TAG, "Socket created");
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream());

                    connected = true;
                    Log.i(TAG, "Input and output streams ready");

                } catch (UnknownHostException e1) {
                    errorMsg = e1.getMessage();
                } catch (IOException e1) {
                    errorMsg = e1.getMessage();
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException ignored) {
                    }
                }
                Log.i(TAG, "Connect task finished");
                return errorMsg;
            }

            @Override
            protected void onPostExecute(String errorMsg) {
                if (errorMsg == null) {
                    Toast.makeText(getApplicationContext(),
                            "Connected to server", Toast.LENGTH_SHORT).show();

                    // start receiving
                    receive();

                    /////////////// for testing with groupcast server //////////////
                   // send("NAME,locom");
                    //send("msg,AMy," + mLatitudeText + ", " + mLongitudeText);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
                    // can't connect: close the activity
                    finish();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Start receiving one-line messages over the TCP connection. Received lines are
     * handled in the onProgressUpdate method which runs on the UI thread.
     * This method is automatically called after a connection has been established.
     */

    void receive() {
        new AsyncTask<Void, String, Void>() {

            @Override
            protected Void doInBackground(Void... args) {
                Log.i(TAG, "Receive task started");
                try {
                    while (connected) {

                        String msg = in.readLine();

                        if (msg == null) { // other side closed the connection
                            break;
                        }
                        publishProgress(msg);
                    }

                } catch (UnknownHostException e1) {
                    Log.i(TAG, "UnknownHostException in receive task");
                } catch (IOException e1) {
                    Log.i(TAG, "IOException in receive task");
                } finally {
                    connected = false;
                    try {
                        if (out != null)
                            out.close();
                        if (socket != null)
                            socket.close();
                    } catch (IOException e) {
                    }
                }
                Log.i(TAG, "Receive task finished");
                return null;
            }

            @Override
            protected void onProgressUpdate(String... lines) {
                // the message received from the server is
                // guaranteed to be not null
                String msg = lines[0];

                // if we haven't returned yet, tell the user that we have an unhandled message
                Toast.makeText(getApplicationContext(), "Unhandled message: "+msg, Toast.LENGTH_SHORT).show();

                //////// for testing with groupcast ///////
                if(msg.startsWith("+OK,NAME")) {

                    return;
                }
                if(msg.startsWith("+MSG")) {
                    Log.i(TAG, "you received message from server " + msg );
                    return;
                }
                if(msg.startsWith("+OK,MSG")) {
                    Log.i(TAG, "server received your message");
                    return;
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Disconnect from the server
     */
    void disconnect() {
        new Thread() {
            @Override
            public void run() {
                if (connected) {
                    connected = false;
                }
                // make sure that we close the output, not the input
                if (out != null) {
                    out.print("BYE");
                    out.flush();
                    out.close();
                }
                // in some rare cases, out can be null, so we need to close the socket itself
                if (socket != null)
                    try { socket.close();} catch(IOException ignored) {}

                Log.i(TAG, "Disconnect task finished");
            }
        }.start();
    }



    boolean send(String msg) {
        if (!connected) {
            Log.i(TAG, "can't send: not connected");
            return false;
        }

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... msg) {
                Log.i(TAG, "sending: " + msg[0]);
                out.println(msg[0]);
                return out.checkError();
            }

            @Override
            protected void onPostExecute(Boolean error) {
                if (!error) {
                    Toast.makeText(getApplicationContext(),
                            "Message sent to server", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error sending message to server",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msg);

        return true;
    }


    @Override
    public void onConnected(Bundle bundle) {
        // Connected to Google Play services!
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
        Log.i(TAG, "Latitude " + mLatitudeText);
        Log.i(TAG, "Longitude " + mLongitudeText);

        // send server latitude and longitude
        if (connected) {
            send("NAME,locom");

            Toast.makeText(getApplicationContext(),
                    "Long: " + mLatitudeText + " Lat: " + mLongitudeText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
            //showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}


