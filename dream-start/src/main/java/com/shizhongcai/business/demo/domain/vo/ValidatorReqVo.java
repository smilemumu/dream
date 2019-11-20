package com.shizhongcai.business.demo.domain.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * 参数验证请求参数
 * @Author shizhongcai
 * @Date 2019/11/20 15:35
 */
@Data
public class ValidatorReqVo{

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull
    @Future(message = "格式为yyyy-MM-dd,且为将来时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date future;

    @NotNull
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "1000")
    private Double doubleValue;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Integer intValue;

    @NotNull
    @Range(min = 1,max = 10,message = "最小为1，最大为10")
    private Integer range;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10,max = 20,message = "字符串长度在10-20之间")
    private String strSize;

}
