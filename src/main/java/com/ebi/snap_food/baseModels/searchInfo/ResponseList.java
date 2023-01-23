package com.ebi.snap_food.baseModels.searchInfo;

import java.util.List;

public class ResponseList {
    private Integer totalPage;
    private Long totalCount;
    private  Integer pageNo;
    private List<Object> result;

    public Integer getTotalPage() {
        return totalPage;
    }

    public ResponseList setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public ResponseList setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public ResponseList setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public List<Object> getResult() {
        return result;
    }

    public ResponseList setResult(List<Object> result) {
        this.result = result;
        return this;
    }
}

