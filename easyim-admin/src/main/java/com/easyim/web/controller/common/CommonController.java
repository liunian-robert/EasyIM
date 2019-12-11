package com.easyim.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyim.common.config.FastDFSConfig;
import com.easyim.common.fastdfs.client.FastDFSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.easyim.common.config.Global;
import com.easyim.common.config.ServerConfig;
import com.easyim.common.constant.Constants;
import com.easyim.common.core.domain.AjaxResult;
import com.easyim.common.utils.StringUtils;
import com.easyim.common.utils.file.FileUploadUtils;
import com.easyim.common.utils.file.FileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用请求处理
 * 
 * @author emessage
 */
@Controller
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private FastDFSConfig fastDFSConfig;

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = Global.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            FastDFSClient client = new FastDFSClient();
            Map<String,String> mps = new HashMap<String,String>();
            mps.put("fileName",file.getOriginalFilename());
            String suffix = FileUploadUtils.getExtension(file);
            mps.put("fileExtName",suffix);
            mps.put("contentType",file.getContentType());
            mps.put("fileLength",file.getSize()+"");
            String filename = client.upload(file.getInputStream(),StringUtils.getUUID(Boolean.TRUE)+"."+suffix,mps);
            String url = fastDFSConfig.getDownload_url() + filename;
            Map<String,Object> result = new HashMap<String,Object>();
            result.put("fileId",filename);
            result.put("name", filename.substring(filename.lastIndexOf("/")+1,filename.length()));
            result.put("src", url);
            return AjaxResult.success("success",result);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        // 本地资源路径
        String localPath = Global.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }
}
