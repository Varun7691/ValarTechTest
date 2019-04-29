
package com.valartech.test.data.model;

import com.google.gson.annotations.SerializedName;

public class RatingsModel {

    @SerializedName("Source")
    private String source;
    @SerializedName("Value")
    private String value;

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }

    public static class Builder {

        private String source;
        private String value;

        public RatingsModel.Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public RatingsModel.Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public RatingsModel build() {
            RatingsModel ratingsModel = new RatingsModel();
            ratingsModel.source = source;
            ratingsModel.value = value;
            return ratingsModel;
        }

    }

}
