package net.bhtech.lygmanager.isecuritys.main.index;

/**
 * Created by zhangxinbiao on 2017/11/17.
 */

public class IndexDataEntity {
    private String imageUrl;
    private String itemText;
    private String itemId;
    private int itemType;
    private int ITEM_TYPE;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(int ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }
}
