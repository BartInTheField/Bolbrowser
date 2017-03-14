package com.bartintveld.bolbrowser;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by barti on 14-3-2017.
 */

public class BolApiConnector extends AsyncTask<String, Void, String> {
    
    private ProductsAvailable listener = null;
    
    public BolApiConnector(ProductsAvailable listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        //Log.i(TAG, "doInBackground");
        InputStream inputStream = null;
        int responsCode = -1;
        // De URL die we via de .execute() meegeleverd krijgen
        String personUrl = params[0];
        String result = "";

        //Log.i(TAG, "doInBackground - " + personUrl);
        try {
            // Maak een URL object
            URL url = new URL(personUrl);
            // Open een connection op de URL
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                return null;
            }

            // Initialiseer een HTTP connectie
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            // Voer het request uit via de HTTP connectie op de URL
            httpConnection.connect();
            // Kijk of het gelukt is door de response code te checken
            responsCode = httpConnection.getResponseCode();
            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                result = getStringFromInputStream(inputStream);
            } else {
                //Log.e(TAG, "Error, invalid response");
            }
        } catch (MalformedURLException e) {
            //Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            //Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    /**
     * onPostExecute verwerkt het resultaat uit de doInBackground methode.
     *
     * @param result
     */

    @Override
    protected void onPostExecute(String result) {

        try {
            //Top level object
            JSONObject jsonObject = new JSONObject(result);

            // Getting all albums and start looping
            JSONArray products = jsonObject.getJSONArray("products");

            ArrayList<Product> returnProducts = new ArrayList<>();

            for (int i = 0; i < products.length(); i++) {

                //Array level objects and products
                JSONObject product = products.getJSONObject(i);

                // Get title, specsTag and summary
                String title = product.getString("title");
                String specsTag = product.getString("specsTag");
                String summary = product.getString("summary");
                String longDescription = product.getString("longDescription");

                //JSONArray with images
                JSONArray images = product.getJSONArray("images");

                // Get XS image and L image
                String smallImageUrl = images.getJSONObject(0).getString("url");
                String largeImageUrl = images.getJSONObject(4).getString("url");

                //Create new Product
                Product p = new Product(title, specsTag, summary, longDescription, smallImageUrl, largeImageUrl);

                //Add Product to list
                returnProducts.add(p);


            }
            //Callback
            listener.productAvailable(returnProducts);
        } catch (JSONException ex) {
            //Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());

        }

    }


    //Convert InputStream to String
    private String getStringFromInputStream(InputStream inputStream) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }



    //Callback interface
    public interface ProductsAvailable{;
        void productAvailable(ArrayList<Product> result);
    }
}
