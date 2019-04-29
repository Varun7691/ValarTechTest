package com.valartech.test.data;

import com.valartech.test.data.model.MovieModel;
import com.valartech.test.data.model.SearchResponseModel;
import com.valartech.test.util.threads.MainUiThread;
import com.valartech.test.util.threads.ThreadExecutor;

import java.util.Map;

public abstract class DataSource {

    protected MainUiThread mainUiThread;
    protected ThreadExecutor threadExecutor;

    public DataSource(MainUiThread mainUiThread, ThreadExecutor threadExecutor) {
        this.mainUiThread = mainUiThread;
        this.threadExecutor = threadExecutor;
    }

    public interface GetSearchedMovies {
        void onSuccess(SearchResponseModel model);

        void onFailure(String errorMessage, Throwable t);

        void onNetworkFailure();
    }


    public abstract void searchMovie(Map<String, String> map, GetSearchedMovies callback);


    public interface GetParticularMovie {
        void onSuccess(MovieModel model);

        void onFailure(String errorMessage, Throwable t);

        void onNetworkFailure();
    }

    public abstract void getMovieDetails(Map<String, String> map, GetParticularMovie callback);
}
