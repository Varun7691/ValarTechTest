package com.valartech.test.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseModel {

    @SerializedName("Search")
    private List<MovieModel> mSearch;

    @SerializedName("totalResults")
    private String mTotalResults;

    @SerializedName("Response")
    private String mResponse;

    @SerializedName("Error")
    private String mError;

    public List<MovieModel> getSearch() {
        return mSearch;
    }

    public void setSearch(List<MovieModel> mSearch) {
        this.mSearch = mSearch;
    }

    public String getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(String mTotalResults) {
        this.mTotalResults = mTotalResults;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }
}
