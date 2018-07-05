package com.mygdx.game;

/*
import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONException;
import com.google.gwt.thirdparty.json.JSONObject;*/

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonHandler {
    static final String domain = "http://172.17.41.110:8080/";
    public static String	errorMessage = null;


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            readErrorMessage(json);
            System.out.println(json);
            if (errorMessage != null)
                return null;
            return json;
        } finally {
            is.close();
        }
    }

    public static void      parseJson(JSONObject obj) throws JSONException {
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        JSONArray arr = obj.getJSONArray("posts");
        for (int i = 0; i < arr.length(); i++)
        {
            String post_id = arr.getJSONObject(i).getString("post_id");
        }
    }

    public static void	readErrorMessage(JSONObject jsonObject) {
        String	status;

        errorMessage = null;
        try {
            status = jsonObject.getString("status");
            if (status.equals("success")) {
                errorMessage = null;
            }
            else if (status.equals("failed")){
                errorMessage = jsonObject.getString("message");
            }
            else {
                errorMessage = "No valid information from server received";
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorMessage = "A json exception occured";
        };

    }

    public static void      getUserAccount(String login, String password) {
        JSONObject  jsonObject;
        String      url;

        try {
            url = domain + "/register_player?login=" + login;
            url += "&password=" + password;
            jsonObject = readJsonFromUrl(url);
            System.out.println(jsonObject);
            parseJson(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
