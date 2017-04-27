package com.fivido.sectionedexpandablegridlayout.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.fivido.sectionedexpandablegridlayout.R;
import com.fivido.sectionedexpandablegridlayout.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2/23/2016.
 */
public class SectionedExpandableGridAdapter extends RecyclerView.Adapter<SectionedExpandableGridAdapter.ViewHolder> {

    //data array
    private ArrayList<Object> mDataArrayList;

    //context
    private final Context mContext;

    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;

    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.item_common_use_section;
    private static final int VIEW_TYPE_ITEM = R.layout.item_common_use_goods; //TODO : change this

    private static final int ITEM_TYPE_HEADER = 1;
    private static final int ITEM_TYPE_FOOTER = R.layout.item_common_use_footview; ;
    private int mBottomCount=1;//底部View个数

    public SectionedExpandableGridAdapter(Context context, ArrayList<Object> dataArrayList,
                                          final GridLayoutManager gridLayoutManager, ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
        mSectionStateChangeListener = sectionStateChangeListener;
        mDataArrayList = dataArrayList;

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return isBottomView(position)||isSection(position)?gridLayoutManager.getSpanCount():1;
            }
        });
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return position >=   mDataArrayList.size();
    }

    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof Section;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (holder.viewType) {
            case VIEW_TYPE_ITEM :
                final Item item = (Item) mDataArrayList.get(position);
                holder.itemTextView.setText(item.getName());
                if(item.isItemSelected()){
                    holder.itemTextView.setBackgroundResource(R.drawable.icon_order_note_rb_selected);

                }else {
                    holder.itemTextView.setBackgroundResource(R.drawable.shape_often_buy_note_selected_not);
                }

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(item);
                        ((Item) mDataArrayList.get(position)).setItemSelected(!item.isItemSelected());
                        for(int n=0;n<mDataArrayList.size();n++)
                        {
                            if(!isSection(n))
                            {
                                Item item3 = (Item) mDataArrayList.get(n);

                                if(item.getSectionTitle().equals(item3.getSectionTitle())&&!item.getName().equals(item3.getName())){
                                    ((Item) mDataArrayList.get(n)).setItemSelected(false);
                                }
                            }
                        }
                    notifyDataSetChanged();
                    }
                });
                break;
            case VIEW_TYPE_SECTION :
                if(position ==0){
                    holder.viewline.setVisibility(View.GONE);
                }else {
                    holder.viewline.setVisibility(View.VISIBLE);
                }
                final Section section = (Section) mDataArrayList.get(position);
                holder.sectionTextView.setText(section.getName());
                holder.sectionTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(section);
                    }
                });
                holder.sectionToggleButton.setChecked(section.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSectionStateChangeListener.onSectionStateChanged(section, isChecked);
                    }
                });
                break;

            case ITEM_TYPE_FOOTER :

                int j=0;
                for(int n=0;n<mDataArrayList.size();n++)
                {
                    if(!isSection(n))
                    {
                        final Item item2 = (Item) mDataArrayList.get(n);
                        if(item2.isItemSelected()){

                            holder.tv_ok.setEnabled(true);
                            break;
                        }
                    }
                    j++;
                }
              if(j==mDataArrayList.size()){
                  holder.tv_ok.setEnabled(false);
              }
                holder.tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Item> selectList =new ArrayList<>();

                        for(int n=0;n<mDataArrayList.size();n++)
                        {
                            if(!isSection(n))
                            {
                                selectList.add((Item) mDataArrayList.get(n));
                            }

                        }

                        mItemClickListener.selectOK(selectList);
                    }
                });
                holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mItemClickListener.selectCancel();
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size() + mBottomCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()) {
            return ITEM_TYPE_FOOTER;
        }
        else if (isSection(position))
            return VIEW_TYPE_SECTION;

        else return VIEW_TYPE_ITEM;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        View view;
        int viewType;

        //for section
        TextView sectionTextView;
        ToggleButton sectionToggleButton;
        View viewline;
        //for item
        TextView itemTextView;

        TextView tv_cancel;
        TextView tv_ok;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            if (viewType == VIEW_TYPE_ITEM) {
                itemTextView = (TextView) view.findViewById(R.id.text_item);
            }else if (viewType == ITEM_TYPE_FOOTER) {
                tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                tv_ok = (TextView) view.findViewById(R.id.tv_ok);
            } else {
                viewline=view.findViewById(R.id.view_line);
                sectionTextView = (TextView) view.findViewById(R.id.text_section);
                sectionToggleButton = (ToggleButton) view.findViewById(R.id.toggle_button_section);
            }
        }
    }
}
