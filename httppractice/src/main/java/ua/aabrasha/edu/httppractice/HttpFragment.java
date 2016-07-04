package ua.aabrasha.edu.httppractice;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andrii Abramov on 7/4/16.
 */
public class HttpFragment extends Fragment {

    private static final String TAG = HttpFragment.class.getSimpleName();

    private List<Drawable> images = new ArrayList<>();

    EditText etUrl;
    GridView gridImages;
    ImageAdapter imageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View httpContainer = inflater.inflate(R.layout.http_fragment, container, false);

        initComponents(httpContainer);

        return httpContainer;
    }

    private void initComponents(View view) {

        gridImages = (GridView) view.findViewById(R.id.gridImages);
        imageAdapter = new ImageAdapter();

        etUrl = (EditText) view.findViewById(R.id.etUrl);

        Button btnClear = (Button) view.findViewById(R.id.btnClear);
        btnClear.setEnabled(false); // TODO
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUrl.setText("");
            }
        });

        Button btnGo = (Button) view.findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://api.vk.com/method/photos.get?owner_id=85201518&album_id=wall&count=20";
                loadImages(url);
            }
        });

    }

    private void loadImages(String url) {

        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectionIsPresent(connManager.getActiveNetworkInfo())) {
            images = Collections.emptyList();
            try {
                images = Utils.getImages(url);
                Log.d(TAG, "setting in in .... " + Thread.currentThread().getName());
                gridImages.setAdapter(imageAdapter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean connectionIsPresent(NetworkInfo info) {
        return info != null && info.isConnected();
    }

//    class DownloadHelper extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
////            Toast.makeText(getActivity(), "Making Async request...." + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
//            try {
//                return Utils.readFromUrl(strings[0]);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//    }

    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int i) {
            return images.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.grid_item_image, viewGroup, false);
            }

            ImageView holder = (ImageView) view.findViewById(R.id.image_holder);
            holder.setImageDrawable(images.get(i));

            return view;// TODO
        }
    }

}
