package com.sf.mybatis.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * <br>
 * <b>功能�?</b>详细的功能描�?<br>
 * <b>作�?�：</b>肖财�?<br>
 * <b>日期�?</b> 2011-12-20 <br>
 * <b>版权�?有：<b>版权�?�?(C) 2011，QQ 405645010<br>
 * 
 */
public class WolfXmlUtil {
	//private SAXReader saxReader=new SAXReader();
    @SuppressWarnings("unused")
	private void getAddStrutsElemant(String filePath,String nodexPath) throws Exception{
    	//SAXReader saxReader=new SAXReader();
    	Document document=this.getPath(filePath, "utf-8");
    	Element element=document.getRootElement();
    	Element nextElement=element.element("package");
    	Element newElement=nextElement.addElement("action");
    	newElement.addComment("系统自动创建");
    	newElement.addAttribute("name", "test");
    	newElement.addAttribute("class", "");
    	newElement.addAttribute("method", "");
    	newElement.addText("hello");
    }
    /**
     * 
     * <br>
     * <b>功能�?</b>添加<br>
     * <b>作�?�：</b>肖财�?<br>
     * <b>日期�?</b> 2011-12-21 <br>
     * @param filePath
     * @param xPath
     * @param newNode
     * @param attrMap
     * @param text
     * @throws Exception
     */
	public void getAddNode(String filePath,String xPath,String newNode,Map<String,String> attrMap,String text) throws Exception{
		if(this.getQueryNode(filePath, xPath, newNode, attrMap, text)<1){
			Document document=this.getPath(filePath, "UTF-8");
			List<?> list=document.selectNodes(xPath);
			System.out.println(xPath);
	    	Element element=(Element) list.get(0);
	    	Element newElement=element.addElement(newNode);
	    	for(Map.Entry<String, String> entry:attrMap.entrySet()){
	    		newElement.addAttribute(entry.getKey(), entry.getValue());
	    	}
	    	if(null!=text && text.trim().length()>0){
	    	    newElement.addText(text);
	    	}
	    	this.getXMLWrite(document, filePath);
	    	System.out.println("修改"+xPath+"成功");
		}else{
			System.out.println("已添�?");
		}
    }
	/**
	 * 
	 * <br>
	 * <b>功能�?</b>查询<br>
	 * <b>作�?�：</b>肖财�?<br>
	 * <b>日期�?</b> 2011-12-21 <br>
	 * @param filePath
	 * @param xPath
	 * @param newNode
	 * @param attrMap
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public int getQueryNode(String filePath,String xPath,String newNode,Map<String,String> attrMap,String text) throws Exception{
	   /**
		 *  用XPath.selectSingleNode(root,xpath)方法来查询具体某个节点�?? 
			根据单个属�?�查询： 
			Element findelement=(Element)XPath.selectSingleNode(root,"/root/biaodan[@bdname='"+chkname+"']"); 
			根据多个属�?�查询： 
			Element findelement=(Element)XPath.selectSingleNode(root,"/root/biaodan[@bdname='"+chkname+"'][@liuname='"+flowname+"']"); 
			根据多个属�?�查询，（多属�?�之间或的关系）�? 
			Element findelement=(Element)XPath.selectSingleNode(root,"/root/biaodan[@bdname='"+chkname+"'] | /root/biaodan[@liuname='"+flowname+"']"); 
		          如果有多个节点，用�??/”分�?，如：book[@url='dom4j.com']/title[@id='123']
		    element.asXML() 输出
		*/
		int count=0;
		Document document=this.getPath(filePath, "UTF-8");
    	StringBuffer sb=new StringBuffer();
    	for(Map.Entry<String, String> entry:attrMap.entrySet()){
    		sb.append("[@"+entry.getKey()+"='"+entry.getValue()+"']");
    	}
    	xPath=xPath+"/"+newNode+sb.toString();
    	System.out.println("xPath="+xPath);
    	document.selectNodes(xPath);
    	List<?> list=document.selectNodes(xPath);
    	for(int i=0;i<list.size();i++){
    		Element element=(Element) list.get(i);
    		if(element.getText().equals(text)){
    			count++;
    		}
    	}
    	//System.out.println(xPath+"|i="+list.size());
    	//System.out.println("count="+count+"|list.size="+list.size());
    	return count;
    }
	/**
	 * 
	 * <br>
	 * <b>功能�?</b>保存文件<br>
	 * <b>作�?�：</b>肖财�?<br>
	 * <b>日期�?</b> 2011-12-21 <br>
	 * @param document
	 * @param filePath
	 * @throws Exception
	 */
	public void getXMLWrite(Document document,String filePath) throws Exception{
			//FileOutputStream fos = new FileOutputStream(filePath);
	    	OutputFormat of = new OutputFormat(" ", true);
	    	of.setEncoding("UTF-8"); 
	    	XMLWriter xw = new XMLWriter(new FileWriter(filePath), of); 
	    	xw.setEscapeText(false); //不需要转�?
	        xw.write(document);   
	        xw.close(); 
	    	System.out.println(document.asXML());
	}
	
	public void getEditNode(String filePath,String xPath,Map<String,String> attrMap,String text) throws Exception{
		Document document=this.getPath(filePath, "UTF-8");
		List<?> list=document.selectNodes(xPath);
		Element element=(Element) list.get(0);
		if(null!=attrMap){
			for(Map.Entry<String, String> entry:attrMap.entrySet()){
				element.addAttribute(entry.getKey(), entry.getValue());
	    	}
		}
		/******************删除子节�?*********************/
		List<?> nodelist=element.elements();
		for(int i=0;i<nodelist.size();i++){
			Element nodeElement=(Element) nodelist.get(i);
			nodeElement.getParent().remove(nodeElement);
		}
		element.setText(text);
		this.getXMLWrite(document, filePath);
	}
	/**
	 * 
	 * <br>
	 * <b>功能�?</b>获取文件设置编码<br>
	 * <b>作�?�：</b>肖财�?<br>
	 * <b>日期�?</b> 2011-12-22 <br>
	 * @param filePath
	 * @param coding
	 * @return
	 */
	public Document getPath(String filePath,String coding){
		 SAXReader saxReader=new SAXReader();
		 
		 Document document = null;
		 try {
			 saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)),coding));
			document = saxReader.read(read);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return document;
	}
	public static void main(String[] args){
		WolfXmlUtil xml=new WolfXmlUtil();
		String filePath1="D:\\MyEclipse 8.5\\ssi\\src\\com\\wei\\ssi\\conf\\sqlmap\\ProUserSQL.xml";
		String filePath="D:\\MyEclipse 8.5\\ssi\\src\\com\\wei\\ssi\\conf\\struts2\\struts2-ssi-proWbType.xml";
		try{
		//xml.getAddStrutsElemant(filePath, "/struts/package/action");
	/*	Map map=new HashMap<String, String>();
		map.put("file", "no");
		xml.getAddNode(filePath, "/struts/package","action",map,"/web/proWbType/proWbTypeEdit.jsp");
		xml.getQueryNode(filePath, "/struts/package","action",map,"/web/proWbType/proWbTypeEdit.jsp");*/
		Map<String,String> map=new HashMap<String, String>();
		map.put("file", "no");
		xml.getEditNode(filePath1, "/sqlMap/select[@id='getProUserList']", map, "嘿嘿");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
