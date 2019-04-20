package com.lushuitv.yewuds.utils;


import android.util.Log;

import com.socks.library.KLog;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;
import com.tencent.cos.utils.SHA1Utils;

/**
 * Description
 * Created by weip
 * Date on 2017/9/19.
 */

public class UpLoadImageTecentYun {

    public static UploadImageUrlInterface uploadImageUrlInterface;


    public UploadImageUrlInterface getUploadImageUrlInterface() {
        return uploadImageUrlInterface;
    }

    public void setUploadImageUrlInterface(UploadImageUrlInterface uploadImageUrlInterface) {
        this.uploadImageUrlInterface = uploadImageUrlInterface;
    }

    /**
     * 简单文件上传 : <20M的文件，直接上传
     */
    public static void  putObjectForSamllFile(BizService bizService,
                                              String cosPath, String localPath,String imageName,UploadImageUrlInterface uploadImageUrlInterface){
        /** PutObjectRequest 请求对象 */
        PutObjectRequest putObjectRequest = new PutObjectRequest();
        /** 设置Bucket */
        putObjectRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        putObjectRequest.setCosPath(cosPath);
        /** 设置srcPath: 本地文件的路径 */
        putObjectRequest.setSrcPath(localPath);
        /** 设置 insertOnly: 是否上传覆盖同名文件*/
        putObjectRequest.setInsertOnly("1");
        /** 设置sign: 签名，此处使用多次签名 */
        putObjectRequest.setSign(bizService.getSign(imageName));

        /** 设置sha: 是否上传文件时带上sha，一般不需要带*/
        //putObjectRequest.setSha(putObjectRequest.getsha());

        /** 设置listener: 结果回调 */
        putObjectRequest.setListener(new IUploadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, long currentSize, long totalSize) {
                long progress = ((long) ((100.00 * currentSize) / totalSize));
                Log.w("XIAO","progress =" + progress + "%");
                KLog.e("onProgress");
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {
                String result = "上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
                KLog.e("onCancel");
            }

            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                PutObjectResult putObjectResult = (PutObjectResult) cosResult;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" 上传结果： ret=" + putObjectResult.code + "; msg =" +putObjectResult.msg + "\n");
                stringBuilder.append(" access_url= " + putObjectResult.access_url == null ? "null" :putObjectResult.access_url + "\n");
                stringBuilder.append(" resource_path= " + putObjectResult.resource_path == null ? "null" :putObjectResult.resource_path + "\n");
                stringBuilder.append(" url= " + putObjectResult.url == null ? "null" :putObjectResult.url);
                String result = stringBuilder.toString();
                Log.w("XIAO",result);
                KLog.e("onSuccess");
                if (uploadImageUrlInterface != null){
                    uploadImageUrlInterface.getUrl(putObjectResult.access_url);
                }
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = "上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
                KLog.e("上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg);
            }
        });
        /** 发送请求：执行 */
        bizService.cosClient.putObject(putObjectRequest);
    }

    /**
     * 大文件分片上传 : >=20M的文件，需要使用分片上传，否则会出错
     */
    public static void putObjectForLargeFile(BizService bizService,
                                             String cosPath, String localPath,String name){
        /** PutObjectRequest 请求对象 */
        PutObjectRequest putObjectRequest = new PutObjectRequest();
        /** 设置Bucket */
        putObjectRequest.setBucket(bizService.bucket);
        /** 设置cosPath :远程路径*/
        putObjectRequest.setCosPath(cosPath);
        /** 设置srcPath: 本地文件的路径 */
        putObjectRequest.setSrcPath(localPath);
        /** 设置 insertOnly: 是否上传覆盖同名文件*/
        putObjectRequest.setInsertOnly("1");
        /** 设置sign: 签名，此处使用多次签名 */
        putObjectRequest.setSign(bizService.getSign(name));

        /** 设置sliceFlag: 是否开启分片上传 */
        putObjectRequest.setSliceFlag(true);
        /** 设置slice_size: 若使用分片上传，设置分片的大小 */
        putObjectRequest.setSlice_size(1024*1024);

        /** 设置sha: 是否上传文件时带上sha，一般带上sha*/
        putObjectRequest.setSha(SHA1Utils.getFileSha1(localPath));

        /** 设置listener: 结果回调 */
        putObjectRequest.setListener(new IUploadTaskListener() {
            @Override
            public void onProgress(COSRequest cosRequest, long currentSize, long totalSize) {
                long progress = ((long) ((100.00 * currentSize) / totalSize));
                Log.w("XIAO","progress =" + progress + "%");
            }

            @Override
            public void onCancel(COSRequest cosRequest, COSResult cosResult) {
                String result = "上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }

            @Override
            public void onSuccess(COSRequest cosRequest, COSResult cosResult) {
                PutObjectResult putObjectResult = (PutObjectResult) cosResult;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" 上传结果： ret=" + putObjectResult.code + "; msg =" +putObjectResult.msg + "\n");
                stringBuilder.append(" access_url= " + putObjectResult.access_url == null ? "null" :putObjectResult.access_url + "\n");
                stringBuilder.append(" resource_path= " + putObjectResult.resource_path == null ? "null" :putObjectResult.resource_path + "\n");
                stringBuilder.append(" url= " + putObjectResult.url == null ? "null" :putObjectResult.url);
                String result = stringBuilder.toString();
                Log.w("XIAO",result);
            }

            @Override
            public void onFailed(COSRequest cosRequest, COSResult cosResult) {
                String result = "上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg;
                Log.w("XIAO",result);
            }
        });
        /** 发送请求：执行 */
        bizService.cosClient.putObject(putObjectRequest);
    }


    public interface UploadImageUrlInterface{
        void getUrl(String url);
    }
}
