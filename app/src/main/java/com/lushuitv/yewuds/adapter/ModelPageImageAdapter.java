package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.module.response.ContentListBean;

import java.util.List;

/**
 * Description 九宫格
 * Created by weip
 * Date on 2017/9/4.
 */

public class ModelPageImageAdapter extends BaseAdapter {

    private Context context;
    private List<ContentListBean> urlLists;

    public ModelPageImageAdapter(Context context, List<ContentListBean> urlLists) {
        this.context = context;
        this.urlLists = urlLists;
    }

    @Override
    public int getCount() {
        return urlLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.activity_model_page_gv_item, null);
            holder.modelGvItemImage = (ImageView) convertView.findViewById(R.id.model_page_gv_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageManager.getInstance().loadImage(context,urlLists.get(position).getContentUrl()+"?imageView2/0/w/190/h/190",holder.modelGvItemImage);
        return convertView;
    }

    class ViewHolder {
        private ImageView modelGvItemImage;
    }
}

