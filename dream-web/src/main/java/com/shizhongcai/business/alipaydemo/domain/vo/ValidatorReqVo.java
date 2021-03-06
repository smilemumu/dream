package com.shizhongcai.business.alipaydemo.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shizhongcai.common.serializer.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate localDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime localDateTime;


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

    @NotBlank(message = "email不能为空")
    @Email
    private String email;

    @NotBlank(message = "字符串不能为空")
    @Size(min = 10,max = 20,message = "字符串长度在10-20之间")
    private String strSize;

}
