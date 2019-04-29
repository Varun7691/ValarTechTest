package com.valartech.test.ui.contracts;

import android.content.Context;

import com.valartech.test.data.model.MovieModel;
import com.valartech.test.util.mvp.IBasePresenter;
import com.valartech.test.util.mvp.IBaseView;

import java.util.Map;

public interface MovieDetailsContract {

    interface View extends IBaseView {
        void showMovieDetails(MovieModel model);

        void movieDetailsFailed(String errorMessage);
    }

    interface Presenter extends IBasePresenter<View> {
        void getMovieDetails(Context context, Map<String, String> map);
    }
}
