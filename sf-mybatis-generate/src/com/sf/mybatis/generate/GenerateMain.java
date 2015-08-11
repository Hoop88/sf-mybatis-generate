package com.sf.mybatis.generate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.velocity.VelocityContext;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>Edson.di<br>
 * <b>日期：</b> 07 16, 2015 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class GenerateMain {
	private static ResourceBundle res = ResourceBundle.getBundle("sfgenerateConfig");
	private static String url= res.getString("gpt.url"); 
	private static String username =  res.getString("gpt.username");
	private static String passWord = res.getString("gpt.password");

	 //项目跟路径路径，此处修改为你的项目路径
	private static String rootPath = "D:\\workspace\\sf-mybatis-generate\\";//getRootPath();// "F:\\openwork\\open\\";
	private static String actionPath = "D:\\workspace\\sf-mybatis-generate\\src\\com\\sf\\pmp\\";


	public static void main(String[] args) {
		 CreateBean createBean=new CreateBean();
		 createBean.setMysqlInfo(url, username, passWord);
		 /** 此处修改成你的 表名 和 中文注释***/
		 String tableName="user_role"; //
		 String codeName ="用户角色";//中文注释  当然你用英文也是可以的 
		 String className= createBean.getTablesNameToClassName(tableName);
		 String lowerName =className.substring(0, 1).toLowerCase()+className.substring(1, className.length());
		 
		 //根路径
		 String srcPath = rootPath + "src\\";
		 //包路径
		 String pckPath = rootPath + "src\\com\\sf\\pmp\\";
		 //页面路径，放到WEB-INF下面是为了不让手动输入路径访问jsp页面，起到安全作用
		 String webPath = rootPath + "WebRoot\\jsp\\sys\\"; 
		 
		 //java,xml文件名称
		 String modelPath = "model\\"+className+"Model.java";
		 String beanPath =  "bean\\"+className+".java";
		 String mapperPath = "mapper\\"+className+"Mapper.java";
		 String servicePath = "service\\"+className+"Service.java";
		 String controllerPath = "action\\"+className+"Action.java";
		 String sqlMapperPath = "mybatis\\"+className+"Mapper.xml";
		//jsp页面路径
		 String pageEditPath = lowerName+"\\"+lowerName+"Edit.jsp";
		 String pageListPath = lowerName+"\\"+lowerName+"List.jsp";
		 
		
		VelocityContext context = new VelocityContext();
		context.put("className", className); //
		context.put("lowerName", lowerName);
		context.put("codeName", codeName);
		context.put("tableName", tableName);
		
		/******************************生成bean字段*********************************/
		try{
			context.put("feilds",createBean.getBeanFeilds(tableName)); //生成bean
		}catch(Exception e){
			e.printStackTrace();
		}

		/*******************************生成sql语句**********************************/
		try{
			Map<String,Object> sqlMap=createBean.getAutoCreateSql(tableName);
			context.put("columnDatas",createBean.getColumnDatas(tableName)); //生成bean
			context.put("SQL",sqlMap);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
//		
		//-------------------生成文件代码---------------------/
		CommonPageParser.WriterPage(context, "TempBean.java",pckPath, beanPath);  //生成实体Bean
		CommonPageParser.WriterPage(context, "TempModel.java",pckPath,modelPath); //生成Model
		CommonPageParser.WriterPage(context, "TempMapper.java", pckPath,mapperPath); //生成MybatisMapper接口 相当于Dao
		CommonPageParser.WriterPage(context, "TempService.java", pckPath,servicePath);//生成Service
		CommonPageParser.WriterPage(context, "TempMapper.xml",pckPath,sqlMapperPath);//生成Mybatis xml配置文件
		CommonPageParser.WriterPage(context, "TempController.java",actionPath, controllerPath);//生成Controller 相当于接口
//		生JSP页面，如果不需要可以注释掉
		context.put("basePath", "${e:basePath()}");
		CommonPageParser.WriterPage(context, "TempList.jsp",webPath, pageListPath);//生成Controller 相当于接口
		CommonPageParser.WriterPage(context, "TempEdit.jsp",webPath, pageEditPath);//生成Controller 相当于接口

		
		/*************************修改xml文件*****************************/
		WolfXmlUtil xml=new WolfXmlUtil();
		Map<String,String> attrMap=new HashMap<String, String>();
		try{
		   /**   引入到mybatis-config.xml 配置文件 */
//			attrMap.clear();
//			attrMap.put("resource","com/yiya/mybatis/"+className+"Mapper.xml");
//		    xml.getAddNode(rootPath+"conf/mybatis-config.xml", "/configuration/mappers", "mapper", attrMap, "");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取项目的路径
	 * @return
	 */
	public static String getRootPath(){
		String rootPath ="";
		try{
			 File file = new File(CommonPageParser.class.getResource("/").getFile());
			 rootPath = file.getParentFile().getParentFile().getParent()+"\\";
			 rootPath = java.net.URLDecoder.decode(rootPath,"utf-8");
			 System.out.println(rootPath);
			 return rootPath;
		}catch(Exception e){
			e.printStackTrace();
		}
		return rootPath;
	}
}
