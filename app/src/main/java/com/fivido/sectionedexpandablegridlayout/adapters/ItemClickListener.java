package com.fivido.sectionedexpandablegridlayout.adapters;

import com.fivido.sectionedexpandablegridlayout.models.Item;

import java.util.List;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(Item item);
    void itemClicked(Section section);
    void selectCancel();
    void selectOK(List<Item> items);
}
