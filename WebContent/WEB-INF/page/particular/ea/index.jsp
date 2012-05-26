<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>企业文章信息</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript" src='<s:url value="/ewcmssource/page/particular/ea/index.js"/>'></script>
		<script type="text/javascript">
			var eaIndex = new EaIndex({
				queryUrl:'<s:url namespace="/particular/ea" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>',
				inputUrl:'<s:url namespace="/particular/ea" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>',
				deleteUrl:'<s:url namespace="/particular/ea" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>'
			});
			$(function(){
				<s:include value="../../alertMessage.jsp"/>
				eaIndex.init({
		        	datagridId:'#tt'
				});
			});
		</script>		
	</head>
	<body class="easyui-layout">
		<s:hidden id="channelId" name="channelId"/>
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;企业文章信息" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-save" href="javascript:void(0)" onclick="saveOperator()">保存</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#edit-window').window('close');">取消</a>
                </div>
            </div>
        </div>	
        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true"  >
                <div region="center" border="false" >
                <form id="queryform">
                	<table class="formtable">
                            <tr>
                                <td class="tdtitle">组织机构代码：</td>
                                <td class="tdinput">
                                    <input type="text" id="code" name="code" class="inputtext" maxLength="9" size="9"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">机关单位名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext" size="60"/>
                                </td>
                            </tr>
               		</table>
               	</form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                    <a class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)" onclick="javascript:$('#query-window').window('close');">取消</a>
                </div>
            </div>
        </div>      	
	</body>
</html>