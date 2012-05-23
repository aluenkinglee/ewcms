package com.ewcms.content.particular.service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.particular.dao.ApprovalRecordDAO;
import com.ewcms.content.particular.dao.IndustryCodeDAO;
import com.ewcms.content.particular.dao.ProjectBasicDAO;
import com.ewcms.content.particular.dao.PublishingSectorDAO;
import com.ewcms.content.particular.dao.ZoningCodeDAO;
import com.ewcms.content.particular.model.ApprovalRecord;
import com.ewcms.content.particular.model.IndustryCode;
import com.ewcms.content.particular.model.ProjectBasic;
import com.ewcms.content.particular.model.ProjectBasic.Nature;
import com.ewcms.content.particular.model.PublishingSector;
import com.ewcms.content.particular.model.ZoningCode;
import com.ewcms.content.particular.util.XmlConvert;

@Service
public class ProjectBasicService implements ProjectBasicServiceable {

	@Autowired
	private ProjectBasicDAO projectBasicDAO;
	@Autowired
	private ZoningCodeDAO zoningCodeDAO;
	@Autowired
	private ApprovalRecordDAO approvalRecordDAO;
	@Autowired
	private IndustryCodeDAO industryCodeDAO; 
	@Autowired
	private PublishingSectorDAO publishingSectorDAO;
	
	@Override
	public Long addProjectBasic(ProjectBasic projectBasic) {
		projectBasic.setCode(generateCode(projectBasic));
		
		setZoningCode(projectBasic);
		setApprovalRecord(projectBasic);
		setIndustryCode(projectBasic);
		setPublishingSector(projectBasic);
		
		projectBasicDAO.persist(projectBasic);
		return projectBasic.getId();
	}

	private String generateCode(ProjectBasic projectBasic){
		String zoningCode_code = projectBasic.getZoningCode().getCode();
		String organizationCode_code = projectBasic.getOrganizationCode();
		Calendar ca = Calendar.getInstance();
		ca.setTime(projectBasic.getBuildTime());
		String year = String.valueOf(ca.get(Calendar.YEAR));
		String unitId = projectBasic.getUnitId();
		String code = zoningCode_code + organizationCode_code + year + unitId;
		
		return code;
	}
	
	@Override
	public Long updProjectBasic(ProjectBasic projectBasic) {
		setZoningCode(projectBasic);
		setApprovalRecord(projectBasic);
		setIndustryCode(projectBasic);
		setPublishingSector(projectBasic);

		projectBasicDAO.merge(projectBasic);
		return projectBasic.getId();
	}

	private void setZoningCode(ProjectBasic projectBasic){
		String zoningCode_code = projectBasic.getZoningCode().getCode();
		Assert.notNull(zoningCode_code);
		ZoningCode zoningCode = zoningCodeDAO.findZoningCodeByCode(zoningCode_code);
		Assert.notNull(zoningCode);
		projectBasic.setZoningCode(zoningCode);
	}
	
	private void setApprovalRecord(ProjectBasic projectBasic){
		String approvalRecord_code = projectBasic.getApprovalRecord().getCode();
		Assert.notNull(approvalRecord_code);
		ApprovalRecord approvalRecord = approvalRecordDAO.findApprovalRecordByCode(approvalRecord_code);
		Assert.notNull(approvalRecord);
		projectBasic.setApprovalRecord(approvalRecord);
	}
	
	private void setIndustryCode(ProjectBasic projectBasic){
		String industryCode_code = projectBasic.getIndustryCode().getCode();
		Assert.notNull(industryCode_code);
		IndustryCode industryCode = industryCodeDAO.findIndustryCodeByCode(industryCode_code);
		Assert.notNull(industryCode);
		projectBasic.setIndustryCode(industryCode);
	}
	
	private void setPublishingSector(ProjectBasic projectBasic){
		String publishingSector_code = projectBasic.getPublishingSector().getCode();
		PublishingSector publishingSector = publishingSectorDAO.findPublishingSectorByCode(publishingSector_code);
		projectBasic.setPublishingSector(publishingSector);
	}
	
	@Override
	public void delProjectBasic(Long id) {
		projectBasicDAO.removeByPK(id);
	}

	@Override
	public ProjectBasic findProjectBasicById(Long id) {
		return projectBasicDAO.get(id);
	}
	
	@Override
	public List<ProjectBasic> findProjectBasicAll(){
		return projectBasicDAO.findProjectBasicAll();
	}
	
	@Override
	public void addProjectBasicByImportXml(File file, Integer channelId){
		List<Map<String,Object>> list = XmlConvert.importXML(file);
		
		if (list.isEmpty()) return;
		
		for (Map<String, Object> map : list){
			ProjectBasic projectBasic = new ProjectBasic();

			String zoningCode_code = (String) map.get("行政区划代码");
			ZoningCode zoningCode = zoningCodeDAO.findZoningCodeByCode(zoningCode_code);
			if (zoningCode == null) continue;
			projectBasic.setZoningCode(zoningCode);
				
			String organizationCode = (String) map.get("组织机构代码");
			projectBasic.setOrganizationCode(organizationCode);
				
			String buildTime = (String) map.get("建设时间");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				projectBasic.setBuildTime(dateFormat.parse(buildTime));
			} catch (ParseException e) {
				continue;
			}
				
			String unitId = (String) map.get("建设单位项目编号");
			projectBasic.setUnitId(unitId);
				
			String generateCode = generateCode(projectBasic);
			projectBasic.setCode(generateCode);
			ProjectBasic projectBasic_db = projectBasicDAO.findProjectBasicByCode(generateCode);
			if (projectBasic_db != null){
				projectBasic.setId(projectBasic_db.getId());
				projectBasic.setPublishingSector(projectBasic_db.getPublishingSector());
			}
			
			String bildNature = (String) map.get("建设性质");
			for (Nature nature : Nature.values()){
				if (nature.getDescription().trim().equals(bildNature.trim())){
					projectBasic.setBildNature(nature);
					break;
				}
			}
				
			String industryCode_code = (String) map.get("行业编码");
			IndustryCode industryCode = industryCodeDAO.findIndustryCodeByCode(industryCode_code);
			if (industryCode == null) continue;
			projectBasic.setIndustryCode(industryCode);
			
			String approvalRecord_code = (String) map.get("审批备案机关编号");
			ApprovalRecord approvalRecord = approvalRecordDAO.findApprovalRecordByCode(approvalRecord_code);
			if (approvalRecord == null) continue;
			projectBasic.setApprovalRecord(approvalRecord);
				
			projectBasic.setChannelId(channelId);
			
			String name = (String) map.get("项目名称");
			projectBasic.setName(name);
				
			String investmentScale = (String) map.get("投资规模");
			projectBasic.setInvestmentScale(investmentScale);
				
			String overview = (String) map.get("项目概况");
			projectBasic.setOverview(overview);
				
			String buildUnit= (String) map.get("建设单位");
			projectBasic.setBuildUnit(buildUnit);
				
			String unitPhone= (String) map.get("建设单位联系电话");
			projectBasic.setUnitPhone(unitPhone);
				
			String unitAddress = (String) map.get("建设单位地址");
			projectBasic.setUnitAddress(unitAddress);
				
			String category = (String) map.get("项目类别");
			projectBasic.setCategory(category);
				
			String address = (String) map.get("项目地址");
			projectBasic.setAddress(address);
				
			String contact = (String) map.get("项目联系人");
			projectBasic.setContact(contact);
				
			String phone = (String) map.get("项目联系人电话");
			projectBasic.setPhone(phone);
				
			String email = (String) map.get("项目联系人电子邮箱");
			projectBasic.setEmail(email);
				
			String shape = (String) map.get("形式");
			projectBasic.setShape(shape);
				
			String documentId = (String) map.get("文号");
			projectBasic.setDocumentId(documentId);
				
			String participation = (String) map.get("参建单位");
			projectBasic.setParticipation(participation);
				
			if (projectBasic.getId() == null){
				projectBasicDAO.persist(projectBasic);
			}else{
				projectBasicDAO.merge(projectBasic);
			}
		}
	}
	
	public Document exportXml(List<Long> projectBasicIds){
		Document document = DocumentHelper.createDocument();  
		Element root = document.addElement("MetaDatas");  
        Element metaViewData = root.addElement("MetaViewData");
        
        if (projectBasicIds.isEmpty()) return document;
        
		for (Long projectBasicId : projectBasicIds){
			ProjectBasic projectBasic = projectBasicDAO.get(projectBasicId);
	        
	        Element projects = metaViewData.addElement("PROPERTIES");
	        
	        Element code = projects.addElement("项目编号");  
	        code.addText(projectBasic.getCode() == null ? "" : projectBasic.getCode());
	        
	        Element name = projects.addElement("项目名称");
	        name.addText(projectBasic.getName() == null ? "" : projectBasic.getName());

	        Element buildTime = projects.addElement("建设时间");
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        if (projectBasic.getBuildTime() != null){
	        	buildTime.addText(dateFormat.format(projectBasic.getBuildTime()));
	        }else{
	        	buildTime.addText("");
	        }
	        
	        Element investmentScale = projects.addElement("投资规模");
	        investmentScale.addText(projectBasic.getInvestmentScale() == null ? "" : projectBasic.getInvestmentScale());
	        
	        Element overview = projects.addElement("项目概况");
	        overview.addText(projectBasic.getOverview() == null ? "" : projectBasic.getOverview());
	        
	        Element buildUnit= projects.addElement("建设单位");
	        buildUnit.addText(projectBasic.getBuildUnit() == null ? "" : projectBasic.getBuildUnit());
	        
	        Element unitPhone= projects.addElement("建设单位联系电话");
	        unitPhone.addText(projectBasic.getUnitPhone() == null ? "" : projectBasic.getUnitPhone());
	        
	        Element unitAddress = projects.addElement("建设单位地址");
	        unitAddress.addText(projectBasic.getUnitAddress() == null ? "" : projectBasic.getUnitAddress());
	        
	        Element zoningCode_code= projects.addElement("行政区划代码");
	        if (projectBasic.getZoningCode() != null)
	        	zoningCode_code.addText(projectBasic.getZoningCode().getCode() == null ? "" : projectBasic.getZoningCode().getCode());
	        else
	        	zoningCode_code.addText("");
	        
	        Element organizationCode = projects.addElement("组织机构代码");
	        organizationCode.addText(projectBasic.getOrganizationCode() == null ? "" : projectBasic.getOrganizationCode());
	        
	        Element industryCode_code = projects.addElement("行业编码");
	        if (projectBasic.getIndustryCode() != null)
	        	industryCode_code.addText(projectBasic.getIndustryCode().getCode() == null ? "" : projectBasic.getIndustryCode().getCode());
	        else
	        	industryCode_code.addText("");
	        
	        Element category = projects.addElement("项目类别");
	        category.addText(projectBasic.getCategory() == null ? "" : projectBasic.getCategory());
	        
	        Element unitId = projects.addElement("建设单位项目编号");
	        unitId.addText(projectBasic.getUnitId() == null ? "" : projectBasic.getUnitId());
	        
	        Element approvalRecord_code = projects.addElement("审批备案机关编号");
	        if (projectBasic.getApprovalRecord() != null)
	        	approvalRecord_code.addText(projectBasic.getApprovalRecord().getCode() == null ? "" : projectBasic.getApprovalRecord().getCode());
	        else
	        	approvalRecord_code.addText("");
	        
	        Element address = projects.addElement("项目地址");
	        address.addText(projectBasic.getAddress() == null ? "" : projectBasic.getAddress());
	        
	        Element bildNature = projects.addElement("建设性质");
	        if (projectBasic.getBildNature() != null)
	        	bildNature.addText(projectBasic.getBildNature().getDescription() == null ? "" : projectBasic.getBildNature().getDescription());
	        else
	        	bildNature.addText("");
	        
	        Element contact = projects.addElement("项目联系人");
	        contact.addText(projectBasic.getContact() == null ? "" : projectBasic.getContact());
	        
	        Element phone = projects.addElement("项目联系人电话");
	        phone.addText(projectBasic.getPhone() == null ? "" : projectBasic.getPhone());
	        
	        Element email = projects.addElement("项目联系人电子邮箱");
	        email.addText(projectBasic.getEmail() == null ? "" : projectBasic.getEmail());
		}
		return document;
	}
}
