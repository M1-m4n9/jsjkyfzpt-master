package com.sicnu.person.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/12 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueValueAndLabel implements Serializable
{
    private static final long serialVersionUID = 1L;
    Integer value;
    String label;
    List<VueValueAndLabel> children;
}
