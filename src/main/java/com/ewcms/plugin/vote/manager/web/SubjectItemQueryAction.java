/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.vote.manager.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.plugin.vote.model.SubjectItem.Status;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 吴智俊
 *
 */
public class SubjectItemQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 5539246522711259327L;
	
	private Long subjectId;
	
	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "Select i From Subject As s Left Join s.subjectItems AS i Where s.id=:subjectId ";
		String countHql = "Select count(i.id) From Subject As s Left Join s.subjectItems AS i Where s.id=:subjectId ";
    	
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And i.id=:id ";
			countHql += " And i.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)){
			hql += " And i.title Like :title";
			countHql += " And i.title Like :title";
		}
		
    	String status = getParameterValue(String.class, "status");
		if (isStringNotEmpty(status) && !status.equals("-1")){
			hql += " And i.status=:status";
			countHql += " And i.status=:status";
		}
		
		Long voteNumberBegin = getParameterValue(Long.class, "voteNumberBegin");
		if (isNotNull(voteNumberBegin)) {
			hql += " And i.voteNumber>=:voteNumberBegin";
			countHql +=" And i.voteNumber>=:voteNumberBegin";
		}
		
		Long voteNumberEnd = getParameterValue(Long.class, "voteNumberEnd");
		if (isNotNull(voteNumberEnd)) {
			hql += " And i.voteNumber<=:voteNumberEnd";
			countHql +=" And i.voteNumber<=:voteNumberEnd";
		}
		
		hql += " Order By i.sort Asc ";
		hql += " Limit " + rows + " OffSet " + (rows * (page + 1));
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isStringNotEmpty(status) && !status.equals("-1")){
			query.setParameter("status", Status.valueOf(status));
		}
		if (isNotNull(voteNumberBegin)) {
			query.setParameter("voteNumberBegin", voteNumberBegin);
		}
		if (isNotNull(voteNumberEnd)) {
			query.setParameter("voteNumberEnd", voteNumberEnd);
		}
		query.setParameter("subjectId", getSubjectId());
    	
    	return query.setRow(rows).setPage(page).queryResult();
	}

	@Override
	protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select i From Subject As s Left Join s.subjectItems AS i Where s.id=:subjectId And i.id In (:id) Order By i.sort ";
		String countHql = "Select count(i.id) From Subject As s Left Join s.subjectItems AS i Where s.id=:subjectId And i.id In (:id) ";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);

		query.setParameter("id", getIds(Long.class));
		query.setParameter("subjectId", getSubjectId());
    	
        return query.setRow(rows).setPage(page).queryResult();
	}

}
