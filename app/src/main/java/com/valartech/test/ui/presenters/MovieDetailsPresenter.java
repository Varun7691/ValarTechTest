package com.valartech.test.ui.presenters;

import android.content.Context;

import com.valartech.test.data.DataRepository;
import com.valartech.test.data.DataSource;
import com.valartech.test.data.model.MovieModel;
import com.valartech.test.ui.contracts.MovieDetailsContract;
import com.valartech.test.util.mvp.BasePresenter;

import java.util.Map;

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsContract.View> implements MovieDetailsContract.Presenter {

    private DataRepository dataRepository;

    public MovieDetailsPresenter(MovieDetailsContract.View view, DataRepository dataRepository) {
        this.view = view;
        this.dataRepository = dataRepository;
    }

    @Override
    public void getMovieDetails(Context context, Map<String, String> map) {
        if (view == null)
            return;

        view.setProgressBar(true);

        dataRepository.getParticularMovie(context, map, new DataSource.GetParticularMovie() {
            @Override
            public void onSuccess(MovieModel model) {
                if (view != null) {
                    view.showMovieDetails(model);
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(String errorMessage, Throwable t) {
                if (view != null) {
                    view.movieDetailsFailed(errorMessage);
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onNetworkFailure() {
                if (view != null) {
                    view.showToastMessage("No network. Please try again.");
                    view.setProgressBar(false);
                }
            }
        });
    }
}
