package no.nilsjarh.ntnu.fantj2;

import java.math.BigDecimal;
import java.util.List;

import no.nilsjarh.ntnu.fantj2.model.Item;
import no.nilsjarh.ntnu.fantj2.model.Purchase;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    public Call<Purchase> purchaseItem(
            @Query("item") Long id,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST(PREFIX + "add")
    public Call<Item> createItem(
            @Field("title") String title,
            @Header("Authorization") String token,
            @Field("price") BigDecimal price
            );

    @FormUrlEncoded
    @POST(PREFIX + "update")
    public Call<Item> updateItem(
            @Query("item") Long id,
            @Header("Authorization") String token,
            @Field("title") String title,
            @Field("price") BigDecimal price,
            @Field("description") String description
    );

    @Multipart
    @POST(PREFIX + "attach")
    public Call<Item> attachImage(
            @Part("itemid") Long itemid,
            @Part("description") String descr,
            @Part("image")RequestBody image
            );


}
