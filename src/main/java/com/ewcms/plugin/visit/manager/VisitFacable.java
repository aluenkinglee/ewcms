/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager;

import java.util.List;

import com.ewcms.plugin.visit.manager.vo.ClickRateVo;
import com.ewcms.plugin.visit.manager.vo.PublishedVo;
import com.ewcms.plugin.visit.manager.vo.TrafficVo;
import com.ewcms.plugin.visit.manager.vo.ClientVo;
import com.ewcms.plugin.visit.manager.vo.SummaryVo;
import com.ewcms.plugin.visit.manager.vo.LoyaltyVo;
import com.ewcms.plugin.visit.model.Visit;
import com.ewcms.plugin.visit.model.VisitItem;
import com.ewcms.web.vo.TreeGridNode;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface VisitFacable {
	
	public void addVisitByLoadEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByKeepAliveEvent(Visit visit, VisitItem visitItem);
	
	public void addVisitByUnloadEvent(Visit visit, VisitItem visitItem);
	
	public String findFirstDate(Integer siteId);
	
	public Integer findDays(Integer siteId);
	
	public List<SummaryVo> findSummaryTable(Integer siteId);

	public String findSummaryReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findSiteTable(String startDate, String endDate, Integer siteId);
	
	public List<SummaryVo> findLastTable(String startDate, String endDate, Integer siteId);
	
	public List<SummaryVo> findHourTable(String startDate, String endDate, Integer siteId);
	
	public String findHourReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findEntranceTable(String startDate, String endDate, Integer siteId);
	
	public String findEmtranceTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findExitTable(String startDate, String endDate, Integer siteId);
	
	public String findExitTrendReport(String url, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findHostTable(String startDate, String endDate, Integer siteId);
	
	public String findHostTrendReport(String host, String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public String findHostReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 区域分布(国家)统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findCountryTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 区域分布(国家)统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCountryReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 区域分布(省份) 统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findProvinceTable(String startDate, String endDate, String country, Integer siteId);
	
	/**
	 * 区域分布(省份)统计图形	
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findProvinceReport(String startDate, String endDate, String country, Integer siteId);
	
	/**
	 * 区域分布(城市) 统计表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份
	 * @param siteId 站点编号
	 * @return List SummaryVo对象集合
	 */
	public List<SummaryVo> findCityTable(String startDate, String endDate, String country, String province, Integer siteId);
	
	/**
	 * 区域分布(城市) 统计图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCityReport(String startDate, String endDate, String country, String province, Integer siteId);
	
	/**
	 * 区域分布(国家)统计之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCountryTrendReport(String startDate, String endDate, String country, Integer labelCount, Integer siteId);
	
	/**
	 * 区域分布(省份)统计之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findProvinceTrendReport(String startDate, String endDate, String country, String province, Integer labelCount, Integer siteId);
	
	/**
	 * 区域分布(城市)统计之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param country 国家名称
	 * @param province 省份名称
	 * @param city 城市名称
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findCityTrendReport(String startDate, String endDate, String country, String province, String city, Integer labelCount, Integer siteId);
	
	public String findOnlineReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	public List<SummaryVo> findOnlineTable(String startDate, String endDate, Integer siteId);

	public List<ClientVo> findClientTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendReport(String startDate, String endDate, String fieldName, String fieldValue, Integer labelCount, Integer siteId);
	
	public List<ClientVo> findClientBooleanTable(String startDate, String endDate, String fieldName, Integer siteId);
	
	public String findClientBooleanReport(String startDate, String endDate,  String fieldName, Integer siteId);
	
	public String findClientTrendBooleanReport(String startDate, String endDate, String fieldName, Boolean enabled, Integer labelCount, Integer siteId);

	/**
	 * 文章点击排行表格
	 * 
	 * @param channelId 频道编号
	 * @param siteId 站点编号
	 * @return List ClickVo对象集合
	 */
	public List<TrafficVo> findArticleTable(Integer channelId, Integer siteId);
	
	/**
	 * URL点击排行表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List ClickVo对象集合
	 */
	public List<TrafficVo> findUrlTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * URL点击排行之时间趋势图形
	 * 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期
	 * @param url URL地址
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findUrlTrendReport(String startDate, String endDate, String url, Integer labelCount, Integer siteId);
	
	/**
	 * 栏目点击排行表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param channelParentId 父频道编号
	 * @param siteId 站点编号
	 * @return List ClickVo对象集合
	 */
	public List<TrafficVo> findChannelTable(String startDate, String endDate, Integer channelParentId, Integer siteId);
	
	/**
	 * 栏目点击排行图形
	 * 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期
	 * @param channelParentId 父频道编号
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findChannelReport(String startDate, String endDate, Integer channelParentId, Integer labelCount, Integer siteId);
	
	/**
	 * 栏目点击排行之时间趋势图形
	 * 
	 * @param startDate 开始日期 
	 * @param endDate 结束日期
	 * @param channelId 频道编号
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findChannelTrendReport(String startDate, String endDate, Integer channelId, Integer labelCount, Integer siteId);
	
	/**
	 * 访问频率表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List VisitFreqVo对象集合
	 */
	public List<LoyaltyVo> findFrequencyTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问频率图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findFrequencyReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问频率之时间趋势
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param frequency 访问频率数
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findFrequencyTrendReport(String startDate, String endDate, Long frequency, Integer labelCount, Integer siteId);
	
	/**
	 * 访问深度表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List VisitFreqVo对象集合
	 */
	public List<LoyaltyVo> findDepthTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问深度图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findDepthReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 访问深度之时间趋势
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param frequency 访问频率数
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findDepthTrendReport(String startDate, String endDate, Long depth, Integer labelCount, Integer siteId);
	
	/**
	 * 回头率表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List LoyaltyVo对象集合
	 */
	public List<LoyaltyVo> findVisitorTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 回头率表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findVisitorReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 停留时间表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List VisitFreqVo对象集合
	 */
	public List<LoyaltyVo> findStickTimeTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 停留时间图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findStickTimeReport(String startDate, String endDate, Integer labelCount, Integer siteId);
	
	/**
	 * 来源组成表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SourceVo对象集合
	 */
	public List<ClickRateVo> findSourceTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 来源组成图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSourceReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 搜索引擎表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SourceVo对象集合
	 */
	public List<ClickRateVo> findSearchTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 搜索引擎图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSearchReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 搜索引擎之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param domain 域名
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findSearchTrendReport(String startDate, String endDate, String domain, Integer labelCount, Integer siteId);
	
	/**
	 * 来源网站表格
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List SourceVo对象集合
	 */
	public List<ClickRateVo> findWebSiteTable(String startDate, String endDate, Integer siteId);
	
	/**
	 * 来源网站图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findWebSiteReport(String startDate, String endDate, Integer siteId);
	
	/**
	 * 来源网站之时间趋势图形
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param domain 域名
	 * @param labelCount 图形X轴显示标题数量
	 * @param siteId 站点编号
	 * @return String 图形格式字符串
	 */
	public String findWebSiteTrendReport(String startDate, String endDate, String webSite, Integer labelCount, Integer siteId);
	
	/**
	 * 人员发布统计
	 * 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param siteId 站点编号
	 * @param channelId 频道编号
	 * @return List PublishedVo对象集合
	 */
	public List<PublishedVo> findStaffReleased(String startDate, String endDate, Integer siteId, Integer channelId);
	
	/**
	 * 栏目发布统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findChannelRelease(String startDate, String endDate, Integer siteId);
	
	/**
	 * 政民互动统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findInteractive(String startDate, String endDate);
	
	/**
	 * 网上咨询统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findAdvisory(String startDate, String endDate);
	
	/**
	 * 组织机构发布统计
	 * 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param siteId 站点编号 
	 * @return List TreeGridNode对象集合
	 */
	public List<TreeGridNode> findOrganReleased(String startDate, String endDate, Integer siteId);
}
