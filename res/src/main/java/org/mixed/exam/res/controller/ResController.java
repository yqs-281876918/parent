package org.mixed.exam.res.controller;

import org.mixed.exam.res.config.Config;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ResController
{
    @ResponseBody
    @GetMapping("/upload")
    public String show()
    {
        return "connected";
    }
    @ResponseBody
    @PostMapping("/upload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
    {
        if (file.isEmpty()) {
            System.out.println("文件为空");
            return "文件为空";
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
        return "/res/"+suffixName.substring(1)+"/"+fileName;
    }
    @ResponseBody
    @GetMapping(value="/png/{fileName}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] png(@PathVariable("fileName")String fileName) {
        return getFileBytes(fileName);
    }
    @ResponseBody
    @GetMapping(value="/txt/{fileName}",produces = MediaType.TEXT_PLAIN_VALUE)
    public byte[] text(@PathVariable("fileName")String fileName) {
        return getFileBytes(fileName);
    }
    @ResponseBody
    @GetMapping(value="/jpeg/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] jpg(@PathVariable("fileName")String fileName) {
        return getFileBytes(fileName);
    }
    private byte[] getFileBytes(String fileName)
    {
        try {
            File file = new File(Config.RES_LOCATION+fileName);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
