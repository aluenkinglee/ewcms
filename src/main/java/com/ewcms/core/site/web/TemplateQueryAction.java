/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.core.site.web;

import static com.ewcms.common.lang.EmptyUtil.isNotNull;
import static com.ewcms.common.lang.EmptyUtil.isStringNotEmpty;

import org.springframework.stereotype.Controller;

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.EntityQueryable;
import com.ewcms.core.site.model.Template;
import com.ewcms.web.QueryBaseAction;

/**
 * 
 * @author 周冬初
 */
@Controller()
public class TemplateQueryAction extends QueryBaseAction {

	private static final long serialVersionUID = 6314025295072345366L;
	
	private Integer channelId;
	
    public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Override
	protected Resultable queryResult(com.ewcms.common.query.jpa.QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Template.class).setPage(page).setRow(rows);
		Integer id = getParameterValue(Integer.class, "id");
		if (isNotNull(id)) query.eq("id", id);
		String name = getParameterValue(String.class, "name");
		if (isStringNotEmpty(name)) query.likeAnywhere("name", name);
		query.eq("channelId", getChannelId());
		entityOrder(query, order);
		return query.queryCacheResult(cacheKey);
	}

	@Override
	protected Resultable querySelectionsResult(com.ewcms.common.query.jpa.QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
		EntityQueryable query = queryFactory.createEntityQuery(Template.class).setPage(page).setRow(rows);
		query.in("id", getIds(Integer.class));
		query.orderDesc("id");
		return query.queryResult();
	}
}
