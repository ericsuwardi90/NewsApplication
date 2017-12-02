package com.ericsuwardi.newsapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ericsuwardi.newsapplication.R;
import com.ericsuwardi.newsapplication.adapter.SearchResultAdapter;
import com.ericsuwardi.newsapplication.helper.ContextHelper;
import com.ericsuwardi.newsapplication.helper.DateTimeHelper;
import com.ericsuwardi.newsapplication.helper.StringHelper;
import com.ericsuwardi.newsapplication.model.Article;
import com.ericsuwardi.newsapplication.presenter.SearchPresenter;
import com.ericsuwardi.newsapplication.view.iview.ISearchView;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends BaseActivity implements ISearchView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener {

    SearchPresenter presenter;
    SearchResultAdapter adapter;

    TextView fromDateTextView;
    TextView toDateTextView;
    EditText searchEditText;
    IconTextView searchIcon;
    IconButton searchAdvanceButton;
    IconButton toggleButton;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ExpandableRelativeLayout expandableLayout;
    LinearLayoutManager llm;

    Spinner sortBySpinner;

    int page = 1;
    String query;
    String from;
    String to;
    String sortBy;
    String language;

    String source_id;
    String source_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        source_id = getIntent().getExtras().getString("source_id", "");
        source_name = getIntent().getExtras().getString("source_name", "");

        setContentView(R.layout.activity_search);
        setTitle(StringHelper.isNullOrEmpty(source_name) ?
                getString(R.string.search_activity_default_title) :
                String.format(getString(R.string.search_activity_formatted_title), source_name));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchEditText = (EditText) findViewById(R.id.search_query);
        searchIcon = (IconTextView) findViewById(R.id.search_icon_submit);
        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        toggleButton = (IconButton) findViewById(R.id.toggle_button);
        searchAdvanceButton = (IconButton) findViewById(R.id.search_advance_button);
        sortBySpinner = (Spinner) findViewById(R.id.sort_by_spinner);
        fromDateTextView = (TextView) findViewById(R.id.search_from_datepicker_text);
        toDateTextView = (TextView) findViewById(R.id.search_to_datepicker_text);

        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        presenter = new SearchPresenter(this);
        adapter = new SearchResultAdapter(this, presenter, new Article[0]);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();
                int threshold = 10;

                if (!adapter.isLoading() && dy > 0) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - threshold) {
                        // load for next page
                        adapter.setLoading(true);
                        presenter.getOtherNewsApi(query, source_id, from, to, sortBy, language, page, ContextHelper.getApiKey(SearchActivity.this));
                    }
                }
            }
        });

        sortBySpinner.setAdapter(
                ArrayAdapter.createFromResource(
                        this,
                        R.array.sort_by_spinner_array,
                        R.layout.support_simple_spinner_dropdown_item
                )
        );

        fromDateTextView.setText(DateTimeHelper.writeFormattedDate("yyyy-MM-dd", new Date()));
        toDateTextView.setText(DateTimeHelper.writeFormattedDate("yyyy-MM-dd", new Date()));

        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(this);

        searchIcon.setOnClickListener(this);
        toggleButton.setOnClickListener(this);
        searchAdvanceButton.setOnClickListener(this);
        fromDateTextView.setOnClickListener(this);
        toDateTextView.setOnClickListener(this);
    }

    @Override
    public void openingDatePicker(int resourceId) {
        // opening date picker here
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SearchActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.show(getFragmentManager(), String.valueOf(resourceId));
    }

    @Override
    public void onLoadNewQuery() {
        adapter.clearData();
    }

    @Override
    public void afterCallSearchApi() {
        swipeRefreshLayout.setRefreshing(false);
        searchAdvanceButton.setText(getString(R.string.search_advance_button_idle));
        searchAdvanceButton.setEnabled(true);

        searchIcon.setEnabled(true);
        searchIcon.setText(getString(R.string.search_icon));
    }

    @Override
    public void getNewQuery(Article[] articles) {
        page += 1;
        adapter.clearData();
        adapter.addArticles(articles);
    }

    @Override
    public void getMoreResults(Article[] articles) {
        page += 1;
        adapter.addArticles(articles);
    }

    @Override
    public void failedGetNewQuery() {

    }

    @Override
    public void failedGetMoreResults() {
        Toast.makeText(this, getString(R.string.search_error_message_more_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openNewsActivity(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", article.getUrl());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.search_advance_button:
                page = 1;
                query = StringHelper.getAppendedQuery(searchEditText.getText().toString());
                sortBy = sortBySpinner.getSelectedItem().toString();
                from = fromDateTextView.getText().toString();
                to = toDateTextView.getText().toString();

                searchAdvanceButton.setEnabled(false);
                searchAdvanceButton.setText(getString(R.string.search_advance_button_loading));

                presenter.getOtherNewsApi(query, source_id, from, to, sortBy, language, page, ContextHelper.getApiKey(this));
                break;

            case R.id.search_icon_submit:
                page = 1;
                query = StringHelper.getAppendedQuery(searchEditText.getText().toString());
                sortBy = "";
                from = "";
                to = "";

                searchIcon.setEnabled(false);
                searchIcon.setText(getString(R.string.search_icon_loading));

                presenter.getOtherNewsApi(query, source_id, from, to, sortBy, language, page, ContextHelper.getApiKey(this));
                break;

            case R.id.toggle_button:
                expandableLayout.toggle();
                break;

            case R.id.search_from_datepicker_text:
                // call the picker
                presenter.showDatePicker(R.id.search_from_datepicker_text);
                break;

            case R.id.search_to_datepicker_text:
                // call the picker
                presenter.showDatePicker(R.id.search_to_datepicker_text);
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getOtherNewsApi(query, source_id, from, to, sortBy, language, page, ContextHelper.getApiKey(this));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int dayOfMonth) {

        month += 1;

        // it's not clean solution, but given the pattern i think it is better for using this method
        String date = year+"-"+(month >= 10 ? "" : "0" )+month+"-"+(dayOfMonth >= 10 ? "" : "0" )+dayOfMonth;

        if(view.getTag().equals( String.valueOf( R.id.search_from_datepicker_text) )){
            fromDateTextView.setText(date);
        } else if(view.getTag().equals( String.valueOf( R.id.search_to_datepicker_text) )){
            toDateTextView.setText(date);
        }
    }
}