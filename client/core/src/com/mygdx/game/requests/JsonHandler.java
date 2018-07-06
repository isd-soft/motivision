package com.mygdx.game.requests;

/*
import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONException;
import com.google.gwt.thirdparty.json.JSONObject;*/

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
import java.util.List;


import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.http.HttpHeaders.USER_AGENT;


public class JsonHandler {
    static final String domain = "http://172.17.41.110:8080";
    public static String	errorMessage = null;


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
/*

    private static InputStream  makePostRequest(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://jakarata.apache.org/");
        NameValuePair[] data = {
                new NameValuePair("user", "joe"),
                new NameValuePair("user", "joe"),
                new NameValuePair("password", "bloggs")
        };
        post.addHeader("login", "alex");
        post.addHeader("password", "123");
        //post.setRequestBody(data);
        int status;
        try {
            status = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("status = " + status);

        try {
            InputStream in = post.getResponseBodyAsStream();
            return in;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

*/


    private static String POSTMethod(String url, String urlParameters) throws IOException {

       // String url = "https://httpbin.org/post";
        //String urlParameters = "name=Jack&occupation=programmer";
        byte[] postData = urlParameters.getBytes();

        HttpURLConnection con = null;
        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
                con.setRequestMethod("POST");
            //System.out.println(urlParameters);
           // con.setRequestProperty("User-Agent", "Java client");
           // con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
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
        //String url = "https://selfsolve.apple.com/wcResults.do";

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
/*
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        */
    }

    public static JSONObject    readJsonFromUrl(String url, String urlParameters, String requestMethod) throws IOException, JSONException {
        String              jsonText;
        InputStream   inputStream;
        BufferedReader      bufferedReader;

//        System.out.println();
//        System.out.println(url + "?" + urlParameters);
   //     jsonText = POSTMethod(url, urlParameters, isPostMethod);
     //   System.out.println(jsonText);
        if (requestMethod.equals("POST")) {
            jsonText = POSTMethod(url, urlParameters);
        }
        else {
            inputStream = new URL(url + "?" + urlParameters).openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            jsonText = readAll(bufferedReader);
        }
        return readJsonFromUrl(jsonText);
    }

    public static JSONObject readJsonFromUrl(String jsonText) throws IOException, JSONException {
        InputStream is;

        //is = new URL(url).openStream();
        try {
          //  System.out.println(urlParameters);
           // BufferedReader rd = POSTMethod(url, urlParameters);

            //BufferedReader rd = getRequestResult(url, urlParameters);
            //BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            //String jsonText = POSTMethod(url, urlParameters);
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
                //System.out.println(errorMessage);
            }
            else if (status.equals("failed")){
                errorMessage = jsonObject.getString("message");
                System.out.println(errorMessage);
            }
            else if (jsonObject.has("error")) {
                errorMessage = jsonObject.getString("error");
                System.out.println(errorMessage);
                System.out.println(jsonObject.getString("message"));
            }
            else {
                errorMessage = "No valid information from server received";
                System.out.println(errorMessage);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorMessage = "A json exception occured";
        };

    }
/*
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
    }*/
}
