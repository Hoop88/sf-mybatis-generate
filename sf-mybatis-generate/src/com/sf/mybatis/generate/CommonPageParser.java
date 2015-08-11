package com.sf.mybatis.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;


/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>Edson.di<br>
 * <b>日期：</b> 2011-7-22 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class CommonPageParser {
	
	private static VelocityEngine ve;// = VelocityEngineUtil.getVelocityEngine();
	
	private static Properties properties;
	
	private final static String CONTENT_ENCODING ="UTF-8";
	
	private static final Log log = LogFactory.getLog(CommonPageParser.class);
	
	
	private static boolean isReplace = true;  //是否可以替换文件 true =可以替换，false =不可以替换
	
	
	
	public static void main(String[] args) {
	}
	
	
	static{
		try{
			//获取文件模板根路径
			String  templateBasePath = "D:\\workspace\\sf-mybatis-generate\\template";//"Constant.WORK_TEMPLATE_PATH;
			Properties properties = new Properties();
			properties.setProperty(Velocity.RESOURCE_LOADER,"file");
			properties.setProperty("file.resource.loader.description","Velocity File Resource Loader");
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,  templateBasePath);
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");
	        properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
	        properties.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS,  "org.apache.velocity.runtime.log.Log4JLogChute");
	        properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
	        properties.setProperty("directive.set.null.allowed", "true");
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init(properties);
			ve = velocityEngine;
		}catch(Exception e){
			log.error(e);
		}
	}
	
	/**
	 * <br>
	 * <b>功能：</b>生成页面文件<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> 2011-7-23 <br>
	 * @param context 内容上下文
	 * @param templateName 模板文件路径（相对路径）article\\article_main.html
	 * @param targetPage 生成页面文件路径（相对路径） vowo\index_1.html
	 */
	public static void WriterPage(VelocityContext context,String templateName,String fileDirPath,String targetFile){
		try{
			File file = new File(fileDirPath+targetFile);
			if(!file.exists()){
				new File(file.getParent()).mkdirs();
			}else{
				if(isReplace){
					log.info("替换文件"+file.getAbsolutePath());
				}else{
				}
			}
			
			Template template = ve.getTemplate(templateName, CONTENT_ENCODING);
			FileOutputStream fos = new FileOutputStream(file);  
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos,CONTENT_ENCODING));
			template.merge(context, writer);
			writer.flush();  
		    writer.close();  
	    	fos.close();  
//	    	System.out.println("页面生成成功"+file.getAbsolutePath());
		}catch (Exception e) {
			log.error(e);
		}
	} 

}
