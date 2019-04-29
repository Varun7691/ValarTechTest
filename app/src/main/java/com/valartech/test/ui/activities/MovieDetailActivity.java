package com.valartech.test.ui.activities;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.valartech.test.R;
import com.valartech.test.data.DataRepository;
import com.valartech.test.data.model.MovieModel;
import com.valartech.test.di.Injection;
import com.valartech.test.ui.contracts.MovieDetailsContract;
import com.valartech.test.ui.presenters.MovieDetailsPresenter;
import com.valartech.test.util.mvp.BaseActivity;
import com.valartech.test.util.threads.MainUiThread;
import com.valartech.test.util.threads.ThreadExecutor;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends BaseActivity implements MovieDetailsContract.View {

    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @BindView(R.id.drawer_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.plot)
    TextView plot;

    MovieModel movieModel;

    DataRepository dataRepository;
    MovieDetailsPresenter movieDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        movieModel = getIntent().getParcelableExtra("MovieModel");

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
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        assert collapsingToolbarLayout != null;
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBarWhite);

        collapsingToolbarLayout.setTitle(movieModel.getTitle());
        Picasso.with(this).load(movieModel.getPoster()).placeholder(R.drawable.ic_broken_image).into(moviePoster);

        dataRepository = Injection.provideDataRepository(MainUiThread.getInstance(), ThreadExecutor.getInstance());
        movieDetailsPresenter = new MovieDetailsPresenter(this, dataRepository);

        getMovieDetails();
    }

    private void getMovieDetails() {
        Map<String, String> map = new HashMap<>();
        map.put("i", movieModel.getImdbID());
        map.put("plot", "full");
        map.put("type", movieModel.getType());

        movieDetailsPresenter.getMovieDetails(this, map);
    }

    @Override
    public void showMovieDetails(MovieModel model) {
        if (model != null) {
            plot.setText(model.getPlot());
        }
    }

    @Override
    public void movieDetailsFailed(String errorMessage) {
        showToastMessage(errorMessage);
    }
}
