/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ewcms.core.site.model.Organ;

/**
 * 项目编号关联的文章信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>projectBasic:项目基本数据</li>
 * <li>organ:组织机构</li>
 * <li>content:内容</li>
 * <li>published:发布日期</li>
 * <li>dense:所属密级</li>
 * <li>channelId:专栏编号</li>
 * <li>release:发布</li>
 * </ul>
 * 
 * @author wuzhijun
 */
@Entity
@Table(name = "particular_project_article")
@SequenceGenerator(name = "seq_particular_project_article", sequenceName = "seq_particular_project_article_id", allocationSize = 1)
public class ProjectArticle implements Serializable {

	private static final long serialVersionUID = -253251794589551895L;

	@Id
	@GeneratedValue(generator = "seq_particular_project_article", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, targetEntity = ProjectBasic.class)
	@JoinColumn(name = "project_basic_code", nullable = false)
	private ProjectBasic projectBasic;
	@OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER, targetEntity = Organ.class)
	@JoinColumn(name = "organ_id")
	private Organ organ;
	@OneToOne(cascade = { CascadeType.ALL }, targetEntity = ParticularContent.class)
	@JoinColumn(name = "content_id")
	private ParticularContent content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "published")
	private Date published;
	@Column(name = "dense")
	@Enumerated(EnumType.STRING)
	private Dense dense;
	@Column(name = "channel_id")
	private Integer channelId;
	@Column(name = "release")
	private Boolean release;

	public ProjectArticle(){
		release = false;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectBasic getProjectBasic() {
		return projectBasic;
	}

	public void setProjectBasic(ProjectBasic projectBasic) {
		this.projectBasic = projectBasic;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public ParticularContent getContent() {
		return content;
	}

	public void setContent(ParticularContent content) {
		this.content = content;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Dense getDense() {
		return dense;
	}

	public String getDenseDescription(){
		if (dense != null){
			return dense.getDescription();
		}else{
			return Dense.GENERAL.getDescription();
		}
	}
	
	public void setDense(Dense dense) {
		this.dense = dense;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Boolean getRelease() {
		return release;
	}

	public void setRelease(Boolean release) {
		this.release = release;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectArticle other = (ProjectArticle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
