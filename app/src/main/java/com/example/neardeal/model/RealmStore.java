package com.example.neardeal.model;

import io.realm.RealmObject;

public class RealmStore extends RealmObject {

    private String storeName;
    private String storeDesc;

    public RealmStore(String storeName, String storeDesc) {
        this.storeName = storeName;
        this.storeDesc = storeDesc;
    }

    public RealmStore() {
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

}
