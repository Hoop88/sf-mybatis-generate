package com.yiya.ms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.yiya.bean.${className};
import com.yiya.bean.${className};
import com.yiya.bean.SiteType;
import com.yiya.model.${className}Model;
import com.yiya.model.${className}Model;
import com.yiya.service.${className}Service;
import com.yiya.utils.HtmlUtil;
import com.yiya.bean.${className};
import com.yiya.bean.BaseBean.DELETED;
import com.yiya.model.${className}Model;
 
@Controller
@RequestMapping("/${lowerName}") 
public class ${className}Action extends BaseAction{
	
	private final static Logger log= Logger.getLogger(${className}Action.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ${className}Service<${className}> ${lowerName}Service; 
	
	
	
	
	
	/**
	 * 
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list") 
	public ModelAndView  list(${className}Model model,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("${lowerName}/list",context); 
	}
	
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList") 
	public void  datalist(${className}Model model,HttpServletResponse response) throws Exception{
		model.setDeleted(0);
		List<${className}> dataList = ${lowerName}Service.queryByList(model);
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",model.getPager().getRowCount());
		jsonMap.put("rows", dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(${className} bean,Integer[] typeIds,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		bean.setDeleted(DELETED.NO.key);
		if(bean.getId() == null){
			${lowerName}Service.add(bean);
		}else{
			${lowerName}Service.update(bean);
		}
		sendSuccessMessage(response, "保存成功~");
	}
	
	
	@RequestMapping("/getId")
	public void getId(Integer id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap();
		${className} bean  = ${lowerName}Service.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	
	
	@RequestMapping("/delete")
	public void delete(Integer id,HttpServletResponse response) throws Exception{
		${lowerName}Service.delete(id);
		sendSuccessMessage(response, "删除成功");
	}

}
