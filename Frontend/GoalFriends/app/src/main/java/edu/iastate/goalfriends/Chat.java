package edu.iastate.goalfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class Chat extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;
    private Button bConnect, bDisconnect, bSendButton;
    private TextView mOutput;
    private EditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bConnect = findViewById(R.id.b_connect);
        bSendButton = findViewById(R.id.b_sendMessage);
        bDisconnect = findViewById(R.id.b_Disconnect);

        mOutput = findViewById(R.id.m_output);

        mOutput.setMovementMethod(new ScrollingMovementMethod());

        mInput = findViewById(R.id.m_input);

        bConnect.setOnClickListener(v -> connectWebSocket());

        bDisconnect.setOnClickListener(v -> {
            mWebSocketClient.close();
            mOutput.setText("");
        });

        bSendButton.setOnClickListener(v -> {

            String message = mInput.getText().toString();

            if(message != null && message.length() > 0){
                mWebSocketClient.send(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebSocketClient.close();
    }

    private void connectWebSocket(){
        URI uri;
        try{
            //uri = new URI(http://coms-309-054.cs.iastate.edu:8080);
            uri = new URI("ws://echo.websocket.org");
        } catch (URISyntaxException e){
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri){

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String message) {
                Log.i("Websocket", "Message Recieved");

                mOutput.append("\n" + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}