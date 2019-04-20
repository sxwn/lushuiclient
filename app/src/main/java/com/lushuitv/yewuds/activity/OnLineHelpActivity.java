package com.lushuitv.yewuds.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.flyn.Eyes;

/**
 * Description
 * Created by weip
 * Date on 2017/10/10.
 */

public class OnLineHelpActivity extends BaseActivity {

    TextView onHelpContent;
    private View mView;

    @Override
    protected View initContentView() {
        mView = View.inflate(this, R.layout.activity_online_help, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        mToolbarTitle.setText("帮助");
        setSupportActionBar(mToolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.black));
    }

    @Override
    public void initOptions() {
        onHelpContent = (TextView) mView.findViewById(R.id.online_help_content);
        onHelpContent.setText("1:如何下载露水TV：\n" +
                "我们的应用更新会在第一时间在ios应用商店及安卓市场发布更新，同时也可以在官网www.lushuitv.com及关注微信公众号露水TV下载使用。\n" +
                "\n" +
                "2：打开露水TV后无内容：\n" +
                "a：您当前网络状况不佳\n" +
                "b：您当前运营商DNS解析问题\n" +
                "c：您设置了代理模式访问\n" +
                "d：a和b您只需下拉刷新即可，c请在手机-设置中关闭代理上网即可\n" +
                "\n" +
                "3：视频付费后无法正常观看\n" +
                "我们所有付费的视频提供免费的预览片段，如需观看全部精彩内容按照提示付费即可。付费后如无法观看，请直接联系我们的在线客服，加QQ：1083152940提供您的露水TV ID第一时间为您解决。\n" +
                "\n" +
                "4：关于转发：\n" +
                "所有视频您都可以分享转发，转发的人观看后您将获得相应财务收益，收益颇丰，欢迎转发。且被转发视频您付费的情况下相关财务收益可以直接提现。\n" +
                "\n" +
                "5：关于提现：\n" +
                "所有您付费的视频在转发后在您账户余额里将变成可以提现的金额，你可以随时安排提现。单笔金额为>=100元，每笔收取手续费两元。提现操作会在T+1时间安排处理到您指定支付宝账户。如果累计每月获得提现金额较大，则由本公司安排代缴个税。本协议具体内容解释权归本公司所有。\n" +
                "\n" +
                "6：如何获得积分：\n" +
                "对于每月的付费和转发排行榜中前20名的用户我们会有额外的积分赠送，积分可以兑换相应产品。同时对于每天点赞和好评数量多的用户我们也会有额外积分赠送。\n" +
                "7：关于评论：\n" +
                "a. 不得发表：煽动抗拒、破坏宪法和法律、行政法规实施的言论，煽动颠覆国家政权，推翻社会主义制度的言论，煽动分裂国家、破坏国家统一的言论，煽动民族仇恨、民族歧视、破坏民族团结的言论.\n" +
                "b. 不得发表任何违法犯罪的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、淫秽的、不文明的信息资料。\n" +
                "c. 未经本公司同意，禁止用户在露水TV评论上发布任何形式的广告\n");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_toolbar, menu);
        updateOptionsMenu(menu);
        return true;
    }

    protected void updateOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        menu.findItem(R.id.action_download).setVisible(false);
    }
}
