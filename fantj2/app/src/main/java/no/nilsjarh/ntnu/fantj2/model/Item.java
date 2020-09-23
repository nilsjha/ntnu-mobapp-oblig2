package no.nilsjarh.ntnu.fantj2.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Item {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("priceNok")
    public BigDecimal price;
}
