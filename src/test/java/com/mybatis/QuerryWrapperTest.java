package com.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

@MapperScan(basePackages = "com.mapper")
@SpringBootTest
@Slf4j
public class QuerryWrapperTest {
    @Test
    void testNested() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .and(i -> i.eq("id", 1).nested(j -> j.ne("id", 2)))
                .or(i -> i.eq("id", 1).and(j -> j.ne("id", 2)))
                .nested(i -> i.eq("id", 1).or(j -> j.ne("id", 2)));
        log.info("测试 Nested 下的方法", queryWrapper);
    }
}
