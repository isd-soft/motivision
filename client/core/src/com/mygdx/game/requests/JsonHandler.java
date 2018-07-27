package com.mygdx.game.requests;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.http.HttpHeaders.USER_AGENT;


public class JsonHandler {
    static final GameProperties gameProperties = new GameProperties();
    static String domain = gameProperties.getDomain();
    public static String errorMessage = null;

    public static String getDomain() {
        GameProperties gameP = new GameProperties();
        return gameP.getDomain();
    }

    public static void setDomain(String domain) {
        JsonHandler.domain = domain;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static HttpURLConnection getHttpConnection(String url, String type) throws IOException {
        URL uri = new URL(url);
        HttpURLConnection httpCon = (HttpURLConnection) uri.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod(type);
        httpCon.connect();
        return httpCon;
    }

    public static String requestMethod(String url, String type) {
        HttpURLConnection con = null;
        String jsonText = null;

        try {
            con = getHttpConnection(url, type);
            // con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;
            StringBuilder sb = new StringBuilder();
            while ((temp = in.readLine()) != null) {
                sb.append(temp).append(" ");
            }
            jsonText = sb.toString();
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //logger.error(e.getMessage());
        }
        return jsonText;
        //result is the response you get from the remote side
    }


    private static String POSTMethod(String url, String urlParameters, String requestMethod) throws IOException {
        byte[] postData = urlParameters.getBytes();

        HttpURLConnection con = null;
//        System.out.println("Connection Timeout: " + con.getConnectTimeout());
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setConnectTimeout(3000);
            con.setDoOutput(true);
            con.setRequestMethod(requestMethod);
            DataOutputStream wr;
            try {
                wr = new DataOutputStream(con.getOutputStream());
            } catch (java.net.ConnectException e) {
                errorMessage = "Could not connect to server";
                return null;
            }
            try {
                wr.write(postData);
            } finally {
                if (wr != null) wr.close();
            }

            StringBuilder content;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            if (bufferedReader == null) {
                System.out.println("Stream is null");
                throw new IOException();
            }
            return readAll(bufferedReader);
            //return in;

            //System.out.println(content.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                con.disconnect();
        }
        return null;
    }


    public static BufferedReader getRequestResult(String url, List<BasicNameValuePair> urlParameters) {

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            return rd;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject readJsonFromUrl(String urlString, String urlParameters, String requestMethod) throws IOException, JSONException {
        String jsonText;
        InputStream inputStream;
        BufferedReader bufferedReader;
        URL url;

        System.out.println(urlString + "?" + urlParameters);
        if (requestMethod.equals("POST")) {
            jsonText = POSTMethod(urlString, urlParameters, requestMethod);
        } else if (requestMethod.equals("GET")) {
            if (urlString.equals("")) {
                url = new URL(urlString);
                URLConnection connection = url.openConnection();
                // set the connection timeout to 5 seconds
                connection.setConnectTimeout(3000);
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            else {
                if (urlParameters == "")
                    url = new URL(urlString);
                else
                    url = new URL(urlString + "?" + urlParameters);
                URLConnection connection = url.openConnection();
                // set the connection timeout to 5 seconds
                connection.setConnectTimeout(3000);
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
//            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            jsonText = readAll(bufferedReader);
        } else {
            jsonText = requestMethod(urlString + "?" + urlParameters, requestMethod);
        }
        return readJsonFromUrl(jsonText);
    }

    public static JSONObject readJsonFromUrl(String jsonText) throws IOException, JSONException {
        InputStream is;

        try {
            if (jsonText == null)
                return null;
            JSONObject json = new JSONObject(jsonText);
            System.out.println(json);
            readErrorMessage(json);
            if (errorMessage != null)
                return null;
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // is.close();
        }
    }

    public static void parseJson(JSONObject obj) throws JSONException {
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        JSONArray arr = obj.getJSONArray("posts");
        for (int i = 0; i < arr.length(); i++) {
            String post_id = arr.getJSONObject(i).getString("post_id");
        }
    }

    public static void readErrorMessage(JSONObject jsonObject) {
        String status;

        errorMessage = null;
        try {
            status = jsonObject.getString("status");
            if (status.equals("success")) {
                errorMessage = null;
                //System.out.println(errorMessage);
            } else if (status.equals("failed")) {
                errorMessage = jsonObject.getString("message");
                System.out.println(errorMessage);
            } else if (jsonObject.has("error")) {
                errorMessage = jsonObject.getString("error");
                System.out.println(errorMessage);
                System.out.println(jsonObject.getString("message"));
            } else {
                errorMessage = "No valid information from server received";
                System.out.println(errorMessage);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorMessage = "A json exception occured";
        }
        ;

    }
}
