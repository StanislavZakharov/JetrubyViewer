package com.itwashard.jetrubyviewer.data.remote;

import com.google.gson.Gson;
import com.itwashard.jetrubyviewer.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stanislavzakharov on 22.12.16.
 */

public class JetrubyServiceFactory {

    public static JetrubyService makeJetrubyService() {
        OkHttpClient okHttpClient = makeOkHttpClient(makeLoggingInterceptor());
        return makeJetrubyService(okHttpClient);
    }

    private static JetrubyService makeJetrubyService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DRIBBBLE_API_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit.create(JetrubyService.class);
    }

    private static OkHttpClient makeOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Constants.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

}
