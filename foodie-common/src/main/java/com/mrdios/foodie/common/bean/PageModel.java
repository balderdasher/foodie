package com.mrdios.foodie.common.bean;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页数据封装
 *
 * @Title: PageModel.java
 * @Package com.imooc.utils
 * @Description: 用来返回分页Grid的数据格式
 * Copyright: Copyright (c) 2019
 */
public class PageModel<T> {

    private int page;            // 当前页数
    private int total;           // 总页数
    private long records;        // 总记录数
    private List<T> rows;        // 每行显示的内容

    public static <T> PageModel<T> buildPageModelFromPageInfo(List<T> list, Integer page) {
        PageInfo<T> pageList = new PageInfo<>(list);
        PageModel<T> grid = new PageModel<>();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
