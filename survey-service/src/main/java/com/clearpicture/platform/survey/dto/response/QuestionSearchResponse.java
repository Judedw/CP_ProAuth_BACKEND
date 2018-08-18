package com.clearpicture.platform.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author nuwan
 *
 */
@Data
public class QuestionSearchResponse {

	private String id;
	
	private String name;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime lastModifiedDate;
	
	private AnswerTemplateData answerTemplate;
	
	@Data
	public static class AnswerTemplateData {
		
		private String id;
		
		private String name;

	
	}
	
}
