package aabrasha.edu.masterapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Andrii Abramov on 7/8/16.
 */
public class ImageThread implements Runnable {

    private static final String TAG = ImageThread.class.getSimpleName();
    private final Bitmap image;

    public static final String KEY_SOCKET_IP = "aabrasha.SOCKET_IP";
    public static final String KEY_SOCKET_PORT = "aabrasha.SOCKET_PORT";
    public static final String ACTION_SEND_SOCKET_CREDS = "aabrasha.SEND_SOCKET_CREDS";

    public String SERVER_IP = "10.4.51.39";

    public static final int SERVER_PORT = 9999;

    private ServerSocket serverSocket;
    private final Context context;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // check if socket is listening
            Intent serverSocketCreds = new Intent(ACTION_SEND_SOCKET_CREDS);
            serverSocketCreds.putExtra(KEY_SOCKET_IP, serverSocket.getInetAddress().getHostAddress());
            serverSocketCreds.putExtra(KEY_SOCKET_PORT, serverSocket.getLocalPort()); // Stub
            context.sendBroadcast(serverSocketCreds);

        }
    };

    public ImageThread(Context context, Bitmap image) {
        this.image = image;
        this.context = context;
        context.registerReceiver(broadcastReceiver, new IntentFilter(MasterActivity.ACTION_REQUEST_CREDS_FROM_IMAGE_THREAD)); // sent by activity
    }

    public void run() {
        try {
//                SERVER_IP = getLocalIpAddress();
            if (SERVER_IP != null) {
                serverSocket = new ServerSocket(SERVER_PORT);
                Log.d(TAG, "Server Port: " + serverSocket.getLocalPort());
                Log.d(TAG, "Server Inet Address: " + serverSocket.getInetAddress());
                Log.d(TAG, "Server Inet Address host name: " + serverSocket.getInetAddress().getHostAddress());
                Log.d(TAG, "Server is it bound? : " + serverSocket.isBound());


                byte[] imageByteArray = getBytes(image);
                while (true) {
                    Log.e(TAG, "Starting listening ...");
                    Socket client = serverSocket.accept();
                    // then I should process this client in separate process
                    Log.d(TAG, "Got a client: " + client.getInetAddress());
                    try {

                        // request
                        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
                        out.write(imageByteArray);
                        out.flush();


                        // response
                        byte[] bytes = readBytes(new BufferedInputStream(client.getInputStream()));
                        client.close();
                        Log.d(TAG, "Got response from client socket: " + new String(bytes));


                    } catch (Exception e) {
                        Log.d(TAG, Log.getStackTraceString(e));
                        throw new RuntimeException(e);
                    }
                }
            } else {
                Log.e(TAG, "Couldn't detect internet connection.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Got an exception: ", e);
            e.printStackTrace();
        } finally {
            context.unregisterReceiver(broadcastReceiver);
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
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
        int total = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
            int available = inputStream.available();
            Log.d(TAG, "Available: " + available);
            Log.d(TAG, String.format("read %d bytes, total - %d", len, total));
            if (available <= 0) {
                break;
            }
        }


        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    private byte[] getBytes(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
