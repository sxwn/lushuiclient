package com.lushuitv.yewuds.search;

import com.lushuitv.yewuds.base.BaseView;
import com.lushuitv.yewuds.module.response.HomeAll;
import com.lushuitv.yewuds.module.response.NewVideoResponse;

import java.util.List;

/**
 * @author weip
 * @date
 */

public interface SearchContract {

    interface View extends BaseView {
        /**
         * 给搜索框显示搜索条件
         *
         * @param msg
         */
        void setEditText(String msg);

        /**
         * 是否显示搜索结果，显示搜索结果时需要隐藏掉热门搜索和历史搜索
         *
         * @param flag
         */
        void showSearchResult(boolean flag);

        /**
         * 更新界面列表数据
         */
        void updateShow(NewVideoResponse newVideoResponse, int type);

        /**
         * 显示错误提示信息
         *
         * @param msg
         */
        void showErrorTip(String msg);

        void setPrestener();
    }

    interface Prestener {
        /**
         * 加载热门搜索标签数据
         *
         * @return
         */
        List<String> loadHotTag();

        /**
         * 从服务端获取数据
         *
         * @param keyword
         * @param page
         * @param type
         */
        void getDataFromService(String keyword, int page, int type);

        /**
         * 根据搜索条件从服务端查询数据
         *
         * @param keyword
         */
        void searchFromServer(String keyword);

    }

}
