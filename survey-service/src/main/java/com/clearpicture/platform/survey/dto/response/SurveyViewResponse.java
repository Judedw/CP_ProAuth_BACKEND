package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.util.List;

/**
 * SurveyViewResponse
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveyViewResponse {

    private String id;

    private String topic;

    private String type;

    private String startDate;

    private String endDate;

    private List<QuestionData> questions;

    @Data
    public static class QuestionData {

        private String name;

        private String id;

        private AnswerTemplateData answerTemplate;

        @Data
        public static class AnswerTemplateData  {

            private String name;

            private String id;

        }

    }

}
