package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public R findAllTearcher(){
        //调用service 的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacher.list(null);
        return R.ok().data("items",list);
    }

    //删除逻辑讲师的方法
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean flag = eduTeacher.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询
    //current 当前页
    //limit 每页记录数
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法
        //调用方法时,底层自动处理  把分页所有数据封装到pageTeacher对象里面
        eduTeacher.page(pageTeacher,null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        //方式一
        Map map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);

        //方式二:
        //        return R.ok().data("total",total).data("rows",records);

    }
}

