package com.fivido.sectionedexpandablegridlayout.models;

/**
 * Created by bpncool on 2/23/2016.
 */
public class Item {

    private final String name;
    private final int id;
    private final String sectionTitle;
    private boolean itemSelected;
    public Item(String name, int id, String sectionTitle) {
        this.name = name;
        this.id = id;
        this.sectionTitle =sectionTitle;
        this.itemSelected =false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public boolean isItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        this.itemSelected = itemSelected;
    }
    public String getSectionTitle() {
        return sectionTitle;
    }

//    public void setSectionTitle(String sectionTitle) {
//        this.sectionTitle = sectionTitle;
//    }
//

}
