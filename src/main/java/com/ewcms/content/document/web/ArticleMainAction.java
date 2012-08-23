/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.ewcms.content.document.BaseException;
import com.ewcms.content.document.DocumentFacable;
import com.ewcms.content.document.model.ArticleMain;
import com.ewcms.web.CrudBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;

/**
 * 
 * @author 吴智俊
 */
public class ArticleMainAction extends CrudBaseAction<ArticleMain, Long> {

	private static final long serialVersionUID = 7275967705688396524L;

	@Autowired
	private DocumentFacable documentFac;
	
	private Integer channelId;
	
	private Boolean node=false;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Boolean getNode() {
		return node;
	}

	public void setNode(Boolean node) {
		this.node = node;
	}

	public ArticleMain getArticleMainVo() {
		return super.getVo();
	}

	public void setArticleMainVo(ArticleMain articleMainVo) {
		super.setVo(articleMainVo);
	}

	public List<Long> getSelections() {
		return super.getOperatorPK();
	}

	public void setSelections(List<Long> selections) {
		super.setOperatorPK(selections);
	}

	@Override
	protected ArticleMain createEmptyVo() {
		return null;
	}

	@Override
	protected void deleteOperator(Long articleMainId) {
		documentFac.delArticleMainToRecycleBin(articleMainId, getChannelId());
	}

	@Override
	protected Long getPK(ArticleMain vo) {
		return vo.getId();
	}

	@Override
	protected Long saveOperator(ArticleMain vo, boolean isUpdate) {
		return null;
	}

	public void submitReview() {
		try {
			if (getSelections() != null && getSelections().size() >= 1 && getChannelId() != null){
				documentFac.submitReviewArticleMain(getSelections(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (BaseException e){
			Struts2Util.renderJson(JSONUtil.toJSON("notinstate"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}

	public Boolean recursion;
	
	public Boolean getRecursion() {
		return recursion;
	}

	public void setRecursion(Boolean recursion) {
		this.recursion = recursion;
	}

	public void pubArticle() {
		try {
			documentFac.pubArticleMainByChannel(getChannelId(), getRecursion());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}

	private List<Integer> selectChannelIds;

	public List<Integer> getSelectChannelIds() {
		return selectChannelIds;
	}

	public void setSelectChannelIds(List<Integer> selectChannelIds) {
		this.selectChannelIds = selectChannelIds;
	}

	public String copy() {
		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0) {
			Struts2Util.renderText(documentFac.copyArticleMainToChannel(getSelections(), getSelectChannelIds(), getChannelId()).toString());
		}
		return NONE;
	}

	public String move() {
		if (getSelections() != null && getSelections().size() > 0 && getSelectChannelIds() != null && getSelectChannelIds().size() > 0) {
			Struts2Util.renderText(documentFac.moveArticleMainToChannel(getSelections(), getSelectChannelIds(), getChannelId()).toString());
		}
		return NONE;
	}

	private Integer review;
	private String reason;

	public Integer getReview() {
		return review;
	}

	public void setReview(Integer review) {
		this.review = review;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void reviewArticle() {
		try {
			if (getSelections() != null && getSelections().size() == 1 && getChannelId() != null){
				documentFac.reviewArticleMain(getSelections().get(0), getChannelId(), getReview(), getReason());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		} catch (Exception e) {
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	private Long sort;
	private Boolean isTop;
	private Integer isInsert;
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Integer getIsInsert() {
		return isInsert;
	}

	public void setIsInsert(Integer isInsert) {
		this.isInsert = isInsert;
	}

	public void sortArticle(){
		try{
			if (getSelections() != null && getSelections().size() == 1 && getChannelId() != null){
				documentFac.moveArticleMainSort(getSelections().get(0), getChannelId(), getSort(), getIsInsert(), getIsTop());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void isSortArticle(){
		try{
			if (getChannelId() != null){
				Boolean isSort = documentFac.findArticleMainByChannelAndEqualSort(getChannelId(), getSort(), getIsTop());
				Struts2Util.renderJson(JSONUtil.toJSON(isSort.toString()));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}

	@Override
	protected ArticleMain getOperator(Long pk) {
		return null;
	}
	
	public void clearSortArticle(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				documentFac.clearArticleMainSort(getSelections(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void breakArticle(){
		try{
			if (getChannelId() != null && getSelections() != null && getSelections().size() > 0){
				documentFac.breakArticleMain(getSelections(), getChannelId());
				Struts2Util.renderJson(JSONUtil.toJSON("true"));
			}else{
				Struts2Util.renderJson(JSONUtil.toJSON("false"));
			}
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(BaseException e){
			Struts2Util.renderJson(JSONUtil.toJSON("notinstate"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	public void topArticle(){
		try{
			documentFac.topArticleMain(getSelections(), getIsTop(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
	
	private Boolean isShare;
	
	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public void shareArticle(){
		try{
			documentFac.shareArticleMain(getSelections(), getIsShare(), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON("true"));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}

	
	public void reviewEffective(){
		try{
			Boolean effective = documentFac.reviewArticleMainIsEffective(getSelections().get(0), getChannelId());
			Struts2Util.renderJson(JSONUtil.toJSON(effective.toString()));
		} catch (AccessDeniedException e) {
			Struts2Util.renderJson(JSONUtil.toJSON("accessdenied"));
		}catch(Exception e){
			Struts2Util.renderJson(JSONUtil.toJSON("system-false"));
		}
	}
}
