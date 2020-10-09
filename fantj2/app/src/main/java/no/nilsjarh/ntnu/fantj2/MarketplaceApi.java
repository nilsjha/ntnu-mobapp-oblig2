package no.nilsjarh.ntnu.fantj2;

import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MarketplaceApi {
    public static String PREFIX = "/api/marketplace/";

    @GET(PREFIX + "list?list-all=true")
    public Call<List<Item>> getItemList();

    @GET(PREFIX + "view")
    public Call<Item> getSingleItem(@Query("id") Long id);

    @GET(PREFIX + "purchase")
    public Call<Item> purchaseItem(
            @Query("item") Long id,
            @Header("Authorization") String token
    );

    @Multipart
    @POST(PREFIX + "attach")
    public Call<Item> attachImage(
            @Part("itemid") Long itemid,
            @Part("description") String descr,
            @Part("image")RequestBody image
            );


}
