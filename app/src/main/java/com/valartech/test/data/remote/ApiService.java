package com.valartech.test.data.remote;

import com.valartech.test.data.model.MovieModel;
import com.valartech.test.data.model.SearchResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("/?apikey=6ebfcbb4")
    Call<SearchResponseModel> searchMovie(@QueryMap Map<String, String> map);

    @GET("/?apikey=6ebfcbb4")
    Call<MovieModel> getMovieDetails(@QueryMap Map<String, String> map);
}
