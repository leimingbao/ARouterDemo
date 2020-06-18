package com.leiming.arouterdemo.ui.home.subactivity;

import com.leiming.network.database.Logging;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;

public class Test {
    OkHttpClient client = new OkHttpClient().newBuilder().hostnameVerifier(new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            //强行返回true 即验证成功
            return true;
        }
    }).build();

}
