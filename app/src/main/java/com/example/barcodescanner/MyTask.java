package com.example.barcodescanner;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MyTask extends AsyncTask <URL,Void, JSONObject> {

    private MainActivity callback;

    public MyTask(MainActivity callback){
        this.callback=callback;
    }


    @Override
    protected JSONObject doInBackground(URL... urls) {
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = urls[0];
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            return new JSONObject(stringBuffer.toString());
        }
        catch(Exception ex)
        {
            Log.e("App", "yourDataTask", ex);
            return null;
        }
        finally
        {
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }    }

    protected void onPostExecute(JSONObject result) {
        try {
            Product product=new Product(result.getJSONObject("product").getString("product_name_en"),result.getJSONObject("product").getString("image_url"));
            callback.AsynkTaskResult(product);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
