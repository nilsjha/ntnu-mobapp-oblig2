package no.nilsjarh.ntnu.fantj2;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FantService {
    @GET("/marketplace/list?list-all={all}")
    Call<List<Item>> getItems(@Path("all") Boolean includeAll);
}
