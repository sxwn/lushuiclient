package com.lushuitv.yewuds.actor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.lushuitv.yewuds.Image.ImageManager;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.api.ApiRetrofit;
import com.lushuitv.yewuds.base.BaseActivity;
import com.lushuitv.yewuds.constant.Config;
import com.lushuitv.yewuds.luban.Luban;
import com.lushuitv.yewuds.luban.OnCompressListener;
import com.lushuitv.yewuds.module.entity.HttpBean;
import com.lushuitv.yewuds.module.response.UserInfoResponse;
import com.lushuitv.yewuds.utils.SystemUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 发布页面
 * Created by weip on 2017\12\15 0015.
 */

public class ActorPublishActivity extends BaseActivity implements View.OnClickListener {

    View mView;
    TextView cacel, commit, publishWordCount;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private ActorSmallImageAdapter mAdapter;
    private EditText publishContent;

    @Override
    protected View initContentView() {
        mView = LayoutInflater.from(this).inflate(R.layout.actor_publish, null);
        return mView;
    }

    @Override
    public void initToolbar() {
        appBarLayout.setVisibility(View.GONE);
    }


    private ArrayList<String> mImageList = new ArrayList<String>();//适配器图片<压缩后的>
    private ArrayList<String> photos;
    private int actorId;

    @Override
    protected void initOptions() {
        actorId = getIntent().getIntExtra("actorId", 0);
        cacel = (TextView) mView.findViewById(R.id.actor_publish_cacel);
        cacel.setOnClickListener(this);
        commit = (TextView) mView.findViewById(R.id.actor_publish_commit);
        commit.setOnClickListener(this);
        publishContent = (EditText) mView.findViewById(R.id.actor_publish_et);
        publishWordCount = (TextView) mView.findViewById(R.id.actor_publish_count);
        publishContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                publishWordCount.setText(s.toString().length() + "/140");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() >= 140) {
                    UIUtils.showToast("输入内容不能超过140字...");
                }
            }
        });
        GridView gridView = (GridView) findViewById(R.id.actor_publish_gv);
        mAdapter = new ActorSmallImageAdapter(this, mImageList);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Matisse.from(ActorPublishActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .capture(true)
                        .captureStrategy(
                                new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                        .maxSelectable(9)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(
                                getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actor_publish_cacel:
                finish();
                break;
            case R.id.actor_publish_commit:
                UIUtils.showToast("艺人id:"+actorId);
                if (mImageList.size() > 0) {
                    Map<String, RequestBody> photos = new HashMap<>();
                    for (int i = 0; i < mImageList.size(); i++) {
                        photos.put("img\"; filename=\"" + i + "icon.jpeg",
                                RequestBody.create(MediaType.parse("multipart/form-data"), new File(mImageList.get(i))));
                    }
                    ApiRetrofit.getInstance().getApiService().getActorImageUpload(Config.getCachedUserId(this),
                            SystemUtils.getVersion(this), actorId, photos)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<HttpBean>() {

                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(HttpBean httpBean) {
                                    if ("0".equals(httpBean.getSTATUS())){
                                        finish();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                List<Uri> uris = Matisse.obtainResult(data);
                photos = new ArrayList<>();//原图
                if (mImageList.size() > 0)
                    mImageList.clear();
                for (int i = 0; i < uris.size(); i++) {
                    String realPathFromUri = getRealPathFromUri(ActorPublishActivity.this, uris.get(i));
                    photos.add(realPathFromUri);
                }
                compressWithLs(photos);
            }
        }
    }

    private void compressWithRx(final List<String> photos) {
        Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(List<String> list) throws Exception {
                        return Luban.with(ActorPublishActivity.this).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(List<File> list) throws Exception {
                        for (File file : list) {
                            showResult(photos, file);
                        }
                    }
                });
    }

    /**
     * 压缩图片 Listener 方式
     */
    private void compressWithLs(final List<String> photos) {
        Luban.with(this)
                .load(photos)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        showResult(photos, file);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    private void showResult(List<String> photos, File file) {
        int[] thumbSize = computeSize(file.getAbsolutePath());
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], file.length() >> 10);
        mImageList.add(file.getAbsolutePath());
        mAdapter.notifyDataSetChanged();
    }

    private int[] computeSize(String srcImg) {
        int[] size = new int[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(srcImg, options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;
        return size;
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private static class ActorSmallImageAdapter extends BaseAdapter {
        private Context context;
        private List<String> mImages;
        private int maxImages = 9;//最多上传9张

        public ActorSmallImageAdapter(Context context, List<String> mImages) {
            this.context = context;
            this.mImages = mImages;
        }

        private void setData(List<String> uris) {
            mImages = uris;
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            int count = mImages == null ? 1 : mImages.size() + 1;
            if (count >= maxImages) {
                return mImages.size();
            } else {
                return count;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UriViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false);
                viewHolder = new UriViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (UriViewHolder) convertView.getTag();
            }
            /**代表+号之前的需要正常显示图片**/
            if (position < mImages.size()) {
//                String imageUrl = mImages.get(position);
//                File file = new File(imageUrl);
                Glide.with(context)
                        .load(mImages.get(position))
                        .into(viewHolder.mImage);
                viewHolder.btnDelete.setVisibility(View.VISIBLE);
                viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mImages.get(position) != null) {
                            mImages.remove(position);
                        }
                        notifyDataSetChanged();
                    }
                });
            } else {
                /**代表+号的需要+号图片显示图片**/
                ImageManager.getInstance().loadImage(context, R.drawable.actor_publish_addicon, viewHolder.mImage);
                viewHolder.mImage.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder.btnDelete.setVisibility(View.GONE);
            }
            return convertView;
        }

        static class UriViewHolder {
            private ImageView mImage;
            private Button btnDelete;

            UriViewHolder(View contentView) {
                mImage = (ImageView) contentView.findViewById(R.id.uri_item_image);
                btnDelete = (Button) contentView.findViewById(R.id.uri_item_del);
            }
        }
    }
}
