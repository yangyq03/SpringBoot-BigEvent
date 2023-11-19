package com.example.bigevent.service.impl;

import com.example.bigevent.Utils.ThreadLocalUtil;
import com.example.bigevent.mapper.ArticleMapper;
import com.example.bigevent.pojo.Article;
import com.example.bigevent.pojo.PageBean;
import com.example.bigevent.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public void add(Article article) {
        //补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        //通过ThreadLocal获取创建者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询    PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper
        //通过ThreadLocal获取创建者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        //将List<Article>类型强转成Page<Article>类型 → Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页的数据
        Page<Article> p = (Page<Article>) as;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
