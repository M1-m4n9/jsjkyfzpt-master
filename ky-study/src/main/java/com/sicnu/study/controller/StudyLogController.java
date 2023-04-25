package com.sicnu.study.controller;

import com.sicnu.common.utils.PageUtils;
import com.sicnu.common.utils.R;
import com.sicnu.log.dto.OptLogDto;
import com.sicnu.log.service.OptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author LiuChuang
 * @Date 2022/11/13 14:29
 * @PackageName:com.sicnu.study.controller
 * @ClassName: StudyLogController
 * @Description: TODO
 * @Version 1.0
 */

@RestController
@RequestMapping("study/log")
public class StudyLogController
{
    @Autowired
    private OptLogService optLogService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params)
    {
//		List<OptLogDto> list = optLogService.list();
//		return R.ok().put("studyLog", list);

        PageUtils page = optLogService.queryPage(params);
        return R.ok().put("page", page);

    }
}
