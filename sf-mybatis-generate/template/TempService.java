package com.yiya.service;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiya.mapper.${className}Mapper;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Service<br>
 * <b>作者：</b>Edson.di<br>
 * <b>日期：</b> Dec 9, 2011 <br>
 * <b>版权所有：<b>版权所有(C) 2011，WWW.VOWO.COM<br>
 */
@Service("$!{lowerName}Service")
public class ${className}Service<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(${className}Service.class);
	

	

	@Autowired
    private ${className}Mapper<T> mapper;

		
	public ${className}Mapper<T> getMapper() {
		return mapper;
	}

}
