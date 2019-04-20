package com.lushuitv.yewuds.module.response;

import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.entity.WorksListBean;

import java.util.List;

/**
 * Description
 * Created by weip
 * Date on 2017/9/14.
 */

public class HomeAll extends HttpBean{


    /**
     * MSG : SUCCESS
     * OBJECT : [{"actorHeadshot":"aaa.jpg","actorName":"梦心月","contentDuration":"02:21","isColletion":0,"isPraising":0,"worksActor":"1","worksCommentNum":0,"worksCover":"http://work-1254218478.picsh.myqcloud.com/016_fenmian.png","worksDate":"2017-08-24 17:15:04.0","worksDescription":"2","worksId":2,"worksName":"性感文艺的女神范","worksPlayNum":129,"worksPraising":3,"worksType":1},{"contentDuration":"03:00","isColletion":0,"isPraising":0,"worksCommentNum":0,"worksCover":"http://work-1254218478.picsh.myqcloud.com/014_fenmian.png","worksDate":"2017-09-12 16:17:07.0","worksId":6,"worksPlayNum":120,"worksPraising":9,"worksType":1},{"actorHeadshot":"aaa.jpg","actorName":"梦心月","contentDuration":"2:13","isColletion":0,"isPraising":0,"worksActor":"1","worksCommentNum":0,"worksCover":"http://work-1254218478.picsh.myqcloud.com/sufei_fengmian.png","worksDate":"2017-08-24 17:11:41.0","worksDescription":"2","worksId":1,"worksName":"路边野餐","worksPlayNum":86,"worksPraising":13,"worksType":1}]
     * STATUS : 0
     */

    private List<WorksListBean> OBJECT;

    public List<WorksListBean> getOBJECT() {
        return OBJECT;
    }

    public void setOBJECT(List<WorksListBean> OBJECT) {
        this.OBJECT = OBJECT;
    }


}
