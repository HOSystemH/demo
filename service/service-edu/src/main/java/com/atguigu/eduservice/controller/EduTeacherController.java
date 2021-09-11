package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hosystem
 * @since 2021-09-11
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //注入Service
    @Autowired
    private EduTeacherService eduTeacher;

    // 测试环境搭建是否成功
    //rest风格
    @GetMapping("/hello")
    public List<EduTeacher> findAllTearcher(){
        //调用service 的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacher.list(null);
        return list;
    }

    //删除逻辑讲师的方法
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id){
        boolean flag = eduTeacher.removeById(id);
        return flag;
    }

}

