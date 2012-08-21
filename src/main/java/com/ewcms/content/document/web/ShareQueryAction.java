/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.HqlQueryable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.content.document.model.Article.Status;
import com.ewcms.content.document.model.Article.Type;
import com.ewcms.web.QueryBaseAction;

/**
 * @author 吴智俊
 */
public class ShareQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 5813703904325874896L;

	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		String hql = "Select o From ArticleMain As o Left Join o.article AS r Where r.delete=false And o.share=true ";
		String countHql = "Select count(o.id) From ArticleMain As o Left Join o.article AS r Where r.delete=false And o.share=true ";
		
		Long id = getParameterValue(Long.class, "id", "查询编号错误，应该是整型");
		if (isNotNull(id)){
			hql += " And o.id=:id ";
			countHql += " And o.id=:id";
		}
		String title = getParameterValue(String.class, "title", "");
		if (isStringNotEmpty(title)){
			hql += " And r.title Like :title";
			countHql += " And r.title Like :title";
		}
		String publishedStart = getParameterValue(String.class, "publishedStart","");
		if (isStringNotEmpty(publishedStart)){
			hql += " And r.published>=:publishedStart";
			countHql += " And r.published>=:publishedStart";
		}
		String publishedEnd = getParameterValue(String.class, "publishedEnd","");
		if (isStringNotEmpty(publishedEnd)){
			hql += " And r.published<:publishedEnd";
			countHql += " And r.published<:publishedEnd";
		}
		String modifiedStart = getParameterValue(String.class, "modifiedStart","");
		if (isStringNotEmpty(modifiedStart)){
			hql += " And r.modified>=:modifiedStart";
			countHql += " And r.modified>=:modifiedStart";
		}
		String modifiedEnd = getParameterValue(String.class, "modifiedEnd","");
		if (isStringNotEmpty(modifiedEnd)){
			hql += " And r.modified<:modifiedEnd";
			countHql += " And r.modified<:modifiedEnd";
		}
		String articleStatus = getParameterValue(String.class, "articleStatus","");
		if (isStringNotEmpty(articleStatus) && !articleStatus.equals("-1")){
			hql += " And r.status=:articleStatus";
			countHql += " And r.status=:articleStatus";
		}
		String articleType = getParameterValue(String.class, "articleType","");
		if (isStringNotEmpty(articleType) && !articleType.equals("-1")){
			hql += " And r.type=:articleType";
			countHql += " And r.type=:articleType";
		}

		hql += " Order By Case When o.top Is Null Then 1 Else 0 End, o.top Desc, o.sort Asc, Case When r.modified Is Null Then 1 Else 0 End, r.modified Desc, Case When r.published Is Null Then 1 Else 0 End, r.published Desc, o.id Desc";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		if (isNotNull(id)){
			query.setParameter("id", id);
		}
		if (isStringNotEmpty(title)){
			query.setParameter("title", "%" + title + "%");
		}
		if (isStringNotEmpty(publishedStart)){
			try {
				query.setParameter("publishedStart", DATE_FORMAT.parse(publishedStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(publishedEnd)){
			try {
				query.setParameter("publishedEnd", DATE_FORMAT.parse(publishedEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(modifiedStart)){
			try {
				query.setParameter("modifiedStart", DATE_FORMAT.parse(modifiedStart));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(modifiedEnd)){
			try {
				query.setParameter("modifiedEnd", DATE_FORMAT.parse(modifiedEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (isStringNotEmpty(articleStatus) && !articleStatus.equals("-1")){
			query.setParameter("articleStatus", Status.valueOf(articleStatus));
		}
		if (isStringNotEmpty(articleType) && !articleType.equals("-1")){
			query.setParameter("articleType", Type.valueOf(articleType));
		}
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryCacheResult(cacheKey);
    }

	@Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		String hql = "Select o From ArticleMain As o Left Join o.article AS r, Where r.delete=false And o.id In (:id) And o.share=true";
		String countHql = "Select count(o.id) From ArticleMain As o Left  Join o.article AS r, Where r.delete=false And o.id In (:id) and o.share=true ";
		
		hql += " Order By Case When o.top Is Null Then 1 Else 0 End, o.top Desc, o.sort Asc, Case When r.modified Is Null Then 1 Else 0 End, r.modified Desc, Case When r.published Is Null Then 1 Else 0 End, r.published Desc, o.id Des";
		
		HqlQueryable query = queryFactory.createHqlQuery(hql, countHql);
		
		query.setParameter("id", getIds(Long.class));
		
		setDateFormat(DATE_FORMAT);
		
		return query.setRow(rows).setPage(page).queryResult();
    }
}
