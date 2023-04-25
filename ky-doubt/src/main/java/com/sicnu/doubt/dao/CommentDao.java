package com.sicnu.doubt.dao;

import com.sicnu.doubt.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

/**
 * 
 * 
 * @author ?????
 * @email 2565477149@qq.com
 * @date 2022-09-21 21:47:00
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
    /**
     * 根据uid查询评论
     * @param uid
     * @return
     */
    @Select("select *from ky_doubt.d_comment where uid = #{uid}")
    List<CommentEntity> selectByUidCommentEntities(Integer uid);

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @SelectKey(statement = "select LAST_INSERT_ID() as id", keyProperty = "id", before = false, resultType = Integer.class)
    @Insert("INSERT INTO d_comment(uid,entity_type,entity_id,target_id,content,create_time,top_time) values (#{uid},#{entityType},#{entityId},#{targetId},#{content},#{createTime},#{topTime})")
    int insertMy( CommentEntity comment);
}
