package com.easyim.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.easyim.common.config.MessageConfig;
import com.easyim.system.domain.MessageResult;
import com.easyim.system.service.ISysMessageService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISysMessageServiceImpl implements ISysMessageService {
    @Autowired
    private MessageConfig messageConfig;
    @Override
    public void sendMessage(String channelId,String message) throws Exception {
        String pubServer = messageConfig.getPublish_server();
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(pubServer+channelId);
        postMethod.setRequestHeader("Accept","text/json");
        postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        RequestEntity requestEntity = new ByteArrayRequestEntity(message.getBytes("UTF-8"));
        postMethod.setRequestEntity(requestEntity);
        client.executeMethod(postMethod);
        String response = postMethod.getResponseBodyAsString();
        MessageResult result = JSON.parseObject(response,MessageResult.class);
        System.out.println(result.toString());
    }
}
