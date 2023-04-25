package com.sicnu.person.vo;

import com.sicnu.person.entity.PowerEntity;
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
 * @since 2022/10/28 22:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    // 当前菜单
    private PowerEntity powerEntity;
    // 当前菜单的子菜单
    private List<MenuResponseVo> sonList;
}
