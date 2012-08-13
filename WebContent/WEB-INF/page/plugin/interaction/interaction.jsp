<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>政民互动</title>
		<s:include value="../../taglibs.jsp"/>
        <script>
            $(function(){
				//创建和设置页面的基本对象 EwcmsBase
				ewcmsBOBJ = new EwcmsBase();
				ewcmsBOBJ.setQueryURL('<s:url namespace="/plguin/interaction" action="query"/>'); 
            	ewcmsBOBJ.delToolItem('新增');
              	ewcmsBOBJ.delToolItem('修改');
              	ewcmsBOBJ.delToolItem('删除');
                //数据表格定义
              ewcmsBOBJ.openDataGrid('#tt',{
                    singleSelect:true,
                    columns:[[
                            {field:'id',title:'序号',width:40},
                            {field:'username',title:'用户名',width:100},
                            {field:'name',title:'昵名',width:100},
                            {field:'title',title:'标题',width:200},
                            {field:'organName',title:'单位',width:200},
                            {field:'type',title:'类型',width:60,
                                formatter:function(val,rec){
                                    if (val == 1){return '咨询';}
                                    if (val == 2){return '投诉';}
                                    if (val == 3){return '建议'};
                                }
                            },
                            {field:'state',title:'状态',width:60,
                                formatter:function(val,rec){
                                    if (val == 0){return '待处理';}
                                    if (val == 1){return '回复';}
                                }
                            },
                             {field:'checked',title:'审核',width:60,
                                formatter:function(val,rec){
                                    if (val){
                                        return '通过';
                                    }else{
                                        return '';
                                    }
                                }
                            },
                            {field:'date',title:'日期',width:100}
                        ]]
                });
				ewcmsOOBJ = new EwcmsOperate();
				ewcmsOOBJ.setQueryURL(ewcmsBOBJ.getQueryURL());
				ewcmsOOBJ.setInputURL('<s:url action="input"/>');
                $('#tt').datagrid({
                  onDblClickRow:function(rowIndex, rowData){
                      var url = '<s:url action="edit"/>?id='+rowData.id;
                      $("#editifr").attr('src',url);
                      openWindow('#edit-window',{height:380,width:600});
                  }
                });
            });
            function queryInteractionSearch(options){
                if(typeof(options) == 'undefined')options = {};
                var tableid = (options.tableid ? options.tableid : "#tt");
                var windowid = (options.windowid ? options.windowid : "#edit-window");
                var url = (options.url ? options.url : '<s:url namespace="/plguin/interaction" action="query"/>');
                var formid = (options.formid ? options.formid : "#queryform");

                var value = $(formid).serialize();
                var index = url.indexOf("?");
                if (index == -1){
                    url = url + '?' + value;
                }else{
                    url = url + '&' + value;
                }
                $(tableid).datagrid({
                    url:url
                });
                $(windowid).window('close');
            }

            function closeWindow(){
            	queryInteractionSearch('');
			}
        </script>
    </head>
    <body class="easyui-layout">
        <div region="center" style="padding:2px;" border="false">
            <table id="tt" fit="true"></table>
        </div>
        <div id="edit-window" class="easyui-window" closed="true" icon="icon-winedit" title="&nbsp;" style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <iframe id="editifr"  name="editifr" class="editifr" frameborder="0" onload="iframeFitHeight(this);" scrolling="no"></iframe>
                </div>
            </div>
        </div>

        <div id="query-window" class="easyui-window" closed="true" icon='icon-search' title="查询"  style="display:none;">
            <div class="easyui-layout" fit="true">
                <div region="center" border="false">
                    <form id="queryform">
                        <table class="formtable">
                            <tr>
                                <td class="tdtitle">标题：</td>
                                <td class="tdinput">
                                    <input type="text" id="title_id" name="title" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">类容：</td>
                                <td class="tdinput">
                                    <input type="text" id="content_id" name="content" class="inputtext"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">审核：</td>
                                <td class="tdinput">
                                    <input type="radio" id="checked_id" name="checked" value="0"/>所有
                                    <input type="radio" id="checked_id" name="checked" value="1"/>通过&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="checked" value="2"/>未通过&nbsp;&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="tdtitle">回复：</td>
                                <td class="tdinput">
                                    <input type="radio" id="checked_id" name="replay" value="0"/>所有&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="replay" value="1"/>回复&nbsp;&nbsp;
                                    <input type="radio" id="checked_id" name="replay" value="2"/>未回复
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div region="south" border="false" style="text-align:center;height:28px;line-height:28px;background-color:#f6f6f6">
                    <a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="querySearch('');">查询</a>
                </div>
            </div>
        </div>
    </body>
</html>