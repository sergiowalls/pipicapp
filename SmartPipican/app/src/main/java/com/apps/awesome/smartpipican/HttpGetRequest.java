package com.apps.awesome.smartpipican;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    @Override
    protected String doInBackground(String... params) {

        try {
            // Creating & connection Connection with url and required Header.
            URL url = new URL("http://api.thingtia.cloud/data/pipicans/PipicanA5");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("IDENTITY_KEY", "b0fbc84c2ecd88589a7cc11f999bb3cafad521c79b79abffb3ab1b480a56d6ef");
            urlConnection.setRequestMethod("GET");   //POST or GET
            urlConnection.connect();

            // Write Request to output stream to server.
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.close();

            // Check the connection status.
            int statusCode = urlConnection.getResponseCode();
            String statusMsg = urlConnection.getResponseMessage();

            // Connection success. Proceed to fetch the response.
            if (statusCode == 200) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                return dta.toString();
            } else {
                //Handle else case
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
