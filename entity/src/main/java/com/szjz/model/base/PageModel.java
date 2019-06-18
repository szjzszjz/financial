package com.szjz.model.base;

import lombok.Data;

import java.util.List;

/**
 * author:szjz
 * date:2019/6/13
 * <p>
 * 分页模型
 */

@Data
public class PageModel<T> {

    /**
     * 总个数
     */
    private Long totalElements;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 每页容量
     */
    private Integer pageSize;

    /**
     * 每页内容
     */
    private List<T> pageContent;

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<T> pageContent) {
        this.pageContent = pageContent;
    }
}
