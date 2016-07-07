package ua.aabrasha.edu.httppractice;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrii Abramov on 7/4/16.
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static String readFromUrl(String fromUrl) throws IOException {
        StringBuilder response = new StringBuilder();

        URL url = new URL(fromUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        Log.d(TAG, "Connection RC: " + connection.getResponseCode());

        InputStream inputStream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    public static List<Drawable> getImages(String vkUrl) throws Exception {
        return new NetworkContentLoader().execute(vkUrl).get();
    }

    public static Drawable loadImage(String imageUrl) {
        try {
            InputStream is = (InputStream) new URL(imageUrl).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            is.close();
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    static class NetworkContentLoader extends AsyncTask<String, Integer, List<Drawable>> {


        @Override
        protected List<Drawable> doInBackground(String... strings) {


            try {
                Log.d(TAG, "loading in .... " + Thread.currentThread().getName());
                String vkUrl = strings[0];

                List<Drawable> result = new ArrayList<>();
                String content = readFromUrl(vkUrl);

                JSONObject jsonObject = new JSONObject(content);
                JSONArray response = jsonObject.getJSONArray("response");
                for (int i = 0; i < response.length(); i++) {

                    publishProgress(i);
                    String imgUrl = response.getJSONObject(i).getString("src");
                    result.add(loadImage(imgUrl));

                }
                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, Arrays.toString(values));
            super.onProgressUpdate(values);
        }
    }


}