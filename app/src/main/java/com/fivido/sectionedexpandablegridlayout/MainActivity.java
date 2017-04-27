package com.fivido.sectionedexpandablegridlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fivido.sectionedexpandablegridlayout.adapters.ItemClickListener;
import com.fivido.sectionedexpandablegridlayout.adapters.Section;
import com.fivido.sectionedexpandablegridlayout.adapters.SectionedExpandableLayoutHelper;
import com.fivido.sectionedexpandablegridlayout.models.Item;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView mRecyclerView;
    SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


         sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                mRecyclerView, this, 4);

        //random data
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(new Item("iPhone", 0,"Apple Products"));
        arrayList.add(new Item("iPad", 1,"Apple Products"));
        arrayList.add(new Item("iPod", 2,"Apple Products"));
        arrayList.add(new Item("iMac", 3,"Apple Products"));
        sectionedExpandableLayoutHelper.addSection("Apple Products", arrayList);
        arrayList = new ArrayList<>();
        arrayList.add(new Item("LG", 0,"Companies"));
        arrayList.add(new Item("Apple", 1,"Companies"));
        arrayList.add(new Item("Samsung", 2,"Companies"));
        arrayList.add(new Item("Motorola", 3,"Companies"));
        arrayList.add(new Item("Sony", 4,"Companies"));
        arrayList.add(new Item("Nokia", 5,"Companies"));
        sectionedExpandableLayoutHelper.addSection("Companies", arrayList);
        arrayList = new ArrayList<>();
        arrayList.add(new Item("Chocolate", 0,"Ice cream"));
        arrayList.add(new Item("Strawberry", 1,"Ice cream"));
        arrayList.add(new Item("Vanilla", 2,"Ice cream"));
        arrayList.add(new Item("Butterscotch", 3,"Ice cream"));
        arrayList.add(new Item("Grape", 4,"Ice cream"));
        sectionedExpandableLayoutHelper.addSection("Ice cream", arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("Chocolate", 0,"Ice1"));
        arrayList.add(new Item("Strawberry", 1,"Ice1"));
        arrayList.add(new Item("Vanilla", 2,"Ice1"));
        sectionedExpandableLayoutHelper.addSection("Ice1", arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("Chocolate", 0,"Ice2"));
        arrayList.add(new Item("Strawberry", 1,"Ice2"));
        arrayList.add(new Item("Vanilla", 2,"Ice2"));
        sectionedExpandableLayoutHelper.addSection("Ice2", arrayList);
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
        sectionedExpandableLayoutHelper.notifyDataSetChanged();

    }

    @Override
    public void itemClicked(Item item) {
        Toast.makeText(this, "Item: " + item.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemClicked(Section section) {
        Toast.makeText(this, "Section: " + section.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectCancel() {
      finish();
    }

    @Override
    public void selectOK(List<Item> items) {
        Toast.makeText(this, "Section: " + items.get(0).getName() + " clicked", Toast.LENGTH_SHORT).show();
    }


}
