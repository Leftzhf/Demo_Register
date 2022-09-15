package com.registration.response.web;

import io.swagger.annotations.ApiModelProperty;

public class ResponsePageData {

    @ApiModelProperty(value = "数据总数")
    private long totalCount;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
