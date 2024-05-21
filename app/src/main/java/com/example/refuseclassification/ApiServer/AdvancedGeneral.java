package com.example.refuseclassification.ApiServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class AdvancedGeneral {

    public static String recognizeImage(String photoPath) throws Exception {
        byte[] imgData = FileUtil.readFileByBytes(photoPath);
        String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");

        String result = HttpUtil.post("https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general", getAuth(), "image=" + imgParam);

        // 解析返回的结果
        JSONObject jsonObject = new JSONObject(result);
        StringBuilder sb = new StringBuilder();
        if (jsonObject.has("result")) {
            for (int i = 0; i < jsonObject.getJSONArray("result").length(); i++) {
                sb.append(jsonObject.getJSONArray("result").getJSONObject(i).getString("keyword")).append("\n");
            }
        }
        return sb.toString();
    }

    private static String getAuth() {
        String clientId = "HrnD2t1tVs89DcF0v9FdB4Tu";
        String clientSecret = "78uLd0yok6LC86zztszz6TRDoqO5kP35";
        return getAuth(clientId, clientSecret);
    }

    private static String getAuth(String ak, String sk) {
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost + "grant_type=client_credentials" + "&client_id=" + ak + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
