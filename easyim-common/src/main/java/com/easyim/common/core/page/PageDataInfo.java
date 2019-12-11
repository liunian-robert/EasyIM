package com.easyim.common.core.page;

import java.util.List;

public class PageDataInfo {
    private Boolean firstPage;
    private Boolean lastPage;
    /** 列表数据 */
    private List<?> list;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private long totalRow;

    public Boolean getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageDataInfo{");
        sb.append("firstPage=").append(firstPage);
        sb.append(", lastPage=").append(lastPage);
        sb.append(", list=").append(list);
        sb.append(", pageNumber=").append(pageNumber);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", totalPage=").append(totalPage);
        sb.append(", totalRow=").append(totalRow);
        sb.append('}');
        return sb.toString();
    }
}
