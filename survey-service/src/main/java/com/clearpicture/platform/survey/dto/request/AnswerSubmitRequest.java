package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.survey.entity.Answer;
import com.clearpicture.platform.survey.entity.Question;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * AnswerSubmitRequest
 * Created by nuwan on 9/7/18.
 */
@Data
public class AnswerSubmitRequest {

    private String surveyId;

    private List<QuestionData> questions;

    @Data
    public static class QuestionData implements ModelMappingAware {

        private String id;

        @NotEmpty(message = "platformAnswerSubmitRequest.answer.empty")
        private @Valid List<AnswerData> answers;

        @Data
        public static class AnswerData implements ModelMappingAware {

            private String id;

            private String freeText;

            /* (non-Javadoc)
             * @see com.sodexo.begreat.platform.modelmapper.ModelMappingAware#getDestinationType()
             */
            @Override
            public Class<?> getDestinationType() {
                return Answer.class;
            }

        }

        /* (non-Javadoc)
         * @see com.sodexo.begreat.platform.modelmapper.ModelMappingAware#getDestinationType()
         */
        @Override
        public Class<?> getDestinationType() {
            return Question.class;
        }

    }


}
