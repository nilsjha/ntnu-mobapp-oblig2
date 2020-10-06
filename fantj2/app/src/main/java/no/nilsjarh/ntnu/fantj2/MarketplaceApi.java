package no.nilsjarh.ntnu.fantj2;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MarketplaceApi {
    @GET("/api/marketplace/list?list-all=true")
    public Call<List<Item>> getItems();
}
