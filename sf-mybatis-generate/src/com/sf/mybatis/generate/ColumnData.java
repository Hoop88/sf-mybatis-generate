package com.sf.mybatis.generate;

/**
 * 表字段类
 * @author Administrator
 *
 */
public class ColumnData {

	private String columnName;
	private String dataType;
	private String columnComment;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	} 
}
