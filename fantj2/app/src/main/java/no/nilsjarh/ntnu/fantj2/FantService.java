package no.nilsjarh.ntnu.fantj2;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FantService {
    @GET("/marketplace/list?list-all=true")
    Call<List<Item>> getItems();
}
