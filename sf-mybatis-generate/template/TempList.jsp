<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.ssi.com/ssi-el/e" prefix="e"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>分类信息</title>
	<link type="text/css" rel="stylesheet" href="${basePath}static/common/css/tips.css">
	<link type="text/css" rel="stylesheet" href="${basePath}static/sys/css/public.css">
	<script type="text/javascript" src="${basePath}static/common/js/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="${basePath}static/common/js/lhgdialog/lhgdialog.js"></script>
	<script type="text/javascript" src="${basePath}static/common/js/YiYaTips.js"></script>
	<script type="text/javascript" src="${basePath}static/common/js/public.js"></script>
  </head>
<body>
<center>
<form name="form1" method="post" action="${basePath}/sys/$!{lowerName}/list.do">
  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="tit" align="left"><strong>&nbsp;系统管理&gt;&gt;分类管理</strong></td>
	</tr>
</table>

<div style="height:10px;"></div>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td class="td_bd">
	<table class="maintable" border="0" width="98%" cellspacing="0">
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>
		<%-- 查询条件区域 模板区域 --%>
		<tr>
			<td width="15%" style="text-align: right;">
				分类名称：
			</td>
			<td width="35%">
				<input class="inputsearch" type="text" id="name" name="name"
					value="<c:out value="${}" />" size="15" maxlength="15" />
			</td>
			<td width="15%" style="text-align: right;">
				状态：
			</td>
			<td width="35%">
				<select class="inputsearch" name="state">
					<option value="" >----</option>
					
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<img src="${basePath}static/sys/images/report/query.gif" class="query_btn" onclick="query()" style="cursor: pointer;">
			</td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
	</table>
</td>
</tr>
</table>
<table id="scroll_box_content" class="tablelistcontent" width="98%" border="1" cellspacing="1">
    <tr>
    	<%-- 查询列表标题模版 请修改标题
        <th width="5%" height="25"><input type="checkbox" onclick="doSelectAll('ids', this)"></th>
		<th width="5%" height="25">序号</th>
		<th width="12%">名称</th>
		<th width="12%">排序</th>
		<th width="12%">导航</th>
		<th width="8%">状态</th>
		<th width="8%">操作</th>
		--%>
    </tr>
     <c:forEach items="${resultList}"  var="item" varStatus="st">
     	<%--
	    <tr align="center" <c:if test="$ {st.count%2==0}">bgcolor="#EEEEEE"</c:if> class="Off" onMouseOver="this.className='Up'" onMouseOut="this.className='Off'">
	        <td><input type="checkbox" name="ids" value="${item.id}"></td>
			<td><c:out value=""/></td>   
			<td><c:out value=""/></td>
			<td><c:out value=""/></td>
			<td><c:out value=""/></td>
			<td><c:out value=""/></td>
			<td><a href="javascript:toEdit('${id}');">编辑</a></td>  
		</tr>
		--%>
	   </c:forEach>
</table>
<table width="98%"  border="0" cellspacing="1" cellpadding="0" height="30">
    <tr>
        <td> <%-- 按钮--%>
		     <img src="${basePath}static/sys/images/report/add.gif" class="add_btn" onclick="toAdd()" style="cursor: pointer;">&nbsp;
		     <img src="${basePath}static/sys/images/report/delete.gif" class="delete_btn"  onclick="deletes()" style="cursor: pointer;">&nbsp;
		</td> 
		<%-- 分页面公共页面 --%>
		<td align="right"><%@ include file="/jsp/sys/common/navigate.jsp"%></td>
	</tr>
</table>  
</form>
</center>
<script type="text/javascript">
	function toAdd(){
		var _url = '${basePath}sys/$!{lowerName}/toadd.do';
		YiYa.show(_url,'新增分类',450,250);
	}
	
	function toEdit(id){
		var _url = '${basePath}sys/$!{lowerName}/toedit.do?id='+id;
		YiYa.show(_url,'编辑分类信息',450,250);
	}
	
	
	function query(){
		var theform = $("form:first")[0];
		theform.action = '${basePath}/sys/$!{lowerName}/list.do';
		YiYa.query(); 
	} 
	
	function deletes(){
		var theform = $("form:first")[0];
		theform.action = '${basePath}sys/$!{lowerName}/deletes.do';
		YiYa.submit(theform,function callback(data){
			if(data.result ==  'success'){
				alert("保存成功！");
				query();
			}else{
				alert(data.errMsg);
			}
		});
	} 
</script>

</body>
</html>
		