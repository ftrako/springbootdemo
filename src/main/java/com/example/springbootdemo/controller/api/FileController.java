package com.example.springbootdemo.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 文件相关示例
 * @author ftrako
 * @version 1.0 2022-10-26 15:27
 **/
@RestController
@RequestMapping("/v1/file")
public class FileController {

    private static final Logger logger = LogManager.getLogger(FileController.class);

    /**
     * 上传文件
     *
     * @param httpServletRequest - 默认上下文
     * @param file               - key
     * @return ok
     */
    @PostMapping("/upload")
    public String upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) {
        String saveDir = "E:\\tmp";
        //得到文件名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        //uuid + 文件前缀组成新的文件名
        String newFileName = uuid.toString() + suffixName;
        //根据配置文件中的路径创建文件对象（该对象意义是文件存放的路径）
        File fileDirectory = new File(saveDir);
        //创建目标文件
        File destFile = new File(saveDir + File.separator + newFileName);

        //如果文件目录不存在 创建该文件目录 若创建失败 抛出异常
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                logger.fatal("fail mkdir");
                return "fail mkdir";
            }
        }
        try {
            //把内存中图片写入磁盘
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
        return "ok";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        // 把文件以附件的形式返回

        // 设置响应头
        response.setHeader("Content-Disposition", "attachment; filename=test.txt");

        // 读取文件
        try (InputStream is = new ClassPathResource("static/txt/test.txt").getInputStream()) {
            // 将文件数据利用response写回到客户端
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
