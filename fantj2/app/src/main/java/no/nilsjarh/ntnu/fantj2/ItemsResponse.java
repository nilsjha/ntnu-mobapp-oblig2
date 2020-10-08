package no.nilsjarh.ntnu.fantj2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;

public class ItemsResponse {
    @SerializedName("items")
    @Expose
    List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
