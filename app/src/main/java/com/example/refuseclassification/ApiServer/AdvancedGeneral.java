package com.example.refuseclassification.ApiServer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.refuseclassification.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 通用物体和场景识别
 */
public class AdvancedGeneral extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_home); // 确保这里的布局文件是正确的

        resultTextView = findViewById(R.id.searchHome);

        Intent intent = getIntent();
        String photoPath = intent.getStringExtra("photoPath");
        if (photoPath != null) {
            new Thread(() -> {
                try {
                    recognizeImage(photoPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void recognizeImage(String photoPath) throws Exception {
        byte[] imgData = FileUtil.readFileByBytes(photoPath);
        String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");

        String result = HttpUtil.post("https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general", getAuth(), "image=" + imgParam);

        runOnUiThread(() -> resultTextView.setText(result));
    }

    private String getAuth() {
        String clientId = "HrnD2t1tVs89DcF0v9FdB4Tu";
        String clientSecret = "78uLd0yok6LC86zztszz6TRDoqO5kP35";
        return getAuth(clientId, clientSecret);
    }

    private String getAuth(String ak, String sk) {
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
