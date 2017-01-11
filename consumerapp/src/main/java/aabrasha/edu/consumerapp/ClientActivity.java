package aabrasha.edu.consumerapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientActivity extends Activity {

    private static final String TAG = ClientActivity.class.getSimpleName();

    public static final String ACTION_RESOLVE_CONNECTION = "aabrasha.RESOLVE_CONNECTION";
    public static final String ACTION_HANDLE_SOCKET_CREDS = "aabrasha.SEND_SOCKET_CREDS";

    public static final String KEY_SOCKET_IP = "aabrasha.SOCKET_IP";
    public static final String KEY_SOCKET_PORT = "aabrasha.SOCKET_PORT";

    private ImageView imageView;
    private ProgressBar progressBarLoading;

    private Handler handler = new Handler();


    BroadcastReceiver socketReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "got: " + intent.getAction());
            int socketPort = intent.getIntExtra(KEY_SOCKET_PORT, -1);
            String socketIp = intent.getStringExtra(KEY_SOCKET_IP);
            Log.d(TAG, String.format("Got creds %s:%s", socketIp, socketPort));
            new Thread(new ClientThread(socketIp, socketPort), "ClientThread").start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        registerReceiver(socketReceiver, new IntentFilter(ACTION_HANDLE_SOCKET_CREDS));

        imageView = (ImageView) findViewById(R.id.imageView);

        progressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        progressBarLoading.setIndeterminate(true);
        startLoadingImage();
    }

    private void startLoadingImage() {
        Log.d(TAG, "Sending broadcast: " + ACTION_RESOLVE_CONNECTION);
        sendBroadcast(new Intent(ACTION_RESOLVE_CONNECTION));
        progressBarLoading.setIndeterminate(true);
    }

    public class ClientThread implements Runnable {

        private final String serverIpAddress;
        private final int serverPort;

        public ClientThread(String serverIpAddress, int serverPort) {
            this.serverIpAddress = serverIpAddress;
            this.serverPort = serverPort;
        }

        public void run() {
            try {
                InetAddress serverAddress = InetAddress.getByName(serverIpAddress);
                Log.d("ClientActivity", "C: Connecting...");
                Socket socket = new Socket(serverAddress, serverPort);
                BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
                final Bitmap bitmap = decodeBitmap(inputStream);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBarLoading.setIndeterminate(false);
                        imageView.setImageBitmap(bitmap);
                    }
                });

                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                out.write("Hi there, I am done".getBytes());
                out.close();
                Log.d(TAG, "Closed.");
            } catch (Exception e) {
                Log.e(TAG, "Error", e);
            }
        }

        private byte[] readBytes(InputStream inputStream) throws IOException {
            // this dynamically extends to take the bytes you read
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

            // this is storage overwritten on each iteration with bytes
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];

            // we need to know how may bytes were read to write them to the byteBuffer
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
                int available = inputStream.available();
                Log.d(TAG, "Data left to read: " + available);

                if (available <= 0) {
                    break;
                }
            }


            // and then we can return your byte array.
            return byteBuffer.toByteArray();
        }

        private Bitmap decodeBitmap(InputStream inputStream) throws IOException {
            long startTime = System.currentTimeMillis();
            byte[] bytes = readBytes(inputStream);
            Bitmap result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Log.d(TAG, "Got Bitmap: " + result);
            long finishTime = System.currentTimeMillis();
            Log.d(TAG, "I have done reading. In " + (finishTime - startTime) + " milliseconds");
            return result;
        }


    }
}