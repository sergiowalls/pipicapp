package com.apps.awesome.smartpipican;

import android.app.DialogFragment;
import android.content.Context;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.InternCache;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NFCReadFragment extends DialogFragment {

    public static final String TAG = NFCReadFragment.class.getSimpleName();
    private ArrayList<String> selectedDogs;

    public static NFCReadFragment newInstance(ArrayList<String> selectedDogs) {
        NFCReadFragment f = new NFCReadFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("selectedDogs", selectedDogs);
        f.setArguments(args);
        return f;
    }

    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        selectedDogs = args.getStringArrayList("selectedDogs");
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (MapsActivity) context;
        mListener.onDialogDisplayed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.onDialogDismissed();
    }

    public void onNfcDetected(Ndef ndef) throws ExecutionException, InterruptedException {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://api.thingtia.cloud/data/pipicans/PipicanA5";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v(TAG, "Response is: " + response);
//                            SensorModel sm = new ObjectMapper().readValue(response, SensorModel.class);
//                            ObservationModel obs = sm.getObservations().get(0);

                        try {
                            JSONObject resp = new JSONObject(response);
                            JSONArray jArr = resp.getJSONArray("observations");
                            JSONObject jObj = jArr.getJSONObject(0);
                            String val = jObj.getString("value");
                            String loc = jObj.getString("location");
                            JSONObject jVal = new JSONObject(val);
                            JSONArray jDogs = jVal.getJSONArray("dogs");
                            ArrayList<String> list = new ArrayList<>();
                            if (jDogs != null) {
                                int len = jDogs.length();
                                for (int i=0;i<len;i++){
                                    list.add(jDogs.get(i).toString());
                                }
                            }
                            if(containsMyDogs(list, selectedDogs)){
                                list.removeAll(selectedDogs);
                            } else {
                                list.addAll(selectedDogs);
                            }
                            putSensorInfo(list, loc, resp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error is: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("IDENTITY_KEY", "b0fbc84c2ecd88589a7cc11f999bb3cafad521c79b79abffb3ab1b480a56d6ef");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void putSensorInfo(final ArrayList<String> dogs, final String locations, final JSONObject jsonObject) {
        String url = "http://api.thingtia.cloud/data/pipicans/PipicanA5";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "Error is: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("IDENTITY_KEY", "b0fbc84c2ecd88589a7cc11f999bb3cafad521c79b79abffb3ab1b480a56d6ef");
                return params;
            }
            @Override
            public byte[] getBody() {
                try {
                    String str = "{\"observations\":[{\"value\":\"{\\\"dogs\\\":" + Arrays.toString(dogs.toArray()) + "}\",\"location\":\"" + locations + "\"}]}";
                    return str.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        queue.add(putRequest);
    }

    private boolean containsMyDogs(ArrayList<String> apiList, ArrayList<String> selectedDogs) {
        for(String dog: selectedDogs){
            if(apiList.contains(dog)) return true;
        }
        return false;
    }
}
