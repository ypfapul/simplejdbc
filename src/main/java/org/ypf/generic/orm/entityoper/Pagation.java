package org.ypf.generic.orm.entityoper;

/**
 * @date: 2022/6/20 11:32
 */
public class Pagation {
    private int page;
    private int pageSize;
    private int total;

    public Pagation(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    /**
     * 行开始索引
     *
     * @return
     */
    public int getRowBeginIndex() {
        return (page - 1) * pageSize;
    }

    /**
     * 行结束索引
     *
     * @return
     */
    public int getEndIndex() {
        return getRowBeginIndex() + pageSize - 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
}
