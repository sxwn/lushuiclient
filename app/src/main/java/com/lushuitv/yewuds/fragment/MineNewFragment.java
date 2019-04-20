package com.lushuitv.yewuds.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.activity.BootActivity;
import com.lushuitv.yewuds.activity.LoginTranslucentActivtiy;
import com.lushuitv.yewuds.activity.MineCollectActivity;
import com.lushuitv.yewuds.activity.MineFenxiaoActivity;
import com.lushuitv.yewuds.activity.MineMoneyActiivity;
import com.lushuitv.yewuds.activity.MineNoticeActiivity;
import com.lushuitv.yewuds.activity.MineSetActiivity;
import com.lushuitv.yewuds.activity.MineTradeActiivity;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.app.MyApp;
import com.lushuitv.yewuds.coin.MineCoinActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.flyn.Eyes;
import com.lushuitv.yewuds.module.entity.UseParams;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.presenter.AutoLogin;
import com.lushuitv.yewuds.push.PushMessageActivity;
import com.lushuitv.yewuds.user.LoginActivity;
import com.lushuitv.yewuds.utils.CustomPopupWindow;
import com.lushuitv.yewuds.utils.UIUtils;
import com.lushuitv.yewuds.utils.UpLoadImageTecentYun;
import com.lushuitv.yewuds.vip.VipCenterActivity;
import com.socks.library.KLog;
import com.tencent.cos.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Description
 * Created by weip
 * Date on 2017/10/30.
 */

public class MineNewFragment extends Fragment implements View.OnClickListener {

    private ImageView msgTv;
    private TextView usernameTv;
    private RelativeLayout editUsername;
    private ImageView userImageIv;
    private static final int LOGIN_REQUEST_CODE = 0;
    TextView moneyTv, noticeTv, fenxiaoTv, tradeTv, setTv, collectTv;

    CustomPopupWindow mPop;
    private static String photoFileStr;// 文件名
    Button takePhoto, selectPhoto, cacel;
    CustomPopupWindow editnamePop;
    View view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_fragment, container, false);
        initView(view);
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(View view) {
        msgTv = (ImageView) view.findViewById(R.id.fragment_new_top_msg);
        msgTv.setOnClickListener(this);
        usernameTv = (TextView) view.findViewById(R.id.framgent_new_top_username);
        editUsername = (RelativeLayout) view.findViewById(R.id.framgent_new_top_editname);
        editUsername.setOnClickListener(this);
        userImageIv = (ImageView) view.findViewById(R.id.framgent_new_top_userimage);
        userImageIv.setOnClickListener(this);
        Drawable moneyDrawable = getResources().getDrawable(R.mipmap.mine_money_icon, null);
        moneyDrawable.setBounds(0, 0, UIUtils.dip2Px(getActivity(), 50), UIUtils.dip2Px(getActivity(), 50));
        Drawable noticeDrawable = getResources().getDrawable(R.mipmap.mine_notice_icon, null);
        noticeDrawable.setBounds(0, 0, UIUtils.dip2Px(getActivity(), 50), UIUtils.dip2Px(getActivity(), 50));
        Drawable fenxiaoDrawable = getResources().getDrawable(R.mipmap.mine_fenxiao_icon, null);
        fenxiaoDrawable.setBounds(0, 0, UIUtils.dip2Px(getActivity(), 50), UIUtils.dip2Px(getActivity(), 50));
        Drawable tradeDrawable = getResources().getDrawable(R.mipmap.mine_trade_icon, null);
        tradeDrawable.setBounds(0, 0, UIUtils.dip2Px(getActivity(), 50), UIUtils.dip2Px(getActivity(), 50));
        Drawable setDrawable = getResources().getDrawable(R.mipmap.mine_systemset_icon, null);
        setDrawable.setBounds(0, 0, UIUtils.dip2Px(getActivity(), 50), UIUtils.dip2Px(getActivity(), 50));
        Drawable collectDrawable = getResources().getDrawable(R.mipmap.mine_collect_icon, null);
        collectDrawable.setBounds(0, 0, UIUtils.dip2Px(getActivity(), 50), UIUtils.dip2Px(getActivity(), 50));
        moneyTv = (TextView) view.findViewById(R.id.mine_new_fragment_money);
        moneyTv.setOnClickListener(this);
        noticeTv = (TextView) view.findViewById(R.id.mine_new_fragment_notice);
        noticeTv.setOnClickListener(this);
        fenxiaoTv = (TextView) view.findViewById(R.id.mine_new_fragment_fenxiao);
        fenxiaoTv.setOnClickListener(this);
        tradeTv = (TextView) view.findViewById(R.id.mine_new_fragment_trade);
        tradeTv.setOnClickListener(this);
        setTv = (TextView) view.findViewById(R.id.mine_new_fragment_set);
        setTv.setOnClickListener(this);
        collectTv = (TextView) view.findViewById(R.id.mine_new_fragment_collect);
        collectTv.setOnClickListener(this);
        moneyTv.setCompoundDrawables(null, moneyDrawable, null, null);
        noticeTv.setCompoundDrawables(null, noticeDrawable, null, null);
        fenxiaoTv.setCompoundDrawables(null, fenxiaoDrawable, null, null);
        tradeTv.setCompoundDrawables(null, tradeDrawable, null, null);
        setTv.setCompoundDrawables(null, setDrawable, null, null);
        collectTv.setCompoundDrawables(null, collectDrawable, null, null);
        //
        View popview = LayoutInflater.from(getActivity()).inflate(R.layout.custom_popup_window_layout, null);
        takePhoto = (Button) popview.findViewById(R.id.id_btn_take_photo);
        takePhoto.setOnClickListener(this);
        selectPhoto = (Button) popview.findViewById(R.id.id_btn_select);
        selectPhoto.setOnClickListener(this);
        cacel = (Button) popview.findViewById(R.id.id_btn_cancel);
        cacel.setOnClickListener(this);
        mPop = new CustomPopupWindow(getActivity(), popview);
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (usernameTv.getText().toString().equals("点击头像登录")) {
                    if (Config.getCachedUserId(getActivity()) != null) {
                        Config.cachePhoneNum(getActivity(), null);
                        Config.cachePwd(getActivity(), null);
                        Config.cacheUserId(getActivity(), null);
                        Config.cachePayStatus(getActivity(), 0);
                        Config.cacheUserCode(getActivity(), null);
                        MyApp.isLogout = true;
                        onResume();
                    }
                }
            }
        });
        //
        View editView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_edit_popup_window_layout, null);
        nameEt = (EditText) editView.findViewById(R.id.edit_input_et);
        nameCommitRl = (RelativeLayout) editView.findViewById(R.id.edit_commit);
        editnamePop = new CustomPopupWindow(getActivity(), editView);
        nameCommitRl.setOnClickListener(this);
    }

    EditText nameEt;
    RelativeLayout nameCommitRl;

    @Override
    public void onResume() {
        super.onResume();
        KLog.e("====================onResume");
        if (MyApp.isLogout) {
            ImageManager.getInstance().loadRoundImage(getActivity(), R.drawable.default_user_icon, userImageIv);
            usernameTv.setText("点击图像登录");
        } else {
            loadData();
        }
    }

    public void forceOpenSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fragment_new_top_msg://消息
                intent = new Intent(getActivity(),PushMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.framgent_new_top_userimage:
                if (Config.getCachedUserId(getActivity()) == null) {
//                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    //弹出窗口
                    //设置PopupWindow中的位置
                    mPop.showAtLocation(getActivity().findViewById(R.id.mine_new_fragment_rl), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.edit_commit:
                //提交修改用户名
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    UIUtils.showToast("用户名不能为空");
                    return;
                }
                ApiRetrofit.getInstance().getApiService().getChangeUserInfo(Config.getCachedUserId(getActivity()), nameEt.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserInfoResponse>() {

                            @Override
                            public void onError(Throwable e) {
                                if (editnamePop != null)
                                    editnamePop.dismiss();
                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UserInfoResponse userInfoResponse) {
                                if (editnamePop != null)
                                    editnamePop.dismiss();
                                if ("0".equals(userInfoResponse.getSTATUS())) {
                                    usernameTv.setText(nameEt.getText().toString());
                                    String cacheOpenId = Config.getCachedWeChatOpenId(getActivity());
                                    if (cacheOpenId != null && !"".equals(cacheOpenId)) {
                                        //微信登录
                                        Config.cacheWeChatName(getActivity(), nameEt.getText().toString());
                                    }
                                    nameEt.setText("");
                                }
                            }
                        });
                break;
            case R.id.framgent_new_top_editname://编辑用户名称
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
                    WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                    params.alpha = 0.7f;
                    getActivity().getWindow().setAttributes(params);
                    forceOpenSoftKeyboard(getActivity());
                    editnamePop.showAtLocation(getActivity().findViewById(R.id.mine_new_fragment_rl), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    editnamePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                            params.alpha = 1.0f;
                            getActivity().getWindow().setAttributes(params);
                        }
                    });
                }
                break;
            case R.id.id_btn_take_photo:
                //选择拍照
                if (mPop != null)
                    mPop.dismiss();
                if (Build.VERSION.SDK_INT >= 23) {
                    int writePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int cameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    if (writePermission == PackageManager.PERMISSION_GRANTED && cameraPermission == PackageManager.PERMISSION_GRANTED) {
                        toCamera();
                    } else {
                        //未授权,处理权限申请
                        UIUtils.showToast("请手动开启相关权限");
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , Manifest.permission.CAMERA}, 0);
                    }
                } else {
                    toCamera();
                }
                break;
            case R.id.id_btn_select:
                //选择相册
                if (mPop != null)
                    mPop.dismiss();
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        toGallery();
                    } else {
                        //未授权,处理权限申请
                        UIUtils.showToast("请手动开启相关权限");
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                } else {
                    toGallery();
                }
                break;
            case R.id.id_btn_cancel:
                if (mPop != null)
                    mPop.dismiss();
                break;
            case R.id.mine_new_fragment_money:
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
                    startActivity(new Intent(getActivity(), MineMoneyActiivity.class));
//                    startActivity(new Intent(getActivity(), MineCoinActivity.class));
                }
                break;
            case R.id.mine_new_fragment_notice:
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
                    startActivity(new Intent(getActivity(), MineNoticeActiivity.class));
                }
                break;
            case R.id.mine_new_fragment_fenxiao:
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
                    startActivity(new Intent(getActivity(), MineFenxiaoActivity.class));
                }
                break;
            case R.id.mine_new_fragment_trade://会员中心
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
//                    Intent in = new Intent(getActivity(), VipCenterActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("user", useParams);
//                    in.putExtra("userbundle",bundle);
//                    startActivity(in);
                    startActivity(new Intent(getActivity(), MineTradeActiivity.class));
                }
                break;
            case R.id.mine_new_fragment_set:
                startActivity(new Intent(getActivity(), MineSetActiivity.class));
                break;
            case R.id.mine_new_fragment_collect:
                if (Config.getCachedUserId(getActivity()) == null) {
                    intent = new Intent(getActivity(), LoginTranslucentActivtiy.class);
//                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                } else {
                    startActivity(new Intent(getActivity(), MineCollectActivity.class));
                }
                break;
        }
    }

    // 打开照相机
    private void toCamera() {
        // 文件的绝对路径
        String path = Environment.getExternalStorageDirectory() + "/" + "ls";
        File picDir = new File(path);
        if (!picDir.exists()) {
            picDir.mkdir();
        }
        photoFileStr = path + System.currentTimeMillis() + ".png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoFileStr)));
        startActivityForResult(intent, 301);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //处理权限
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    toCamera();
                } else {
                    UIUtils.showToast("未授权");
                }
                break;
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toGallery();
                } else {
                    UIUtils.showToast("未授权");
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 301 && resultCode == RESULT_OK) {
            File temp = new File(photoFileStr);
            startPhotoZoom(Uri.fromFile(temp));
        } else if (requestCode == 302 && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null)
                startPhotoZoom(data.getData());
        } else if (requestCode == 303 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            Uri uri = saveBitmap(bitmap);
            KLog.e(uri.getPath());
            if (uri != null) {
                KLog.e("进来了吗？");
                String path = uri.getPath();
                String cosPath = "/" + FileUtils.getFileName(path);
                ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("图片上传中");
                dialog.show();
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                UpLoadImageTecentYun.putObjectForSamllFile(MyApp.bizService, cosPath, path, FileUtils.getFileName(path)
                                        , new UpLoadImageTecentYun.UploadImageUrlInterface() {
                                            @Override
                                            public void getUrl(String url) {
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                }
                                                uploadImage(url);
                                            }
                                        }
                                );
                            }
                        }
                ).start();
            }
        }
    }

    protected void loadData() {
        String cacheOpenId = Config.getCachedWeChatOpenId(getActivity());
        String userId = Config.getCachedUserPwd(getActivity());
        String userPhone = Config.getCachedPhoneNum(getActivity());
        if (AutoLogin.autoLogin()) {
            AutoLogin.Login(new AutoLogin.AutoCallBack() {
                @Override
                public void callBack(String data) {
                    UseParams useParams = new Gson().fromJson(data, UseParams.class);
                    KLog.e("图像图像:" + useParams.getUserHeadshot());
                    Config.cachePayStatus(getActivity(), useParams.getUserPayStats());
                    Config.cacheSessionId(getActivity(), useParams.getSessionId());
                    KLog.e("==============支付密码状态:" + Config.getCachedPayStatus(getContext()));
                    if (!useParams.getUserHeadshot().contains("http://")) {
                        Config.cacheWeChatUserIcon(getActivity(), "http://" + useParams.getUserHeadshot());
                    } else {
                        Config.cacheWeChatUserIcon(getActivity(), useParams.getUserHeadshot());
                    }
                    getUserInfo();//取用户信息
                }
            });
        }
    }

    UseParams useParams = null;

    public void getUserInfo() {
        ApiRetrofit.getInstance().getApiService().getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResponse>() {

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoResponse userInfo) {
                        if (userInfo.getSTATUS().equals("0")) {
                            useParams = new Gson().fromJson(userInfo.getOBJECT().toString(), UseParams.class);
                            KLog.e(useParams.getUserCode() + useParams.getUserId() + useParams.getUserHeadshot());
                            usernameTv.setText(useParams.getUserName());
                            if (userInfo.getOBJECT().getUserHeadshot() == null) {
                                ImageManager.getInstance().loadRoundImage(getActivity(), R.drawable.default_user_icon, userImageIv);
                            } else {
                                if (useParams.getUserHeadshot().startsWith("http://")) {
                                    ImageManager.getInstance().loadRoundImage(getActivity(), useParams.getUserHeadshot(), userImageIv);
                                } else {
                                    ImageManager.getInstance().loadRoundImage(getActivity(), "http://" + useParams.getUserHeadshot(), userImageIv);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 上传用户图像地址到服务器
     *
     * @param url
     */
    private void uploadImage(String url) {
        String imageAdd = url.replace("file", "picsh").replace("http://", "");
        ApiRetrofit.getInstance().getApiService().getChangeUserHeadShot(Config.getCachedUserId(getActivity()), imageAdd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResponse>() {

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoResponse userInfoResponse) {
                        if (userInfoResponse.getSTATUS().equals("0")) {
                            UIUtils.showToast("上传成功");
                            loadData();
                        } else {
                            UIUtils.showToast("数据异常");
                        }
                    }
                });
    }

    private Uri saveBitmap(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory() + "/lushui");
        if (!file.exists())
            file.mkdirs();
        File imgFile = new File(file.getAbsolutePath() + "/" + String.valueOf(System.currentTimeMillis()) + ".jpeg");
        try {
            FileOutputStream outputStream = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
            outputStream.flush();
            outputStream.close();
            Uri uri = Uri.fromFile(imgFile);
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            KLog.e(e.getLocalizedMessage());
        }
        return null;
    }

    // 调用系统相册
    private void toGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 302);
    }

    // 裁剪选中的图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 303);
    }

}
