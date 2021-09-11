package com.hipepham.springboot.file.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The Class FileStorageProperties.
 *
 * @author CuongNV34
 * @date Jul 09, 2019
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

	/** The upload location. */
	private String uploadLocation;

	/**
	 * Gets the upload location.
	 *
	 * @return the upload location
	 */
	public String getUploadLocation() {
		return uploadLocation;
	}

	/**
	 * Sets the upload location.
	 *
	 * @param uploadLocation the new upload location
	 */
	public void setUploadLocation(String uploadLocation) {
		this.uploadLocation = uploadLocation;
	}
}
