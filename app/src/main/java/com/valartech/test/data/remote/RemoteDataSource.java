package com.valartech.test.data.remote;

import com.valartech.test.data.DataSource;
import com.valartech.test.data.model.MovieModel;
import com.valartech.test.data.model.SearchResponseModel;
import com.valartech.test.util.Constants;
import com.valartech.test.util.threads.MainUiThread;
import com.valartech.test.util.threads.ThreadExecutor;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource extends DataSource {

    public static final String HOST = "http://www.omdbapi.com";

    private static RemoteDataSource remoteDataSource;
    private ApiService apiService;

    public RemoteDataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor, ApiService apiService) {
        super(mainUiThread, threadExecutor);
        this.apiService = apiService;
    }

    public static synchronized RemoteDataSource getInstance(MainUiThread mainUiThread,
                                                            ThreadExecutor threadExecutor,
                                                            ApiService apiService) {
        if (remoteDataSource == null) {
            remoteDataSource = new RemoteDataSource(mainUiThread, threadExecutor, apiService);
        }
        return remoteDataSource;
    }

    @Override
    public void searchMovie(Map<String, String> map, final GetSearchedMovies callback) {
        Call<SearchResponseModel> call = apiService.searchMovie(map);
        call.enqueue(new Callback<SearchResponseModel>() {
            @Override
            public void onResponse(Call<SearchResponseModel> call, Response<SearchResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equalsIgnoreCase(Constants.RESPONSE_SUCCESS))
                        callback.onSuccess(response.body());
                    else
                        callback.onFailure(response.body().getError(), new Throwable());
                } else {
                    callback.onFailure("Something went wrong", new Throwable());
                }
            }

            @Override
            public void onFailure(Call<SearchResponseModel> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage(), t);
            }
        });
    }

    @Override
    public void getMovieDetails(Map<String, String> map, final GetParticularMovie callback) {
        Call<MovieModel> call = apiService.getMovieDetails(map);
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().equalsIgnoreCase(Constants.RESPONSE_SUCCESS)) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(response.body().getError(), new Throwable());
                    }
                } else {
                    callback.onFailure("Something went wrong", new Throwable());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage(), t);
            }
        });
    }
}
