package info.abibas.lazydroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    String url ="http://192.168.0.101:2207/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button VolumeUp = (Button)findViewById(R.id.volume_up);
        Button VolumeDown = (Button)findViewById(R.id.volume_down);
        Button Shutdown = (Button)findViewById(R.id.shutdown);

        //volume up
        VolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAct("volume_up");
            }
        });

        //volume down
        VolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAct("volume_down");
            }
        });

        //shutdown
        Shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt_shutdown();
            }
        });
    }

    private void sendAct(String act) {
        String act_url = url + "?act=" + act;
        final TextView mTextView = (TextView) findViewById(R.id.text);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, act_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //todo: make it generic
    private void prompt_shutdown() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        sendAct("shutdown");
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
