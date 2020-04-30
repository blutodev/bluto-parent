package com.bluto.fc.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 *
 * @author 研发
 * @date 2018/5/30
 */
@Data
@ToString
public class BaseCommonParam implements Serializable{

    @ApiModelProperty(value = "当前页码")
    @Min(value = 1,message = "当前页码不合法")
    private Integer pageNum;

    @ApiModelProperty(value = "每页显示数量")
    @Min(value = 1,message = "每页展示数量不合法")
    private Integer pageSize;

    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty("排序")
    private String sortBy;

    @ApiModelProperty("模糊搜索关键字")
    private String keyWord;
}
