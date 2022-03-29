package org.mixed.exam.res.controller;

import org.mixed.exam.res.config.Config;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ResController
{
    @PostMapping(value = "/upload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
    {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
            return "fail";
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(Config.RES_LOCATION + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    @GetMapping(value="/get/{fileName}",produces = {MediaType.ALL_VALUE})
    @ResponseBody
    public byte[] getRes(@PathVariable("fileName")String fileName) throws IOException {
        File file = new File(Config.RES_LOCATION+fileName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}
