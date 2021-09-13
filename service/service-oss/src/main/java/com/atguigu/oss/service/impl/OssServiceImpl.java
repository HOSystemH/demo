package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String uploadUrl = null;

        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String filename = file.getOriginalFilename();

            //添加随机名称
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            //按照文件日期进行分类
            Date date = new Date();
            String strDateFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            String str = sdf.format(date).toString();

            filename = str+"/"+filename;

            //调用oss方法实现上传
            //arg1: Bucket名称
            //arg2: 上传到oss文件路径和文件名称
            //arg3：上传文件输入流
            ossClient.putObject(bucketName, filename, inputStream);

            //关闭oss
            ossClient.shutdown();

            //返回上传路径
            //手动拼接
            //https://demo-vue.oss-cn-guangzhou.aliyuncs.com/7115e00e9565693ef1bfea48827d8f5f_t.gif
            String url = "https://" + bucketName + "." + endPoint + "/" + filename;
            return url;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
