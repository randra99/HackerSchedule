package com.example.wildcats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.os.StrictMode;
import java.io.File;
import org.apache.http.entity.FileEntity;

public class ParseJSON{
    String path = "";
    public ParseJSON(String path)
    {
        this.path = path;
    }

    public String test()
    {
        String result="";
        String in = imageToText(path);
        try {
            JSONObject reader = new JSONObject(in);
            JSONArray text = reader.getJSONArray("regions");
            for(int i = 0; i<text.length();i++) {
                JSONArray lines = text.getJSONObject(i).getJSONArray("lines");
                for (int j = 0; j < lines.length(); j++) {
                    JSONArray words = lines.getJSONObject(j).getJSONArray("words");
                    for (int k = 0; k < words.length(); k++) {
                        JSONObject word = words.getJSONObject(k);
                        result += word.getString("text");
                    }
                    result += "\n";
                }
            }
            result += reader.toString();
        }
        catch(JSONException e) {result = e.toString();}

        System.out.println(result);
        return result;
    }

    public String imageToText(String path)
    {
        enableStrictMode();
        String result="";

        // **********************************************
        // *** Update or verify the following values. ***
        // **********************************************

        // Replace <Subscription Key> with your valid subscription key.
        String subscriptionKey = "5da28be962b24d4297d2dc0838193d5d";

        // You must use the same Azure region in your REST API method as you used to
        // get your subscription keys. For example, if you got your subscription keys
        // from the West US region, replace "westcentralus" in the URL
        // below with "westus".
        //
        // Free trial subscription keys are generated in the "westus" region.
        // If you use a free trial subscription key, you shouldn't need to changeJSONArray
        // this region.
        String uriBase =
                "https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/ocr";

        String imageToAnalyze =
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/" +
                        "Atomist_quote_from_Democritus.png/338px-Atomist_quote_from_Democritus.png";

        String imagePath = path;

        File file = new File(imagePath);
        FileEntity reqEntity = new FileEntity(file,imagePath);

            try {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                URIBuilder uriBuilder = new URIBuilder();
                uriBuilder = new URIBuilder(uriBase);

                uriBuilder.setParameter("language", "unk");
                uriBuilder.setParameter("detectOrientation", "true");

                // Request parameters.
                URI uri;
                uri = uriBuilder.build();
                HttpPost request = new HttpPost(uri);

                // Request headers.
                request.setHeader("Content-Type", "application/octet-stream");
                request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

                // Request body.
                StringEntity requestEntity;
                requestEntity =
                        new StringEntity("{\"url\":\"" + imageToAnalyze + "\"}");
                request.setEntity(reqEntity);

                // Call the REST API method and get the response entity.
                HttpResponse response = httpClient.execute(request);
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    // Format and display the JSON response.
                    String jsonString = EntityUtils.toString(entity);
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println("REST Response:\n");
                    System.out.println(json.toString(2));
                    result = json.toString();
                }
            } catch(Exception e) {return e.toString();}


        return result;
    }

    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }
}
