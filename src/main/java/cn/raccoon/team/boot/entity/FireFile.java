package cn.raccoon.team.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 阅后即焚的文件信息
 *
 * @author wangjie
 * @date 18:02 2022年06月06日
 **/
@ApiModel("阅后即焚的文件信息")
@Data
public class FireFile implements Serializable {

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("展示名")
    @NotEmpty
    private String showName;

    @ApiModelProperty("文件路径")
    @NotEmpty
    private String path;

    @ApiModelProperty("文件名")
    @NotEmpty
    private String fileName;

    @ApiModelProperty("过期时间级别")
    private Integer expireLevel;

    @ApiModelProperty("是否过期")
    private Boolean expire;

    @ApiModelProperty("文件大小")
    private String fileSize;

    @ApiModelProperty("分享人名称")
    @NotEmpty
    private String username;

    @ApiModelProperty("提取码")
    private String code;

    @ApiModelProperty("过期时间")
    private Date expireDate;

    // TODO 可以加一个下载次数 每次下载 -1
}
