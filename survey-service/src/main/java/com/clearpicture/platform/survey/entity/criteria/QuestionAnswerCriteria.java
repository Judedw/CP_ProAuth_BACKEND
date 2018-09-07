package com.clearpicture.platform.survey.entity.criteria;

import com.clearpicture.platform.survey.entity.Question;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * QuestionAnswerCriteria
 * Created by nuwan on 9/7/18.
 */
@Data
public class QuestionAnswerCriteria extends Question {


        private Long id;

        @NotEmpty(message = "platformAnswerSubmitRequest.answer.empty")
        private @Valid List<AnswerData> answers;

        @Data
        public static class AnswerData  {

            private Long id;

            private String freeText;


        }




}
