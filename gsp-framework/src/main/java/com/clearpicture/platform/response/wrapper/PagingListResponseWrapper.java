package com.clearpicture.platform.response.wrapper;

import com.clearpicture.platform.enums.RestApiResponseStatus;
import lombok.Data;

import java.util.List;

/**
 * PagingListResponseWrapper
 * Created by nuwan on 7/12/18.
 */
public class PagingListResponseWrapper<T> extends BaseResponseWrapper {

    private List<T> content;

    private Pagination pagination;

    public PagingListResponseWrapper(List<T> content,Pagination pagination) {
        super(RestApiResponseStatus.OK);
        this.content = content;
        this.pagination = pagination;

    }

    @Data
    public static class Pagination {

        public Pagination(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalRecords) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalPages = totalPages;
            this.totalRecords = totalRecords;
        }

        private Integer pageNumber;

        private Integer pageSize;

        private Integer totalPages;

        private Long totalRecords;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
