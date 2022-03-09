package com.hurry.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hurry.servicebase.exceptionhandler.GuliException;
import com.hurry.vod.service.VideoService;
import com.hurry.vod.utils.ConstantVodUtil;
import com.hurry.vod.utils.InitObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

import static com.hurry.vod.utils.InitObject.initVodClient;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hurry
 * @CreateTime: 2022/3/3
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try{
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));;
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除一个阿里云视频
    @Override
    public void removeVideoById(String id) {
        try{
            DefaultAcsClient client = initVodClient(ConstantVodUtil.ACCESS_KEY_ID,ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建删除视频的对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //删除多个阿里云视频
    @Override
    public void removeMoreVideo(List videoIdList) {
        try{
            DefaultAcsClient client = initVodClient(ConstantVodUtil.ACCESS_KEY_ID,ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建删除视频的对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //调用org.apache.commons.lang.StringUtils;对视频id进行遍历拼接(1,2,3)形式
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //根据视频id获取视频播放凭证
    @Override
    public String getPlayAuth(String id) {
        try {
            //通过id获取视频播放凭证
            DefaultAcsClient client = initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        }catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取视频凭证失败");
        }
    }
}
