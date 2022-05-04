package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmh
 * @since 2022-04-25
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUserList();
}
