/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ewcms.common.dao.JpaDAO;
import com.ewcms.content.particular.model.EnterpriseBasic;

@Repository
public class EnterpriseBasicDAO extends JpaDAO<Long, EnterpriseBasic> {
	public List<EnterpriseBasic> findEnterpriseBasicByPageAndRows(final Integer page, final Integer rows, final String name){
		String hql = "From EnterpriseBasic As e Where e.release=true And e.organ!=null And e.name Like :name Order By e.published Desc";
		TypedQuery<EnterpriseBasic> query = this.getEntityManager().createQuery(hql, EnterpriseBasic.class);
		query.setParameter("name", "%" + name + "%");
		query.setFirstResult(rows * (page - 1));
		query.setMaxResults(rows);
		return query.getResultList();
	}
	
	public Long findEnterpriseBasicTotal(final String name){
		String hql = "Select Count(e.id) From EnterpriseBasic As e Where e.release=true And e.organ!=null And e.name Like :name ";
		TypedQuery<Long> query = this.getEntityManager().createQuery(hql, Long.class);
		query.setParameter("name", "%" + name + "%");
		return query.getSingleResult();
	}
	
	public EnterpriseBasic findEnterpriseBasicByYyzzzch(final String yyzzzch){
		String hql = "From EnterpriseBasic As p Where p.yyzzzch=:yyzzzch";
		TypedQuery<EnterpriseBasic> query = this.getEntityManager().createQuery(hql, EnterpriseBasic.class);
		query.setParameter("yyzzzch", yyzzzch);
		EnterpriseBasic enterpriseBasic = null;
		try{
			enterpriseBasic = (EnterpriseBasic) query.getSingleResult();
		}catch(NoResultException e){
		}
		return enterpriseBasic;
	}
}
