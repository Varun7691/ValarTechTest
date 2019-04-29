package com.valartech.test.di;

import com.valartech.test.data.DataRepository;
import com.valartech.test.data.remote.ApiService;
import com.valartech.test.data.remote.RemoteDataSource;
import com.valartech.test.util.NetworkHelper;
import com.valartech.test.util.threads.MainUiThread;
import com.valartech.test.util.threads.ThreadExecutor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.valartech.test.data.remote.RemoteDataSource.HOST;

public class Injection {

    public static DataRepository provideDataRepository(MainUiThread mainUiThread,
                                                       ThreadExecutor threadExecutor) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder;

                requestBuilder = original.newBuilder().header("Content-Type", "application/json");

                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)
                        .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        return DataRepository.getInstance(
                RemoteDataSource.getInstance(mainUiThread, threadExecutor, apiService),
                NetworkHelper.getInstance());
    }
}
