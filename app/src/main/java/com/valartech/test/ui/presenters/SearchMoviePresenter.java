package com.valartech.test.ui.presenters;

import android.content.Context;

import com.valartech.test.data.DataRepository;
import com.valartech.test.data.DataSource;
import com.valartech.test.data.model.SearchResponseModel;
import com.valartech.test.ui.contracts.SearchContract;
import com.valartech.test.util.mvp.BasePresenter;

import java.util.Map;

public class SearchMoviePresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private DataRepository dataRepository;

    public SearchMoviePresenter(SearchContract.View view, DataRepository dataRepository) {
        this.view = view;
        this.dataRepository = dataRepository;
    }

    @Override
    public void searchMovie(Context context, Map<String, String> map) {
        if (view == null)
            return;

        view.setProgressBar(true);

        dataRepository.searchMovie(context, map, new DataSource.GetSearchedMovies() {
            @Override
            public void onSuccess(SearchResponseModel model) {
                if (view != null) {
                    view.showSearchResults(model);
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(String errorMessage, Throwable t) {
                if (view != null) {
                    view.searchFailed(errorMessage);
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
