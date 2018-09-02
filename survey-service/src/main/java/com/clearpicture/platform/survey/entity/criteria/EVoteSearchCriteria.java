package com.clearpicture.platform.survey.entity.criteria;

import com.clearpicture.platform.survey.entity.EVote;
import lombok.Data;

/**
 * EVoteSearchCriteria
 * Created by nuwan on 8/23/18.
 */
@Data
public class EVoteSearchCriteria extends EVote {

    private String topic;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}
