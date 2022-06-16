package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.exception.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(tags = "文件")
@RequestMapping("/file")
public class FileController {

    @Value("${in-path}")
    private String PATH;

    /**
     * @description 上传文件
     *
     * @date 23:28 2022年05月04日
     * @param file
     * @return cn.raccoon.team.boot.exception.response.R
     */
    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件")
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        // 获取文件名称
        String originalFilename = file.getOriginalFilename();

        try {
            // 校验文件的内容，读取文件内容
            BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
            if (bis == null) {
                throw new CommonException(EmError.FILE_EXIST);
            }
            //获取文件后缀名
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //重新随机生成名字
            String filename = UUID.randomUUID() +suffixName;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
            // 日期作为上传文件夹
            String date = simpleDateFormat.format(new Date());
            File realFile = null;
            String uploadPath = null;
            // 保存到服务器
            uploadPath = PATH + "/"+ date + "/" + filename;
            realFile = new File(uploadPath);
            // 检查文件夹是否存在
            if (!realFile.getParentFile().exists()) {
                realFile.setWritable(true, false);
                realFile.getParentFile().mkdirs();
                file.transferTo(realFile);
            } else {
                file.transferTo(realFile);
            }
            return R.ok(uploadPath);

        } catch (IOException e) {
            throw new CommonException(EmError.UNKNOWN_ERROR);
        }
    }
}
