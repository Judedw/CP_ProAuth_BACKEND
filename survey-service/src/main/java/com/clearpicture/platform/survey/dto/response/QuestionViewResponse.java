package com.clearpicture.platform.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author nuwan
 *
 */
@Data
public class QuestionViewResponse {
	
	private String id;
	
	private String name;
	
	private ScaleData scale;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime lastModifiedDate;
	
	private String extId;
	
	@Data
	public static class ScaleData {
		
		private String id;
		
		private String name;
		
	}
	
	private AnswerTemplateData answerTemplate;
	
	@Data
	public static class AnswerTemplateData {
		private String id;
		
		private String name;
		
		private Set<AnswerData> answers;
		
		@Data
		public static class AnswerData {
			
			private String id;
			
			private String lable;

			private BigDecimal value;
			
			private Integer optionNumber;
		}
	}

}
