package com.dynamic.mutidatasource.demo;

import com.dynamic.mutidatasource.DemoApplication;
import com.dynamic.mutidatasource.anno.TargetDataSource;
import com.dynamic.mutidatasource.aspect.DynamicDataSourceAspect;
import com.dynamic.mutidatasource.config.DataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class JpaRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @TargetDataSource(type = DataType.slave1)
    public void test1() {
        Map<String, Object> map = jdbcTemplate.queryForMap("select * from user where id='1'");
        System.out.println(map);
    }
}
