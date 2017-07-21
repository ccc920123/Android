package com.cdjysdkj.diary.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdjysdkj.diary.BillActivity;
import com.cdjysdkj.diary.R;
import com.cdjysdkj.diary.tab.DillTab;
import com.cdjysdkj.diary.view.ZQView;

public class BillExpandableAdapter extends BaseExpandableListAdapter {
    private String[] groupItem;
    private DillTab[][] childItem;
    private Activity context;
    private Integer[] img1 = {R.drawable.type_big_1, R.drawable.type_big_2,
            R.drawable.type_big_3, R.drawable.type_big_4,
            R.drawable.type_big_5, R.drawable.type_big_6,
            R.drawable.type_big_7, R.drawable.type_big_8,
            R.drawable.type_big_9, R.drawable.type_big_10,
            R.drawable.type_big_11, R.drawable.type_big_12};
    private Integer[] img2 = {R.drawable.type_big_13, R.drawable.type_big_14,
            R.drawable.type_big_15, R.drawable.type_big_16,
            R.drawable.type_big_17, R.drawable.type_big_18,
            R.drawable.type_big_19};

    /**
     * @param groupItem
     * @param childItem
     * @param activity
     */
    public BillExpandableAdapter(String[] groupItem, DillTab[][] childItem,
                                 Activity activity) {
        super();
        this.groupItem = groupItem;
        this.childItem = childItem;
        this.context = activity;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItem[groupPosition][childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return childItem == null ? 0 : childItem[groupPosition] == null ? 0
                : childItem[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupItem[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return groupItem == null ? 0 : groupItem.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        ZQView slideView = (ZQView) convertView;
        if (slideView == null) {
            View itemView = context.getLayoutInflater().inflate(
                    R.layout.expand_child, null);
            slideView = new ZQView(context);
            slideView.setContentView(itemView);
            childViewHolder = new ChildViewHolder(slideView);
            slideView.setTag(childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) slideView.getTag();
        }
        try {

            slideView.shrink();
             int state = Integer.parseInt(childItem[groupPosition][childPosition]
                    .getTakeType());
            childViewHolder.taketime.setText(childItem[groupPosition][childPosition].getDetailtime());
            if (state == 0) {// 支出
                childViewHolder.imgbill.setImageResource(img1[Integer
                        .parseInt(childItem[groupPosition][childPosition]
                                .getIndexImg())]);
                childViewHolder.billshow_text
                        .setText(childItem[groupPosition][childPosition].getType()
                                + "  "
                                + childItem[groupPosition][childPosition]
                                .getMoney());
                childViewHolder.billshow_text.setVisibility(View.VISIBLE);
                childViewHolder.zuobillshow_text.setVisibility(View.GONE);
            }
            if (state == 1) {// 收入
                childViewHolder.imgbill.setImageResource(img2[Integer
                        .parseInt(childItem[groupPosition][childPosition]
                                .getIndexImg())]);
                childViewHolder.zuobillshow_text
                        .setText(childItem[groupPosition][childPosition].getType()
                                + "  "
                                + childItem[groupPosition][childPosition]
                                .getMoney());
                childViewHolder.zuobillshow_text.setVisibility(View.VISIBLE);
                childViewHolder.billshow_text.setVisibility(View.GONE);
            }
            childViewHolder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 删天

                    ((BillActivity)context).deletDay(String.valueOf(childItem[groupPosition][childPosition].getID()));
                }
            });
        } catch (Exception e) {

        }
        return slideView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final GroupViewHolder groupViewHolder;
        ZQView slideView = (ZQView) convertView;
        if (slideView == null) {
            View itemView = context.getLayoutInflater().inflate(
                    R.layout.expand_group, null);


            slideView = new ZQView(context);
            slideView.setContentView(itemView);
            groupViewHolder = new GroupViewHolder(slideView);
            slideView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) slideView.getTag();
        }
        slideView.shrink();
        groupViewHolder.groupText.setText(groupItem[groupPosition]);
        groupViewHolder.groupcash.setText(cashToMonth(groupPosition));
        if (isExpanded) {
            groupViewHolder.groupImg
                    .setBackgroundResource(R.drawable.p_arrow_down_gray);
        } else {
            groupViewHolder.groupImg
                    .setBackgroundResource(R.drawable.p_arrow_up_gray);
        }
        groupViewHolder.deleteHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 删除月
                ((BillActivity)context).deletMoth(String.valueOf(groupViewHolder.groupText.getText()));

            }
        });
        return slideView;
    }

    /**
     * 计算当月的余
     *
     * @param groupPosition
     * @return
     */
    private String cashToMonth(int groupPosition) {// 计算当月的余
        try {
            DillTab group[] = childItem[groupPosition];
            int cost = 0;
            for (int i = 0; i < group.length; i++) {
                DillTab dillTab = group[i];
                int state = Integer.parseInt(dillTab.getTakeType());
                if (state == 0) {// 支出
                    cost -= Integer.parseInt(dillTab.getMoney());
                }
                if (state == 1) {// 收入
                    cost += Integer.parseInt(dillTab.getMoney());
                }
            }
            return "¥  " + String.valueOf(cost);
        } catch (Exception e) {
            return "¥ 0 ";
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    class GroupViewHolder {
        TextView groupText;
        TextView groupcash;
        ImageView groupImg;
        ViewGroup deleteHolder;

        GroupViewHolder(View convertView) {
            groupText = (TextView) convertView
                    .findViewById(R.id.expandable_title);
            groupcash = (TextView) convertView
                    .findViewById(R.id.cash);
            groupImg = (ImageView) convertView
                    .findViewById(R.id.group_arrow);
            deleteHolder = (ViewGroup) convertView.findViewById(R.id.holder);
        }
    }

    class ChildViewHolder {
        TextView taketime;
        TextView billshow_text;
        TextView zuobillshow_text;
        ImageView imgbill;
        View xian;
        ViewGroup deleteHolder;

        ChildViewHolder(View convertView) {
            billshow_text = (TextView) convertView
                    .findViewById(R.id.billshow_text);
            imgbill = (ImageView) convertView
                    .findViewById(R.id.imgbill);
            taketime = (TextView) convertView.findViewById(R.id.taketime);
            zuobillshow_text = (TextView) convertView
                    .findViewById(R.id.zuobillshow_text);
            xian = (View) convertView.findViewById(R.id.xian);
            deleteHolder = (ViewGroup) convertView.findViewById(R.id.holder);

        }

    }
}
