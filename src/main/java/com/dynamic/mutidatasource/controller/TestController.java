package com.dynamic.mutidatasource.controller;

import com.dynamic.mutidatasource.anno.TargetDataSource;
import com.dynamic.mutidatasource.config.DataType;
import com.dynamic.mutidatasource.jpa.entity.User;
import com.dynamic.mutidatasource.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/web/test")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository repository;

    @GetMapping("get/{uuid}")
    @TargetDataSource(type = DataType.main)
    public Map<String, Object> get(@PathVariable(name = "uuid") String id) {
        String sql = "Select * from user where id ='" + id + "'";
        return jdbcTemplate.queryForMap(sql);
    }

    @GetMapping("jpa/get/{uuid}")
    @TargetDataSource(type = DataType.slave1)
    public User get(@PathVariable(name = "uuid") Integer id) {
        return repository.findById(id).get();
    }

    @PostMapping("save/{uuid}")
    @TargetDataSource(type = DataType.slave1)
    @Transactional(rollbackFor = Exception.class)
    public User save(@PathVariable(name = "uuid") Integer id) throws Exception {
        User user = repository.saveAndFlush(new User(id, "name", "2122", 12));
        if (user != null) {
            throw new Exception("aaaa");
        }
        return user;
    }
}
