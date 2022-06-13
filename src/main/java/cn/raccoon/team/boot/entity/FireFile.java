package cn.raccoon.team.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    private Long fileSize;

    @ApiModelProperty("分享人名称")
    @NotEmpty
    private String username;

    @ApiModelProperty("提取码")
    private String code;
}
