package com.insignia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadRequest {

	private String applicationName;
	private String tenantName;
	private String functionalObject;
	private String functionalObjectId;
	private String fileType;
	private Long customerSequenceNumber;
	private Integer expirationDuration;

}
