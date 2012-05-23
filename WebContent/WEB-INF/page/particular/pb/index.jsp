<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>项目基本数据</title>	
		<s:include value="../../taglibs.jsp"/>
		<script type="text/javascript">
		$(function(){
			ewcmsBOBJ = new EwcmsBase();
			ewcmsBOBJ.setQueryURL('<s:url namespace="/particular/pb" action="query"><s:param name="channelId" value="channelId"></s:param></s:url>');
			
			ewcmsBOBJ.setWinWidth(1050);
			ewcmsBOBJ.setWinHeight(600);
			
			ewcmsBOBJ.addToolItem('导入XML','icon-redo',importXML);
			ewcmsBOBJ.addToolItem('生成XML','icon-undo', generatorXML);
			
			ewcmsBOBJ.openDataGrid('#tt',{
                columns:[[
                        {field:'id',title:'编号',hidden:true},
						{field:'code',title:'项目编号',width:150,sortable:true},
		                {field:'name',title:'项目名称',width:200},
		                {field:'buildTime',title:'建设时间',width:85},
		                {field:'investmentScale',title:'投资规模',width:200},
		                {field:'overview',title:'项目概况',width:300},
		                {field:'buildUnit',title:'建设单位',width:200},
		                {field:'unitId',title:'项目编号',width:60},
		                {field:'unitPhone',title:'单位联系电话',width:100},
		                {field:'unitAddress',title:'单位地址',width:200},
		                {field:'zoningName',title:'行政区划名称',width:80,
		                	formatter : function(val, rec) {
		                		if (rec.zoningCode != null){
		                			return rec.zoningCode.name;
		                		}
		                	}
		                },
		                {field:'organizationCode',title:'组织机构代码',width:80},
		                {field:'industryName',title:'行业名称',width:80,
		                	formatter : function(val, rec){
		                		if (rec.industryCode != null){
		                			return rec.industryCode.name;
		                		}
		                	}	
		                },
		                {field:'category', title:'项目类别', width: 120},
		                {field:'approvalRecordName',title:'审批备案机关名称',width:120,
		                	formatter : function(val, rec){
		                		if (rec.approvalRecord != null){
		                			return rec.approvalRecord.name;
		                		}
		                	}	
		                },
		                {field:'contact',title:'联系人',width:120},
		                {field:'phone',title:'联系人电话',width:100},
		                {field:'email',title:'联系人电子邮箱',width:120},
		                {field:'address',title:'项目地址',width:200},
		                {field:'natureDescription',title:'建设性质',width:100},
		                {field:'shape',title:'形式',width:100},
		                {field:'documentId',title:'文号',width:100},
		                {field:'participation',title:'参建单位',width:200},
		                {field:'publishingSectorName',title:'发布部门名称',width:200,
		                	formatter : function(val, rec){
		                		if (rec.publishingSector != null){
		                			return rec.publishingSector.name;
		                		}
		                	}	
		                }
                  ]]
			});

			ewcmsOOBJ = new EwcmsOperate();
			ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
			ewcmsOOBJ.setInputURL('<s:url namespace="/particular/pb" action="input"><s:param name="channelId" value="channelId"></s:param></s:url>');
			ewcmsOOBJ.setDeleteURL('<s:url namespace="/particular/pb" action="delete"><s:param name="channelId" value="channelId"></s:param></s:url>');
			
			$('#cc_industryCode').combobox({
        		url: '<s:url namespace="/particular/ic" action="findIndustryCodeAll"/>',
        		valueField:'id',
                textField:'text',
                mode:'remote',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
        	});
            $('#cc_zoningCode').combobox({
        		url: '<s:url namespace="/particular/zc" action="findZoningCodeAll"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
            });
            
            $('#cc_approvalRecordCode').combobox({
        		url: '<s:url namespace="/particular/ar" action="findApprovalRecordAll"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
            });
            
            $('#cc_publishingSector').combobox({
        		url: '<s:url namespace="/particular/ps" action="findPsToPb"><s:param name="projectBasicId" value="projectBasicVo.id"></s:param></s:url>',
        		valueField:'id',
                textField:'text',
        		editable:false,
        		multiple:false,
        		cascadeCheck:false,
        		panelWidth:120
            });
		});
		function importXML(){
			var url='<s:url namespace="/particular/pb" action="import"><s:param name="channelId" value="channelId"></s:param></s:url>';
			ewcmsBOBJ.openWindow('#import-window',{url:url,iframeID:'#importifr',width:560,height:100});
		}
		
		function generatorXML(){
			var rows = $("#tt").datagrid('getSelections');
	        if(rows.length == 0){
	         	$.messager.alert('提示','请选择导出记录','info');
	            return;
	        }
	        var url = '<s:url namespace="/particular/pb" action="export"/>';
	        var parameter = '?selections=' + rows[0].id;
	    	for ( var i = 1; i < rows.length - 1; ++i) {
	    		parameter = parameter + '&selections=' + rows[i].id;
	    	}
	    	window.location = url + parameter;
		}
		</script>	
	</head>
	<body class="easyui-layout">
		<s:hidden id="channelId" name="channelId"/>
		<div region="center" style="padding:2px;" border="false">
	 		<table id="tt" fit="true"></table>	
	 	</div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;项目基本数据" style="display:none;">
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
                                <td class="tdtitle">项目编号：</td>
                                <td class="tdinput">
                                    <input type="text" id="code" name="code" class="inputtext" maxLength="23" size="23"/>
                                </td>
                                <td class="tdtitle">项目名称：</td>
                                <td class="tdinput">
                                    <input type="text" id="name" name="name" class="inputtext"/>
                                </td>
 	                            <td class="tdtitle">建设性质：</td>
	                            <td class="tdinput">
	                                <s:select list="@com.ewcms.content.particular.model.ProjectBasic$Nature@values()" listValue="description" name="buildNature" id="buildNature" headerKey="" headerValue="------请选择------"/>
	                            </td>
                        	</tr>
							<tr>
	                            <td class="tdtitle">行业编码：</td>
	                            <td class="tdinput">
	                            	<input id="cc_industryCode" name="industryCode" style="width:150px;"></input>
	                            </td>
	                            <td class="tdtitle">行政区划代码：</td>
	                            <td class="tdinput">
	                            	<input id="cc_zoningCode" name="zoningCode" style="width: 150px;"></input>
	                            </td>
	                            <td class="tdtitle">审批备案机关编号：</td>
	                            <td class="tdinput">
	                            	<input id="cc_approvalRecordCode" name="approvalRecord" style="width: 150px;"></input>
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
        <div id="import-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;项目基本数据导入" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                   <iframe id="importifr"  name="importifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
            </div>
        </div>	
	</body>
</html>