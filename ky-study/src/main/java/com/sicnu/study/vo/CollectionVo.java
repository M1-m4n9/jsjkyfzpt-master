package com.sicnu.study.vo;

import com.sicnu.study.entity.CourseCollectionEntity;
import com.sicnu.study.entity.SourceEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author LiuChuang
 * @Date 2022/12/13 12:03
 * @PackageName:com.sicnu.study.vo
 * @ClassName: collectionVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionVo extends CourseCollectionEntity
{
	private List<SourceEntity> sourceList;
}
