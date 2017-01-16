package io.github.arsrabon.m.homerentalbd;

import java.util.ArrayList;
import java.util.List;

import io.github.arsrabon.m.homerentalbd.model.WishList;

/**
 * Created by msrabon on 1/16/17.
 */
public class WishLists {
    private static WishLists ourInstance = new WishLists();

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    private List<WishList> wishLists = new ArrayList<>();
    public static WishLists getInstance() {
        return ourInstance;
    }

    private WishLists() {
    }
}
