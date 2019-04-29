package com.valartech.test.ui.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.valartech.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_text)
    EditText searchText;

    @BindView(R.id.search_icon)
    FloatingActionButton searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = searchText.getText().toString();

                Intent intent = new Intent(MainActivity.this, SearchListActivity.class);
                intent.putExtra("SearchValue", searchValue);
                startActivity(intent);
            }
        });
    }
}
