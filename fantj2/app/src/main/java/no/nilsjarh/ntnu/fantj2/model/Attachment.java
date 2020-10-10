package no.nilsjarh.ntnu.fantj2.model;

import com.google.gson.annotations.SerializedName;

public class Attachment {
    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }
}
