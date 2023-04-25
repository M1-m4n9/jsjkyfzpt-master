package com.sicnu.policy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sicnu.common.utils.PageUtils;
import com.sicnu.policy.entity.PolicyEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-14 12:34:12
 */
public interface PolicyService extends IService<PolicyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PolicyEntity getByPolicyById(Integer id);

    void redisCache();

    void savePolicy(PolicyEntity policy);

    void updateByPolicyById(PolicyEntity policy);

    void removeByPolicyByIds(List<Long> asList);

    List<PolicyEntity> getTop10();

    void getCur2MySQL();

    List<Integer> listIdsAndNotIn(List<Integer> ids);
}

