package com.lushuitv.yewuds.api;


import com.lushuitv.yewuds.boot.module.DataType;
import com.lushuitv.yewuds.coin.CoinListResponse;
import com.lushuitv.yewuds.coin.CoinResponse;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.response.AccountInfoResponse;
import com.lushuitv.yewuds.module.response.ActorWorkInfo;
import com.lushuitv.yewuds.module.response.AuthCodeResponse;
import com.lushuitv.yewuds.module.response.BannerResponse;
import com.lushuitv.yewuds.module.response.BeenPayResult;
import com.lushuitv.yewuds.module.response.CollectCommonResponse;
import com.lushuitv.yewuds.module.response.CollectResponse;
import com.lushuitv.yewuds.module.response.ComResponse;
import com.lushuitv.yewuds.module.response.CommentResponse;
import com.lushuitv.yewuds.module.response.FenXiaoResponse;
import com.lushuitv.yewuds.module.response.HomeAll;
import com.lushuitv.yewuds.module.response.NewVideoResponse;
import com.lushuitv.yewuds.module.response.RankIncomeResponse;
import com.lushuitv.yewuds.module.response.RankWorksBuyResponse;
import com.lushuitv.yewuds.module.response.TradeRecordResponse;
import com.lushuitv.yewuds.module.response.UnfreeWorkLists;
import com.lushuitv.yewuds.module.response.UserAttentionListBean;
import com.lushuitv.yewuds.module.response.UserAttentionResult;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.module.response.UserPurseResponse;
import com.lushuitv.yewuds.module.response.VersionInfo;
import com.lushuitv.yewuds.module.response.VideoPathResponse;
import com.lushuitv.yewuds.module.response.VideoUrlInfo;
import com.lushuitv.yewuds.module.response.WorksResponse;
import com.lushuitv.yewuds.vip.VipBuyListResponse;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import io.reactivex.Observable;

import static com.lushuitv.yewuds.presenter.AutoLogin.userId;

/**
 * 网络请求的service
 */

public interface ApiService {
    //版本校验接口
    @POST("ver/checkVersion.do?")
    Observable<VersionInfo> getCheckVersion(@Query("version") String version, @Query("type") int phoneType);

    @FormUrlEncoded
    @POST("purse/buyGold.do")
    Observable<HttpBean> getGoldCosumer(@FieldMap Map<String, Object> map);//金币消费接口

    @POST("user/getUserBuyInsiderList.do")
    Observable<VipBuyListResponse> getVipBuyList(@Query("userId") String userId, @Query("version") String version);//金币消费接口

    @POST("user/getPayList.do?type=1")
    Observable<CoinResponse> getCoinBuyList();//金币购买列表接口

    @GET("ver/userCheckVersion.do?clientVersion=1.1&osType=anzhuo_vivo")
    Observable<DataType> getHiddleValue();

    @GET("getNewWorksList.do?")
    Observable<NewVideoResponse> getNewVideoList();

    @GET("getVerNewWorksList.do?")
    Observable<NewVideoResponse> getVerNewVideoList();

    @POST("getSpecialWorksList.do")
    Observable<NewVideoResponse> getSpecialVideoList(@Query("currentPage") int page, @Query("pageSize") int pageSize);

    @POST("getFreeWorks.do")
    Observable<CollectCommonResponse> getFreeList(@Query("currentPage") int page, @Query("pageSize") int pageSize);

    @POST("getVerFreeWorks.do")
    Observable<CollectCommonResponse> getVerFreeList(@Query("currentPage") int page, @Query("pageSize") int pageSize);

    @POST("getFreeInfo.do")
    Observable<UnfreeWorkLists> getUnfreeDetail(@Query("worksId") int worksId);

    @POST("getVerFreeInfo.do")
    Observable<UnfreeWorkLists> getVerUnfreeDetail(@Query("worksId") int worksId);


    //精选和全部一样
    @POST("getAllWorks.do")
    Observable<HomeAll> getGoodVideoList(@Query("currentPage") int page, @Query("pageSize") int pageSize);

    @POST("getBanner.do")
    Observable<BannerResponse> getBanner();

    @POST("getVerBanner.do")
    Observable<BannerResponse> getVerBanner();

    //全部
    @POST("getAllWorks.do")
    Observable<HomeAll> getAllList(@Query("currentPage") int page, @Query("pageSize") int pageSize);

    //全部
    @POST("getVerAllWorks.do")
    Observable<HomeAll> getVerAllList(@Query("currentPage") int page, @Query("pageSize") int pageSize);

    //获取播放地址
    @GET("playWorks.do")
    Observable<VideoUrlInfo> getVideoUrl(@Query("worksId") int worksId,@Query("version") String version,@Query("type") int type);
    @GET("playVerWorks.do")
    Observable<VideoUrlInfo> getVerVideoUrl(@Query("worksId") int worksId);

    /**
     * 获取视频数据json
     *
     * @param link
     * @param r
     * @param s
     * @return
     */
    @GET("http://service.iiilab.com/video/toutiao")
    Observable<VideoPathResponse> getVideoPath(@Query("link") String link, @Query("r") String r, @Query("s") String s);

    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @POST("userVerificationCode.do")
    Observable<AuthCodeResponse> getPhoneCode(@Query("userTel") String phone, @Query("type") int type);

    //注册
    @POST("userRegister.do")
    Observable<UserInfoResponse> getRegister(@Query("userTel") String userPhone,
                                             @Query("veryCode") String code,
                                             @Query("userPassword") String usePwd,
                                             @Query("version") String softVersion,
                                             @Query("model") String phoneModel);

    //登录
    @FormUrlEncoded
    @POST("userLogin.do")
    Observable<UserInfoResponse> getLogin(@FieldMap Map<String, Object> map);

//    @POST("userLogin.do")
//    Observable<UserInfoResponse> getLogin(@Query("userTel") String userPhone,
//                                          @Query("veryCode") String veryCode, @Query("appNum") String appNum);

    @POST("userWxLogin.do")
    Observable<UserInfoResponse> getWeChatLogin(@Query("access_token") String access_token,
                                                @Query("userWechatOpenid") String userWechatOpenid,
                                                @Query("version") String version);

    @POST("autoLogin.do")
    Observable<UserInfoResponse> getAutoLogin(@Query("userTel") String userPhone, @Query("codePassword") String usePwd,
                                              @Query("type") int type);

    @POST("autoLogin.do")
    Observable<UserInfoResponse> getWeChatAutoLogin(@Query("type") int type, @Query("codePassword") String codePassword,
                                                    @Query("userWechatOpenid") String userWechatOpenid);

    @FormUrlEncoded
    @POST("purse/findGold.do")
    Observable<CoinResponse> getFindGold(@FieldMap Map<String, String> map);//钱包显示接口

    @POST("user/getUserInfo.do")
    Observable<UserInfoResponse> getUserInfo();

    @POST("user/userLogout.do")
    Observable<UserInfoResponse> getUserLogout();

    @POST("findPassword.do")
    Observable<UserInfoResponse> getFindPwd(@Query("userTel") String userPhone, @Query("veryCode") String code,
                                            @Query("userPassword") String usePwd);

    @POST("user/boundTel.do")
    Observable<HttpBean> getBindPhone(@Query("userTel") String userPhone, @Query("userPassword") String usePwd
            , @Query("veryCode") String code);


    @POST("user/collectWorks.do")
    Observable<CollectResponse> getCollect(@Query("worksId") int worksId, @Query("isColletion") int isColletion);

    @POST("praising.do")
    Observable<CollectResponse> getIsPraising(@Query("worksId") int worksId, @Query("isPraising") int isPraising);

    @POST("user/getCollectionByUser.do")
    Observable<CollectCommonResponse> getCollectList(@Query("worksType") int worksType, @Query("currentPage") int currentPage);

    @POST("user/changeUserInfo.do")
    Observable<UserInfoResponse> getChangeUserInfo(@Query("userId") String userId, @Query("userName") String userName);

    @POST("user/changeUserHeadShot.do")
    Observable<UserInfoResponse> getChangeUserHeadShot(@Query("userId") String userId, @Query("userHeadshot") String userHeadshot);


    @POST("user/beeCloudPay.do")
    Observable<BeenPayResult> getBeeCloudPay(@Query("type") int type, @Query("money") int money, @Query("worksId") int worksId);

    //购买金币接口
    @POST("user/beeCloudPayGold.do")
    Observable<BeenPayResult> getBeeCloudPayGold(@Query("type") int type, @Query("money") int money, @Query("id") int id);

    @POST("user/cancelCols.do")
    Observable<HttpBean> getCancelCols(@Query("worksIds") String worksIds);

    @POST("getWorksComment.do")
    Observable<CommentResponse> getCommentData(@Query("worksId") int worksId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize
            , @Query("worksActor") int worksActor);


    @POST("commentWorks.do")
    Observable<ComResponse> getCommentWorks(@Query("commentWorks") int worksId, @Query("commentContent") String content);

    @POST("getActorInfo.do")
    Observable<ActorWorkInfo> getActorInfo(@Query("actorId") int actorId);

    @POST("getActorSefInfo.do")
    Observable<ActorWorkInfo> getActorSelfInfo(@Query("userId") String userId,@Query("version") String version,@Query("actorId") int actorId);

    @GET("user/getActorsByUser.do")
    Observable<UserAttentionListBean> getUserAttentionList(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    @POST("operateRelation.do")
    Observable<UserAttentionResult> getAttentionEvent(@Query("actorId") int actorId, @Query("isFocus") int isFocus);

    //取消关注
    @POST("subRelation.do")
    Observable<UserAttentionResult> getCancelAttention(@Query("actorId") int actorId);

    //加关注
    @POST("addRelation.do")
    Observable<UserAttentionResult> getCommitAttention(@Query("actorId") int actorId);

    @POST("user/orderList.do")
    Observable<TradeRecordResponse> getOrderList(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    @POST("user/getUserPurse.do")
    Observable<UserPurseResponse> getUserPurse();

    //分销
    @POST("user/queryBranch.do?")
    Observable<FenXiaoResponse> getUserBranch(@Query("currentPage") int page, @Query("pageSize") int pageSize);


    @POST("searchWorks.do")
    Observable<NewVideoResponse> getSearchWorks(@Query("keyWords") String keyWords);

    /*************************** 排行*********************************************/
    @POST("getCollectionRanking.do")
    Observable<WorksResponse> getCollectionRanking();

    @POST("getWorksBuyRanking.do")
    Observable<RankWorksBuyResponse> getWorksBuyRanking();

    @POST("getUserBuyRanking.do")
    Observable<RankIncomeResponse> getUserBuyRanking();

    @POST("getUserRewardRanking.do")
    Observable<RankIncomeResponse> getUserRewordRanking();

    /***************************提现********************************************/
//    @POST("user/setPayPassword.do")
//    Observable<HttpBean> getSetPayPassword(@Query("userPayPassword") String userPayPassword);
    @POST("user/setPayPassword.do")
    Observable<HttpBean> getSetPayPassword(@Query("userId") String userId, @Query("version") String version,
                                           @Query("userPayPassword") String userPayPassword);

    @POST("user/verifyPayPassword.do")
    Observable<HttpBean> getVerifyPayPassword(@Query("userPayPassWord") String userPayPassword);

    @POST("/user/forgetPayPassword.do")
//设置忘记支付密码
    Observable<HttpBean> getForgetPayPassword(@Query("userPayPassWord") String userPayPassword);

    @POST("user/queryUserAccount.do")
    Observable<AccountInfoResponse> getQueryUserAccount();

    //addUserBank.do
    @POST("user/newAddUserBank.do")
    Observable<HttpBean> getAddUserBank(@Query("bankCard") String bankCard, @Query("bankCardType")
            int bankCardType, @Query("bankOwner") String bankOwner);

    @POST("user/userWithdraw.do")
    Observable<HttpBean> getUserWithDraw(@Query("bankId") int bankId, @Query("withdrawMoney") double withdrawMoney);


    //分销
    @POST("purse/findInRecord.do")
    Observable<CoinListResponse> getIncomeRecord(@Query("version") String version, @Query("userId") String userid,
                                                 @Query("currentPage") int currentpage, @Query("pageSize") int size);

    //交易记录<收入>
    @POST("purse/findOutRecord.do")
    Observable<CoinListResponse> getConsumeRecord(@Query("version") String version, @Query("userId") String userid,
                                                  @Query("currentPage") int currentpage, @Query("pageSize") int size);

    //打赏排行榜
    @POST("getGiveListByActor.do")
    Observable<CoinListResponse> getActorRewardRank(@Query("userId") String userId, @Query("version") String version,
                                                    @Query("actorId") int actorId);

    //艺人图像上传
//    @POST("person/uploadimg.do")
//    Observable<HttpBean> getActorImageUpload(@Query("img") Object object, @Query("actorId") int actorId);
    //发表艺人图片
    @Multipart
    @POST("person/uploadimg.do")
    Observable<HttpBean> getActorImageUpload(@Query("userId") String userId,
                                             @Query("version") String version,
                                             @Query("actorId") int actorId,
                                             @PartMap Map<String, RequestBody> img
                                             );
}

