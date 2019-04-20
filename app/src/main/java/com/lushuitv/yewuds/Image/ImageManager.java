package com.lushuitv.yewuds.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.lushuitv.yewuds.R;
import com.lushuitv.yewuds.utils.FileUtils;
import com.lushuitv.yewuds.utils.UIUtils;
import com.socks.library.KLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 图片处理类
 * Created by weip on 2017\10\15 0005.
 */

public class ImageManager {
    private static ImageManager mImageManager = null;

    private ImageManager() {
    }

    public static ImageManager getInstance() {
        if (mImageManager == null) {
            mImageManager = new ImageManager();
        }
        return mImageManager;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param targetImageView 使用默认的占位等待图片和错误图片
     */
    public void loadImage(Context context, Object path, ImageView targetImageView) {
        Glide.with(context)
                .load(path)
                .skipMemoryCache(false)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.notfound)
                .into(targetImageView);
    }

    public void loadImage(Context context, int id, ImageView imageView) {
        Glide.with(context).load(id)
                .skipMemoryCache(false)
                .centerCrop()
                .crossFade(1000)
                .into(imageView);
    }

    public void loadImage(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri)
                .skipMemoryCache(false)
                .centerCrop()
                .crossFade(1000)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param path
     * @param imageView 使用默认的占位等待图片和错误图片
     */
    public void loadRoundImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param id
     * @param imageView
     */
    public void loadRoundImage(Context context, int id, ImageView imageView) {
        Glide.with(context).load(id)
                .bitmapTransform(new CropCircleTransformation(context))
                .crossFade(1000)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param targetImageView 使用默认的占位等待图片和错误图片
     * @param transformation  图片转换器
     */
    public void loadImage(Context context, Object path, ImageView targetImageView, Transformation transformation) {
        Glide.with(context)
                .load(path)
                .bitmapTransform(transformation)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(targetImageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param path
     * @param targetImageView
     * @param placeHolderResourceId 加载中占位图片
     * @param errorResourceId       加载错误占位图片
     */
    public void loadImage(Context context, Object path, ImageView targetImageView, int placeHolderResourceId, int errorResourceId) {
        Glide.with(context)
                .load(path)
                .placeholder(placeHolderResourceId)
                .error(errorResourceId)
                .into(targetImageView);
    }

    /**
     * 获取图片bitmap
     *
     * @param context
     * @param path
     * @return
     */
    public Bitmap loadImage(Context context, Object path) {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(context)
                    .load(path)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }

    public static Bitmap convertStringToIcon(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    //压缩成100k以下上传
    public static String getcomImageBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 100是压缩率不压缩，如果是30就是压缩70%，压缩后的存放在baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        byte[] bytes = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
