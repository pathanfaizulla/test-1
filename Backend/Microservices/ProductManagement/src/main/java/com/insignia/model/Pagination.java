package com.insignia.model;

import lombok.Data;

@Data
public class Pagination {
    private Long limit = 50l;
    private Long offset = 0l;

    public void setOffset(Long offset) {
        if (offset != null && offset != 0)
            this.offset = (offset - 1);
    }
}
