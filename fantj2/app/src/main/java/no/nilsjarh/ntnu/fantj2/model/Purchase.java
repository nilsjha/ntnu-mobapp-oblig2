package no.nilsjarh.ntnu.fantj2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Purchase {
    @Expose
    @SerializedName("buyerUser")
    private User buyerUser;

    @Expose
    @SerializedName("id")
    private Long purchaseId;

    @Expose
    @SerializedName("purchaseDate")
    private Date purchaseDate;

    public User getBuyerUser() {
        return buyerUser;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
}
