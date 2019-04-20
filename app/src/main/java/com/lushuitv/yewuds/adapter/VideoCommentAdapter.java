package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.module.response.CommentResponse;

import java.util.List;

/**
 * Description 视频评论适配器
 * Created by weip
 * Date on 2017/8/31.
 */

public class VideoCommentAdapter extends RecyclerView.Adapter<VideoCommentAdapter.VideoCommentHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<CommentResponse.OBJECTBean.CommentListBean> lists;

    public VideoCommentAdapter(Context context, List<CommentResponse.OBJECTBean.CommentListBean> lists) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lists = lists;
    }

    @Override
    public VideoCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoCommentHolder holder = new VideoCommentHolder(inflater.inflate(R.layout.activity_video_comment_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoCommentHolder holder, int position) {
        CommentResponse.OBJECTBean.CommentListBean commentListBean = lists.get(position);
        if (commentListBean.getUserHeadshot() != null){
            if (commentListBean.getUserHeadshot().startsWith("http://")){
                ImageManager.getInstance().loadRoundImage(context,lists.get(position).getUserHeadshot()
                        + "?imageView2/0/w/750/h/350",holder.userHeadIcon);
            }else{
                ImageManager.getInstance().loadRoundImage(context,"http://"+lists.get(position).getUserHeadshot()
                        + "?imageView2/0/w/750/h/350",holder.userHeadIcon);
            }
        }else{
            ImageManager.getInstance().loadRoundImage(context,R.mipmap.logo,holder.userHeadIcon);
        }
        holder.author.setText(commentListBean.getUserName());
        holder.content.setText(commentListBean.getCommentContent());
        holder.commentTime.setVisibility(View.GONE);
    }

    public void setData(List<CommentResponse.OBJECTBean.CommentListBean> lists){
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class VideoCommentHolder extends RecyclerView.ViewHolder {

        private ImageView userHeadIcon;
        private TextView author,content,commentTime;
        private View line;


        public VideoCommentHolder(View itemView) {
            super(itemView);
            userHeadIcon = (ImageView) itemView.findViewById(R.id.comment_item_userhead);
            author = (TextView) itemView.findViewById(R.id.item_content_author);
            content = (TextView) itemView.findViewById(R.id.item_content_content);
            commentTime = (TextView) itemView.findViewById(R.id.item_content_time);
            line = itemView.findViewById(R.id.item_divider_line);
        }
    }
}
