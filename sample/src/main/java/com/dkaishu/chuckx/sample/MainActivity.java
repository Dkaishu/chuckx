/*
 * Copyright (C) 2017 Jeff Gilfelt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dkaishu.chuckx.sample;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dkaishu.chuckx.Chuck;
import com.dkaishu.chuckx.ChuckInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.do_http).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doHttpActivity();
            }
        });
        findViewById(R.id.launch_chuck_directly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChuckDirectly();
            }
        });
    }

    private OkHttpClient getClient(Context context) {
        return new OkHttpClient.Builder()
                // Add a ChuckInterceptor instance to your OkHttp client
                .addInterceptor(new ChuckInterceptor(context))
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    private void launchChuckDirectly() {
        // Optionally launch Chuck directly from your own app UI
        startActivity(Chuck.getLaunchIntent(this));
    }

    private void doHttpActivity() {
        SampleApiService.HttpbinApi api = SampleApiService.getInstance(getClient(this));
        Callback<Void> cb = new Callback<Void>() {
            @Override public void onResponse(Call call, Response response) {}
            @Override public void onFailure(Call call, Throwable t) { t.printStackTrace(); }
        };
        api.get().enqueue(cb);
        String longText = readStringFromAssets();
        api.getImg("https://upload-images.jianshu.io/upload_images/16846729-edc4e5b15a30f12b.jpg?name=" + longText).enqueue(cb);
        api.post(new SampleApiService.Data(longText)).enqueue(cb);
        api.post(new SampleApiService.Data("posted")).enqueue(cb);
        api.patch(new SampleApiService.Data("patched")).enqueue(cb);
        api.put(new SampleApiService.Data("put")).enqueue(cb);
        api.delete().enqueue(cb);
        api.status(201).enqueue(cb);
        api.status(401).enqueue(cb);
        api.status(500).enqueue(cb);
        api.delay(9).enqueue(cb);
        api.delay(15).enqueue(cb);
        api.redirectTo("https://http2.akamai.com").enqueue(cb);
        api.redirect(3).enqueue(cb);
        api.redirectRelative(2).enqueue(cb);
        api.redirectAbsolute(4).enqueue(cb);
        api.stream(500).enqueue(cb);
        api.streamBytes(2048).enqueue(cb);
        api.image("image/png").enqueue(cb);
        api.gzip().enqueue(cb);
        api.xml().enqueue(cb);
        api.utf8().enqueue(cb);
        api.deflate().enqueue(cb);
        api.cookieSet("v").enqueue(cb);
        api.basicAuth("me", "pass").enqueue(cb);
        api.drip(512, 5, 1, 200).enqueue(cb);
        api.deny().enqueue(cb);
        api.cache("Mon").enqueue(cb);
        api.cache(30).enqueue(cb);
    }

    private String readStringFromAssets() {
        //通过设备管理对象 获取Asset的资源路径
        AssetManager assetManager = this.getApplicationContext().getAssets();

        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer sb =  new StringBuffer();
        try{
            inputStream = assetManager.open("chuck.gif");
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);

            sb.append(br.readLine());
            String line = null;
            while((line = br.readLine()) != null){
                sb.append("\n" + line);
            }

            br.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}