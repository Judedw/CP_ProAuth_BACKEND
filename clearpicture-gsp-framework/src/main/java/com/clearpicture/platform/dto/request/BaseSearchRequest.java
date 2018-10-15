package com.clearpicture.platform.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * 
 * @author Virajith
 *
 */
@Data
public abstract class BaseSearchRequest {

	@Min(value = 1, message = "{searchRequest.pagination.pageNumber.invalid}")
	@Max(value = 100000, message = "{searchRequest.pagination.pageNumber.invalid}")
	private Integer pageNumber = 1;

	@Min(value = 10, message = "{searchRequest.pagination.pageSize.invalid}")
	@Max(value = 100, message = "{searchRequest.pagination.pageSize.invalid}")
	private Integer pageSize = 50;

	@Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE,
			message = "{searchRequest.pagination.sortDirection.invalid}")
	private String sortDirection = "desc";

	public abstract String getSortProperty();

}
