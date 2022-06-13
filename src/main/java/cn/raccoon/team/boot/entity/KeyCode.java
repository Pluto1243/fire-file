package cn.raccoon.team.boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("文件链接")
public class KeyCode implements Serializable {

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("提取码")
    private String code;

}
