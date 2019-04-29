package com.valartech.test.ui.contracts;

import android.content.Context;

import com.valartech.test.data.model.SearchResponseModel;
import com.valartech.test.util.mvp.IBasePresenter;
import com.valartech.test.util.mvp.IBaseView;

import java.util.Map;

public interface SearchContract {

    interface View extends IBaseView {
        void showSearchResults(SearchResponseModel model);

        void searchFailed(String errorMessage);
    }

    interface Presenter extends IBasePresenter<View> {
        void searchMovie(Context context, Map<String, String> map);
    }
}
