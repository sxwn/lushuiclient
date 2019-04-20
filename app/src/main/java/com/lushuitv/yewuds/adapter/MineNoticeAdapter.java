package com.lushuitv.yewuds.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.ModelPageActivity;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.module.response.UserAttentionListBean;
import com.lushuitv.yewuds.module.response.UserAttentionResult;
import com.socks.library.KLog;

import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description 我的关注适配器
 * Created by weip
 * Date on 2017/9/1.
 */

public class MineNoticeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<UserAttentionListBean.AttantionUser> lists;


    public MineNoticeAdapter(Context context, List<UserAttentionListBean.AttantionUser> lists) {
        this.context = context;
        this.lists = lists;
        this.mInflater = LayoutInflater.from(context);
    }


    public void setData(List<UserAttentionListBean.AttantionUser> lists) {
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoticeHolder noticeHolder = null;
        UserAttentionListBean.AttantionUser attantionUser = lists.get(position);
        if (convertView == null) {
            noticeHolder = new NoticeHolder();
            convertView = mInflater.inflate(R.layout.activity_mine_notice_item, null);
            noticeHolder.userHeadview = (ImageView) convertView.findViewById(R.id.notice_item_usericon);
            noticeHolder.userName = (TextView) convertView.findViewById(R.id.notice_item_username);
            noticeHolder.userAttention = (TextView) convertView.findViewById(R.id.notice_item_attention);
            convertView.setTag(noticeHolder);
        } else {
            noticeHolder = (NoticeHolder) convertView.getTag();
        }
        noticeHolder.userAttention.setText("已关注");
        noticeHolder.userName.setText(attantionUser.getActorName());
        ImageManager.getInstance().loadRoundImage(context, attantionUser.getActorHeadshot(), noticeHolder.userHeadview);
        noticeHolder.userHeadview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ModelPageActivity.class);
                in.putExtra("actor_id", attantionUser.getActorId());
                context.startActivity(in);
            }
        });
        final NoticeHolder holder = noticeHolder;
        noticeHolder.userAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (attantionUser.getIsFocus() == 1) {
//                    UIUtils.showToast("关注");
//                    attentionCommit(holder, attantionUser);//关注
//                } else if (attantionUser.getIsFocus() == 2) {
//                    UIUtils.showToast("取消关注");
//                    attentionCacel(holder, attantionUser);//取消关注
//                }
            }
        });
        return convertView;
    }

    private void attentionCacel(NoticeHolder noticeHolder, UserAttentionListBean.AttantionUser attantionUser) {
        ApiRetrofit.getInstance().getApiService().getCancelAttention(attantionUser.getActorId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserAttentionResult>() {

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserAttentionResult userAttentionResult) {
                        KLog.e("onNext" + userAttentionResult.getSTATUS());
                        if ("0".equals(userAttentionResult.getSTATUS())) {
                            attantionUser.setActorFans(1);
                            noticeHolder.userAttention.setText("关注");
                        }
                    }
                });
    }

    class NoticeHolder {
        ImageView userHeadview;
        TextView userName, userAttention;
    }
    private void attentionCommit(NoticeHolder noticeHolder, UserAttentionListBean.AttantionUser user) {
        ApiRetrofit.getInstance().getApiService().getCommitAttention(user.getActorId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserAttentionResult>() {

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("onError" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserAttentionResult userAttentionResult) {
                        KLog.e("onNext" + userAttentionResult.getSTATUS());
                        if ("0".equals(userAttentionResult.getSTATUS())) {
                            user.setActorFans(2);
                            noticeHolder.userAttention.setText("取消关注");
                        }
                    }
                });
    }

}
