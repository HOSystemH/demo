package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TearchQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public R findAllTearcher() {
        //调用service 的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacher.list(null);
        return R.ok().data("items", list);
    }

    //删除逻辑讲师的方法
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = eduTeacher.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    //current 当前页
    //limit 每页记录数
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法
        //调用方法时,底层自动处理  把分页所有数据封装到pageTeacher对象里面
        eduTeacher.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        //方式一
        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);

        //方式二:
        //        return R.ok().data("total",total).data("rows",records);

    }

    //带条件查询分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TearchQuery tearchQuery) {

        //创建Page对象
        Page<EduTeacher> page = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //mybatis dynamic sql
        //判断条件是否为空  为空不拼接条件
        String name = tearchQuery.getName();
        Integer level = tearchQuery.getLevel();
        String begin = tearchQuery.getBegin();
        String end = tearchQuery.getEnd();
        //判断条件是否为空 不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            queryWrapper.likeRight("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        //调用方法实现 条i教案查询分页
        eduTeacher.page(page, queryWrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        Map map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    //添加讲师接口方法
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        eduTeacher.setGmtCreate(new Date());
        eduTeacher.setGmtModified(new Date());
        boolean save = this.eduTeacher.save(eduTeacher);

        return save == true ? R.ok() : R.error();
    }

    //根据id查询数据
    @GetMapping("/getTearcher/{id}")
    public R getTearcher(@PathVariable String id) {
        EduTeacher byId = eduTeacher.getById(id);
        
//        try {
//            int i = 1 / 0;
//        } catch (Exception e) {
//            //执行自定义异常
//            throw new GuliException(20001,"自定义异常");
//        }
        return R.ok().data("items", byId);
    }

    //修改功能
    @PostMapping("/updateTearcher")
    public R updateTearcher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = this.eduTeacher.updateById(eduTeacher);
        return flag == true ? R.ok() : R.error();
    }
}

