package com.valartech.test.data;

import android.content.Context;

import com.valartech.test.data.model.MovieModel;
import com.valartech.test.data.model.SearchResponseModel;
import com.valartech.test.util.NetworkHelper;

import java.util.Map;

public class DataRepository {

    private DataSource remoteDataSource;
    private NetworkHelper networkHelper;
    private static DataRepository dataRepository;

    public DataRepository(DataSource remoteDataSource, NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.networkHelper = networkHelper;
    }

    public static synchronized DataRepository getInstance(DataSource remoteDataSource, NetworkHelper networkHelper) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(remoteDataSource, networkHelper);
        }
        return dataRepository;
    }

    public void searchMovie(Context context, Map<String, String> map, final DataSource.GetSearchedMovies callback) {
        if (networkHelper.isNetworkAvailable(context)) {
            remoteDataSource.searchMovie(map, new DataSource.GetSearchedMovies() {
                @Override
                public void onSuccess(SearchResponseModel model) {
                    callback.onSuccess(model);
                }

                @Override
                public void onFailure(String errorMessage, Throwable t) {
                    callback.onFailure(errorMessage, t);
                }

                @Override
                public void onNetworkFailure() {
                    callback.onNetworkFailure();
                }
            });
        } else {
            callback.onNetworkFailure();
        }
    }

    public void getParticularMovie(Context context, Map<String, String> map, final DataSource.GetParticularMovie callback) {
        if (networkHelper.isNetworkAvailable(context)) {
            remoteDataSource.getMovieDetails(map, new DataSource.GetParticularMovie() {
                @Override
                public void onSuccess(MovieModel model) {
                    callback.onSuccess(model);
                }

                @Override
                public void onFailure(String errorMessage, Throwable t) {
                    callback.onFailure(errorMessage, t);
                }

                @Override
                public void onNetworkFailure() {
                    callback.onNetworkFailure();
                }
            });
        } else {
            callback.onNetworkFailure();
        }
    }
}
