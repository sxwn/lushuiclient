package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.entity.LISTBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/15.
 */

public class CollectCommonResponse extends HttpBean{


    /**
     * LIST : [{"contentDuration":"03:00","isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"http://work-1254218478.picsh.myqcloud.com/014_fenmian.png","worksId":6,"worksPlayNum":0,"worksPraising":0,"worksType":1},{"contentDuration":"02:21","isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"http://work-1254218478.picsh.myqcloud.com/016_fenmian.png","worksDescription":"2","worksId":2,"worksName":"性感文艺的女神范","worksPlayNum":0,"worksPraising":0,"worksType":1},{"contentDuration":"2:13","isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"http://work-1254218478.picsh.myqcloud.com/sufei_fengmian.png","worksDescription":"2","worksId":1,"worksName":"路边野餐","worksPlayNum":0,"worksPraising":0,"worksType":1}]
     * MSG : SUCCESS
     * OBJECT : {}
     * STATUS : 0
     */

    private List<WorksListBean> LIST;


    public List<WorksListBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<WorksListBean> LIST) {
        this.LIST = LIST;
    }


}
