package com.sicnu.doubt.vo;

import com.sicnu.doubt.entity.PostEntity;
import com.sicnu.doubt.feign.entity.UserCenterVo;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Data
public class PostAllVo extends PostEntity {
    private String Typename;
    private String Typeintroduction;
    private UserCenterVo User;
    private List<String> Tages;
    private Integer IsFollow=0;
}