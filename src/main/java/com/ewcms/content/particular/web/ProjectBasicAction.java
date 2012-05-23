/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.particular.web;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.content.particular.ParticularFacable;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.Struts2Util;

/**
 * @author 吴智俊
 */
public class ProjectBasicAction extends CrudBaseAction<ProjectBasic, Long> {

	private static final long serialVersionUID = -7215016049247026935L;

	@Autowired
	private ParticularFacable particularFac;
	
	private Integer channelId;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public ProjectBasic getProjectBasicVo() {
		return super.getVo();
	}

	public void setProjectBasicVo(ProjectBasic projectBasic) {
		super.setVo(projectBasic);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected Long getPK(ProjectBasic vo) {
		return vo.getId();
	}

	@Override
	protected ProjectBasic getOperator(Long pk) {
		return particularFac.findProjectBasicById(pk);
	}

	@Override
	protected void deleteOperator(Long pk) {
		particularFac.delProjectBasic(pk);
	}

	@Override
	protected Long saveOperator(ProjectBasic vo, boolean isUpdate) {
		vo.setChannelId(getChannelId());
		if (isUpdate) {
			return particularFac.updProjectBasic(vo);
		} else {
			return particularFac.addProjectBasic(vo);
		}
	}

	@Override
	protected ProjectBasic createEmptyVo() {
		return new ProjectBasic();
	}
	
	private File xmlFile;
	
	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String importXML(){
		if (getXmlFile() != null && getChannelId() != null) {
			particularFac.addProjectBasicByImportXml(getXmlFile(), getChannelId());
		}
		return INPUT;
	}
	
	public void exportXML(){
		if (getSelections() != null && getSelections().size() > 0){
			ServletOutputStream out = null;
			try{
				Document document = particularFac.exportXml(getSelections());
				
				StringWriter stringWriter = new StringWriter();
				
	            OutputFormat xmlFormat = new OutputFormat();  
	            xmlFormat.setEncoding("UTF-8");  
	            XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);  
	            xmlWriter.write(document); 
	            xmlWriter.flush();
	            xmlWriter.close();  
	            
	            HttpServletResponse resp = Struts2Util.getResponse();
	            out = resp.getOutputStream();
		    	resp.setCharacterEncoding("UTF-8");
		    	resp.setContentType("text/xml; charset=UTF-8");
		    	resp.addHeader("Content-Disposition", "attachment; filename=xmjbxx.xml");
		    	out.write(stringWriter.toString().getBytes());
		    	out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (out != null){
					try {
						out.close();
					} catch (IOException e) {
					}
					out = null;
				}
			}		
		}
	}
}