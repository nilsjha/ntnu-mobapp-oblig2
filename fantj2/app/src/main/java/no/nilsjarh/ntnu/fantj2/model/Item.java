package no.nilsjarh.ntnu.fantj2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Item {

    @Expose
    @SerializedName("attachments")
    private List<Attachment> attachmentList;

    @Expose
    @SerializedName("createdDate")
    private Date itemCreated;

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("priceNok")
    private BigDecimal itemPrice;

    @Expose
    @SerializedName("purchase")
    private Purchase itemPurchase;

    @Expose
    @SerializedName("sellerUser")
    private User itemSeller;

    @Expose
    @SerializedName("title")
    private String itemTitle;

    @Expose
    @SerializedName("description")
    private String itemDescription;



    public Long getId() {
        return id;
    }


    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public Date getItemCreated() {
        return itemCreated;
    }

    public Purchase getItemPurchase() {
        return itemPurchase;
    }

    public User getItemSeller() {
        return itemSeller;
    }
}
