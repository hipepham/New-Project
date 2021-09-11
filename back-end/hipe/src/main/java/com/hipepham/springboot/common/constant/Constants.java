/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.hipepham.springboot.common.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Sku portal constants.
 */
public class Constants {

	/**
	 * The constant DEFAULT_REQUEST_PATH.
	 */
	public static final String DEFAULT_REQUEST_PATH = "/api/v1";

	/**
	 * The constant ERROR.
	 */
	public static final String ERROR = "error";

	/**
	 * The constant SUCCESS.
	 */
	public static final String SUCCESS = "success";

	/**
	 * The constant DEFAULT_PAGE_SIZE.
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/**
	 * The constant DEFAULT_PAGE_NO.
	 */
	public static final int DEFAULT_PAGE_NO = 1;

	/**
	 * The constant DEFAULT_PAGE_NO.
	 */
	public static final long ZERO = 0;;

	/**
	 * The constant DEFAULT_DATE_FORMAT.
	 */
	public static final String DEFAULT_DATE_FORMAT
			= "dd/MM/yyyy HH:mm:ss";
	
	/**
	 * System.
	 */
	public static final String SYSTEM = "system";

	/**
	 * Hide constructor.
	 */
	private Constants() {
		// Default.
	}

	/**
	 * The type Field es.
	 */
	public static class FieldES {
		/**
		 * The constant TXT_DOC.
		 */
		public static final String TXT_DOC = "{\"doc\":";
		/**
		 * The constant UID.
		 */
		public static final String UID = "_id";
		/**
		 * The constant NULL.
		 */
		public static final String NULL = "null";
		/**
		 * The constant TOR_REC.
		 */
		public static final String TOR_REC = "3";
		/**
		 * The constant TOR_JIRA.
		 */
		public static final String TOR_JIRA = "2";
		/**
		 * The constant TOR_SEARCH.
		 */
		public static final String TOR_SEARCH = "1";
		/**
		 * The constant ERROR.
		 */
		public static final String ERROR = "error";
		/**
		 * The constant DATA_EMPTY.
		 */
		public static final String DATA_EMPTY = "";
		/**
		 * The constant ANALYZE.
		 */
		public static final String ANALYZE = "/_analyze";
		/**
		 * The constant REPLACE_GRADLE_DISTRIBUTION_URL.
		 */
		public static final String REPLACE_GRADLE_DISTRIBUTION_URL = "distributionUrl=https\\://hn-repo.fsoft.com.vn/repository/maven-gradle/";
		/**
		 * The constant SOURCE.
		 */
		public static final String SOURCE = "_source";
		/**
		 * The constant HIGHLIGHT.
		 */
		public static final String HIGHLIGHT = "highlight"; //highlight on elasticsearch
		/**
		 * The constant DATA_NULL.
		 */
		public static final String DATA_NULL = "-";
		/**
		 * The constant ZERO.
		 */
		public static final String ZERO = "0";
		/**
		 * The constant READ_TIME_OUT.
		 */
		public static final int READ_TIME_OUT = 0;
		/**
		 * The constant CONECTION_TIME_OUT.
		 */
		public static final int CONECTION_TIME_OUT = 0;
		/**
		 * The constant ELASTICURL.
		 */
		public static final String ELASTICURL ="http://elasticsearch-sku:8088/sku_portal";
		/**
		 * The constant SORT_DESC.
		 */
		public static final String SORT_DESC = "1";
		/**
		 * The constant SORT_ASC.
		 */
		public static final String SORT_ASC = "2";
		/**
		 * The constant SORT_MV.
		 */
		public static final String SORT_MV = "3";
		/**
		 * The constant SPACE.
		 */
		public static final String SPACE = " ";
		/**
		 * The constant BASE.
		 */
		public static final List<String> BASE = Collections.unmodifiableList(Arrays.asList("26", "4553"));
		/**
		 * The constant HITS.
		 */
		public static final String HITS = "hits";
		/**
		 * The constant CLOSE_BRACKETS.
		 */
		public static final String CLOSE_BRACKETS = "]}}}}";
		/**
		 * The constant INTRODUCTION.
		 */
		public static final String INTRODUCTION = "introduction";
		/**
		 * The constant INTRODUCTION_JP.
		 */
		public static final String INTRODUCTION_JP = "introduction_jp";
		/**
		 * The constant INTRODUCTION_KR.
		 */
		public static final String INTRODUCTION_KR = "introduction_kr";
		/**
		 * The constant INTRODUCTION_GE.
		 */
		public static final String INTRODUCTION_GE = "introduction_ge";
		/**
		 * The constant INTRODUCTION_FR.
		 */
		public static final String INTRODUCTION_FR = "introduction_fr";
		/**
		 * The constant INTRODUCTION_EG.
		 */
		public static final String INTRODUCTION_EG = "introduction_eg";
		/**
		 * The constant INTRODUCTION_CN.
		 */
		public static final String INTRODUCTION_CN = "introduction_cn";
		/**
		 * The constant SUMMARY.
		 */
		public static final String SUMMARY = "summary";
		/**
		 * The constant LINK_JOB_JENKIN.
		 */
		public static final String LINK_JOB_JENKIN = "link_job_jenkins";
		/**
		 * The constant TITLE.
		 */
		public static final String TITLE = "title";
		/**
		 * The constant TITLE_JP.
		 */
		public static final String TITLE_JP = "title_jp";
		/**
		 * The constant TITLE_KR.
		 */
		public static final String TITLE_KR = "title_kr";
		/**
		 * The constant TITLE_GE.
		 */
		public static final String TITLE_GE = "title_ge";
		/**
		 * The constant TITLE_FR.
		 */
		public static final String TITLE_FR = "title_fr";
		/**
		 * The constant TITLE_EG.
		 */
		public static final String TITLE_EG = "title_eg";
		/**
		 * The constant TITLE_CN.
		 */
		public static final String TITLE_CN = "title_cn";
		/**
		 * The constant CONFIDENTIAL_LEVEL.
		 */
		public static final String CONFIDENTIAL_LEVEL = "confidential_level";
		/**
		 * The constant ID.
		 */
		public static final String ID = "id";
		/**
		 * The constant SUBMITED_TIME_NULL.
		 */
		public static final String SUBMITED_TIME_NULL = "1000-01-01T00:00:00Z";
		/**
		 * The constant NULL_PUBLISH_TIME.
		 */
		public static final String NULL_PUBLISH_TIME = "2018-01-30T00:00:00Z";
		/**
		 * The constant PUBLISHED_TIME.
		 */
		public static final String PUBLISHED_TIME = "published_time";
		/**
		 * The constant AUTHOR.
		 */
		public static final String AUTHOR = "author";
		/**
		 * The constant LEVEL.
		 */
		public static final String LEVEL = "level";
		/**
		 * The constant TOTAL.
		 */
		public static final String TOTAL = "total";
		/**
		 * The constant TOTALDOWNLOAD.
		 */
		public static final String TOTALDOWNLOAD = "totalDownload";
		/**
		 * The constant TOTALVIEW.
		 */
		public static final String TOTALVIEW = "totalView";
		/**
		 * The constant SLIDESHOW.
		 */
		public static final String SLIDESHOW = "slideShow";
		/**
		 * The constant CLIENT_LIBRARY.
		 */
		public static final String CLIENT_LIBRARY = "fk_otk_ip_client_library";
		/**
		 * The constant FRAMEWORK.
		 */
		public static final String FRAMEWORK = "fk_otk_ip_framework";
		/**
		 * The constant OPERATING_SYSTEM.
		 */
		public static final String OPERATING_SYSTEM = "fk_otk_ip_operating_system";
		/**
		 * The constant PROGRAM.
		 */
		public static final String PROGRAM = "fk_otk_ip_program";
		/**
		 * The constant SMART_FACTORY.
		 */
		public static final String SMART_FACTORY = "fk_otk_ip_smart_factory";
		/**
		 * The constant IDE.
		 */
		public static final String IDE = "fk_otk_ip_ide";
		/**
		 * The constant DATABASE_DATASTORE.
		 */
		public static final String DATABASE_DATASTORE = "fk_otk_ip_database_datastore";;
		/**
		 * The constant CLOUD_HARDWARE.
		 */
		public static final String CLOUD_HARDWARE = "fk_otk_ip_cloud_hardware";
		/**
		 * The constant DOMAINS_TYPE.
		 */
		public static final String DOMAINS_TYPE = "fk_otk_ip_domains_type";
		/**
		 * The constant DOMAINS_DETAIL.
		 */
		public static final String DOMAINS_DETAIL = "fk_otk_ip_domains_detail";
			
	    }
}
