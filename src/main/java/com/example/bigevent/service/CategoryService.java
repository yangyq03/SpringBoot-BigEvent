package com.example.bigevent.service;

import com.example.bigevent.pojo.Category;

import java.util.List;

public interface CategoryService {
    //添加文章分类
    void add(Category category);

    //文章分类列表
    List<Category> list();

    //文章分类详情
    Category findById(Integer id);

    //更新文章分类
    void update(Category category);

    //删除文章分类
    void delete(String id);
}
