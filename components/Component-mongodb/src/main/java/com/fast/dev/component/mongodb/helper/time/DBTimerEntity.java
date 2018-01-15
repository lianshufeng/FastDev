package com.fast.dev.component.mongodb.helper.time;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "_DBTimerEntity")
public class DBTimerEntity {

	// id的默认值
	public final static String idValues = DBTimerEntity.class.getSimpleName();

	@Id
	private String name = idValues;

	// 当前时间
	private Date date;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
