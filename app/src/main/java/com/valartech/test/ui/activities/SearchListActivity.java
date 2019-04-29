package com.valartech.test.ui.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.valartech.test.R;
import com.valartech.test.data.DataRepository;
import com.valartech.test.data.model.MovieModel;
import com.valartech.test.data.model.SearchResponseModel;
import com.valartech.test.di.Injection;
import com.valartech.test.ui.adapters.SearchListAdapter;
import com.valartech.test.ui.contracts.SearchContract;
import com.valartech.test.ui.presenters.SearchMoviePresenter;
import com.valartech.test.util.EndlessRecyclerOnScrollListener;
import com.valartech.test.util.TestUtils;
import com.valartech.test.util.mvp.BaseActivity;
import com.valartech.test.util.threads.MainUiThread;
import com.valartech.test.util.threads.ThreadExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchListActivity extends BaseActivity implements SearchContract.View {

    @BindView(R.id.drawer_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String searchValue;

    DataRepository dataRepository;
    SearchMoviePresenter searchMoviePresenter;

    SearchListAdapter searchListAdapter;

    Map<String, String> map = new HashMap<>();

    int pageSize = 10;
    int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        ButterKnife.bind(this);

        searchValue = getIntent().getStringExtra("SearchValue");

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));

        assert collapsingToolbarLayout != null;
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);

        collapsingToolbarLayout.setTitle("Search for: " + searchValue);

        dataRepository = Injection.provideDataRepository(MainUiThread.getInstance(), ThreadExecutor.getInstance());
        searchMoviePresenter = new SearchMoviePresenter(this, dataRepository);

        searchMovies(searchValue, pageNumber);

        searchListAdapter = new SearchListAdapter(this, new ArrayList<MovieModel>());
        searchRecyclerView.setAdapter(searchListAdapter);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (!(pageSize < 10)) {
                    pageNumber++;
                    searchMovies(searchValue, pageNumber);
                }
            }
        });
    }

    private void searchMovies(String searchValue, int pageNumber) {
        map.put("s", searchValue);
        map.put("page", "" + pageNumber);

        searchMoviePresenter.searchMovie(this, map);
    }

    @Override
    public void showSearchResults(SearchResponseModel model) {
        if (!model.getSearch().isEmpty()) {
            TestUtils.log("Search Value: " + model.getSearch().size());
            searchListAdapter.updateList((ArrayList<MovieModel>) model.getSearch());
        }
    }

    @Override
    public void searchFailed(String errorMessage) {
        showToastMessage(errorMessage);
    }
}
