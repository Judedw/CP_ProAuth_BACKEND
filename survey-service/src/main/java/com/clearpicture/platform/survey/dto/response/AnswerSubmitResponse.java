package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.util.List;

/**
 * AnswerSubmitResponse
 * Created by nuwan on 9/7/18.
 */
@Data
public class AnswerSubmitResponse {

    private List<AlreadyAnswerQuestion> alreadyAnswerQuestion;

    private List<NewlyAnswerQuestion> newlyAnswerQuestion;

    @Data
    public static class AlreadyAnswerQuestion {

        private String id;
    }

    @Data
    public static class NewlyAnswerQuestion {

        private String id;
    }
}
