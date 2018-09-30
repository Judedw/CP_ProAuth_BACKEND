package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * SurveySearchResponse
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveySearchResponse {

    private String id;

    private String topic;

    private String type;

    private LocalDate startDate;

    private LocalDate endDate;

    private String productId;

    private String voteId;

    private List<QuestionData> questions;

    @Data
    public static class QuestionData {

        private String name;

        private String id;

    }

}
