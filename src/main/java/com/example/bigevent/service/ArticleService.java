package com.example.bigevent.service;

import com.example.bigevent.pojo.Article;
import com.example.bigevent.pojo.PageBean;

public interface ArticleService {

    //新增文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);
}
