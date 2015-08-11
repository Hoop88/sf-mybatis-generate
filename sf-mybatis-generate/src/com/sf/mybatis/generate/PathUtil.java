package com.sf.mybatis.generate;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>罗泽军<br>
 * <b>日期：</b> Dec 23, 2011 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class PathUtil {
	
	private static Logger log =Logger.getLogger(PathUtil.class);
	
	/**
	 * 获取项目部署所在根目录  如：F:\openwork\mn606\WebRoot
	 * @return
	 */
	public static String getRootPath(){
		
//		String rootPath ="";
//		try{
//			 File file = new File(PathUtil.class.getResource("/").getFile());
//			 rootPath = file.getParentFile().getParent();
//			 rootPath = java.net.URLDecoder.decode(rootPath,"utf-8");
//			 return rootPath;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		return Constant.WORK_ROOT_PATH;
	}
	
	
	/**
	 * 主站项目路径 http://www.yy606.com/
	 * @return
	 */
	public static String getBasePath(){
		String str = Constant.SSI_WEBSITE_URL;
    	if(StringUtils.isNotBlank(str)){
            try {
				return URLDecoder.decode(str,"utf-8");
			} catch (UnsupportedEncodingException e) {
				log.error("获取根路径异常：",e);
			}
        }
		return str;
	}

	/**
	 * 主站项目路径 http://www.yy606.com/
	 * @return
	 */
	public static String getBasePath(String patth){
		return getBasePath()+patth;
	}
	
    
	/**
     * 获取分类的URL
     * @param classifyId 分类id
     * @param pageId 分页编号
     * @return http://localhost:8080/mn606/classify/2012061009115579058-1.html
     */
	public static String classifyUrl(Long classifyId,Integer pageId){
		if(pageId == null || pageId < 1){
			pageId = 1;
		}
	    StringBuffer url = new StringBuffer(getBasePath());
	    //判断分类id是否为空
	    if(classifyId != null && classifyId > 1){
	    	url.append("classify/").append(classifyId).append("-");
	    }else{
	    	url.append("list/");
	    }
	    url.append(pageId).append(".html");
		return url.toString();
	}
	    
	  /**
     * 获取相册的URL
     * @param albumId 相册Id
     * @param pageId 分页编号
     * @return http://localhost:8080/mn606/album/2012061009115579058-1.html
     */
    public static String albumUrl(Long albumId,Integer pageId){
    	if(pageId == null || pageId < 1){
			pageId = 1;
		}
    	StringBuffer url = new StringBuffer(getBasePath());
  	    url.append("album/").append(albumId);
  	    url.append("-").append(pageId).append(".html");
  		return url.toString();
    }
    
    /**
     * 图片详细页面的URL
     * @param picId 页面
     * @return http://localhost:8080/mn606/pic/2012061723135819196.html
     */
    public static String picPageUrl(Long picId){
    	  StringBuffer url = new StringBuffer(getBasePath());
  	    url.append("pic/").append(picId).append(".html");
  		return url.toString();
    }
	
    /**
     * 留言墙的URL
     * @param picId 页面
     * @return http://localhost:8080/mn606/message/1.html
     */
    public static String messageUrl(Integer pageId){
    	StringBuffer url = new StringBuffer(getBasePath());
  	    url.append("message/");
  	    if(pageId > 1 ){
  	    	url.append(pageId).append(".html");
  	    }
  		return url.toString();
    }
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>获取图片根路径<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 23, 2011 <br>
	 * @param picId
	 * @return http://image.ssi.com/upload/image/
	 */
	public static String getImageBaseURL(){
		return Constant.UPLOAD_URL;
//		return null;
	}
	
	
	/**
	 * 获取图片URL 加上域名
	 * @param picUrl 数据库picUrl字段内容，不带域名
	 * @return
	 */
	public static String pic(String picUrl){
		if(StringUtils.isNotBlank(picUrl)){
    		//将"\"替换成"/"
    		picUrl = picUrl.replaceAll("\\\\","/");
    		return PathUtil.getImageURL(picUrl);
		}
        return "";
	}
	
	/**
	 * 获取图片URL 加上域名
	 * @param picUrl 数据库picUrl字段内容，不带域名
	 * @param cropArea 图片尺寸 格式：50_50 具体规格请查看配置文件appkey.properties
	 * @return 
	 */
	
	 public static String minPic(String picUrl, String size){
		if (StringUtils.isNotBlank(size)) {
			picUrl = PathUtil.minPicPath(picUrl, size);
		}
		return pic(picUrl);
	}
	
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>图片路径<br>
	 * <b>作者：</b>罗泽军<br>
	 * <b>日期：</b> Dec 23, 2011 <br>
	 * 
	 * @param picId
	 * @return 返回图片路径 http://image.ssi.com/upload/image/photo/1111.jpg
	 */
	public static String getImageURL(String picUrl){
		if(picUrl.indexOf("http://")<0){
			return getImageBaseURL()+picUrl;
		}
		return picUrl;
	}
	

	/**
	 * 获取上传图片路径
	 * @param fileName 文件名称 2012061717081386488.jpg
	 * @param sizeDir 尺寸目录 格式:100_100 原图:ori
	 * @return  \images\2012\06\17\100_100\1111.jpg
	 */
	public static String uploadPicPath(String fileName,String sizeDir){
		Date now = new Date(); //当前日期
		StringBuffer path = new StringBuffer(); 
	//	path.append(Constant.UPLOAD_PATH); //上传根目录
		path.append("images").append(File.separatorChar);//图片根目录
		path.append(DateUtil.convertDateToYear(now)).append(File.separatorChar); //年 2012
		path.append(DateUtil.convertDateToMonth(now)).append(File.separatorChar); //月 06
		path.append(DateUtil.convertDateToDay(now)).append(File.separatorChar); //日 17
		path.append(sizeDir).append(File.separatorChar); // 图片
		path.append(fileName);
		return path.toString();
	}
	
	/**
	 * 获取上传图片路径
	 * @param fileName 文件名称 2012061717081386488.jpg
	 * @param sizeDir 尺寸目录 格式:100_100 原图:ori
	 * @return  \images\2012\06\17\100_100\1111.jpg
	 */
	public static String uploadPath(String fileName,String dir,String sizeDir){
		Date now = new Date(); //当前日期
		StringBuffer path = new StringBuffer(); 
		path.append(Constant.UPLOAD_PATH); //上传根目录
		path.append(dir).append(File.separatorChar);//图片根目录
		path.append(DateUtil.convertDateToYear(now)).append(File.separatorChar); //年 2012
		path.append(DateUtil.convertDateToMonth(now)).append(File.separatorChar); //月 06
		path.append(DateUtil.convertDateToDay(now)).append(File.separatorChar); //日 17
		path.append(sizeDir).append(File.separatorChar); // 图片
		path.append(fileName);
		return path.toString();
	}
	
	/**
	 * 获取小图，将路径中ori 替换成指定 尺寸目录
	 * @param filePath  \images\2012\06\17\ori\2012061717081386488.jpg
	 * @param sizeDir 200_200
	 * @return 返回结果 \images\2012\06\17\200_200\2012061717135895318.jpg
	 */
	public static String minPicPath(String picPath,String sizeDir){
		if(StringUtils.isBlank(picPath)){
			return "";
		}
		String path  = picPath.replace("ori", sizeDir);  
		return path;
	}
	
	public static String searchUrl(String url){
		
		return url.toString();
	}
	
	/**
	 * 搜索相似图片URL
	 * @param picUrl
	 * @param pageId 
	 * @return
	 */
	public static String searchSimUrl(String picUrl,String title,Integer pageId){
		StringBuffer url = new StringBuffer(Constant.SEARCH_URL);
		url.append("similar");
		if(StringUtils.isNotBlank(picUrl)){
			if(picUrl.indexOf("http://") < 0){
				picUrl = pic(picUrl);
			}
			url.append("?picUrl=").append(picUrl);
		}else{
			return url.toString();
		}
		if(pageId != null & pageId > 1){
			url.append("&page=").append(pageId);
		}
		if(StringUtils.isNotBlank(title)){
			url.append("&title=").append(title);
		}
		return url.toString();
	}
	
	/****************** ilook MM  url start*******************************/
	/**
     * 获取分类的URL
     * @param classifyId 分类id
     * @param pageId 分页编号
     * @return http://localhost:8080/mn606/app/classify/2012061009115579058-1.html
     */
	public static String ilookListUrl(Long classifyId,Integer pageId){
		if(pageId == null || pageId < 1){
			pageId = 1;
		}
	    StringBuffer url = new StringBuffer(Constant.LOOK_URL);
	    //判断分类id是否为空
	    if(classifyId != null && classifyId > 1){
	    	url.append("classify/").append(classifyId).append("-");
	    }else{
	    	url.append("list/");
	    }
	    url.append(pageId).append(".html");
		return url.toString();
	}
	
	/**
     * 获取分类的URL
     * @param classifyId 分类id
     * @param pageId 分页编号
     * @return http://localhost:8080/mn606/classify/2012061009115579058-1.html
     */
	public static String ilookItemUrl(Long picId){
		StringBuffer url = new StringBuffer(Constant.LOOK_URL);
  	    url.append("item/").append(picId).append(".html");
  		return url.toString();
	}
	/****************** ilook MM  url end*******************************/
	 public static void main(String[] args) {
//		 String t = uploadPicPath(MethodUtil.getInit().getLongId()+".jpg","ori");
//		 t = minPicPath(t,"200_200");
		 System.out.println(getRootPath());
	 }

	
	
}
