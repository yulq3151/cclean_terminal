package com.cclean.terminal.entity;

import com.cclean.terminal.vo.PageVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author yulq
 * @create 2018-04-12 10:35
 * @desc
 **/
public class PageMo<E> implements Serializable {
    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 总数
     */
    private int total;
    /**
     * 总页数
     */
    private int pages;

    /**
     * 查询结果集
     */
    private List<E> list;

    public PageMo() {
    }

    public PageMo(List<E> list, PageVO pageVO, int total) {
        Integer size = pageVO.getPageSize();
        this.total = total;
        this.pageSize = list.size();
        this.list = list;
        this.pageNum = pageVO.getPageNum();
        if (total >= size) {
            if (total % size > 0) {
                this.pages = total / size + 1;
            } else {
                this.pages = total / size;
            }
        } else {
            this.pages = 1;
        }


    }

    public PageMo(List<E> list, int pageNum, int pageSize, int total) {
        Integer size = pageSize;
        this.total = total;
        this.pageSize = list.size();
        this.list = list;
        this.pageNum = pageNum;
        if (total >= size) {
            if (total % size > 0) {
                this.pages = total / size + 1;
            } else {
                this.pages = total / size;
            }
        } else {
            this.pages = 1;
        }


    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
