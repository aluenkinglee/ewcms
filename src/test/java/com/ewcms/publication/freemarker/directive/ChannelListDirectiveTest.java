/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.FreemarkerTest;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.service.ChannelPublishServiceable;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *ChannelListDirective单元测试
 *
 * @author wangwei
 */
public class ChannelListDirectiveTest extends FreemarkerTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("channel", new ChannelDirective());
    }

    private Channel createChannel(int id,boolean publicenabled) {
        Channel channel = new Channel();

        channel.setId(id);
        channel.setName("频道" + String.valueOf(id));
        channel.setDir(channel.getName());
        channel.setUrl("http://www.sina.com/" + String.valueOf(id));
        channel.setPublicenable(publicenabled);

        return channel;
    }

    private List<Channel> createChannelChildren(int parentId, int count) {
        List<Channel> children = new ArrayList<Channel>();

        for (int i = 0; i < count; ++i) {
            Channel channel = new Channel();
            channel.setId(parentId * 10 + i);
            channel.setName(String.format("%s_%d_%d", "频道", parentId, i));
            channel.setUrl(String.format("%s/%d/%d", "http://www.sina.com", parentId, i));
            channel.setPublicenable((i % 2) == 0);    
            channel.setListSize(20);
            children.add(channel);
        }
        
        return children;
    }
    @Test
    public void testLoadingChannel()throws Exception{
        Channel channel = createChannel(1,true);
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        ChannelListDirective directive = new ChannelListDirective(service);
        
        List<Channel> list = directive.loadingChannel(1, 1, false, false, false);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(1, list.size());
    }
    
    @Test
    public void testDebugLoadingChannel()throws Exception{
        Channel channel = createChannel(1,false);
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        ChannelListDirective directive = new ChannelListDirective(service);
        
        List<Channel> list = directive.loadingChannel(1, 1, false, false,true);
        Assert.assertEquals(1, list.size());
    }
   
    
    @Test
    public void testLoadingChannelChildren()throws Exception{
        Channel channel = createChannel(1,true);
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        List<Channel> children = createChannelChildren(1,5);
        when(service.getChannelChildren(any(Integer.class))).thenReturn(children);
        ChannelListDirective directive = new ChannelListDirective(service);
        
        List<Channel> list = directive.loadingChannel(1, 1, false, true,false);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(3, list.size());
    }
    
    @Test
    public void testDebugLoadingChannelChildren()throws Exception{
        Channel channel = createChannel(1,true);
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        List<Channel> children = createChannelChildren(1,5);
        when(service.getChannelChildren(any(Integer.class))).thenReturn(children);
        ChannelListDirective directive = new ChannelListDirective(service);
        
        List<Channel> list = directive.loadingChannel(1, 1, false, true,true);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(5, list.size());
    }
    
    private String getTemplatePath(String name){
        return String.format("directive/channellist/%s", name);
    }
    
    private Map<String,Object> templateParameters(){
        Map<String,Object> params = new HashMap<String,Object>();
        Site site = new Site();
        site.setId(1);
        params.put(GlobalVariable.SITE.toString(), site);
        return params;
    }
    
    @Test
    public void testValueTemplate()throws Exception{
        Channel channel = createChannel(1,true);
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        ChannelListDirective directive = new ChannelListDirective(service);
        
        cfg.setSharedVariable("clist", directive);
        
        Template template = cfg.getTemplate(getTemplatePath("value.html"));
        Map<String,Object> params = templateParameters();
        String value = process(template, params);
        
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("频道1", value);
    }
    
    @Test
    public void testChildrenTemplate()throws Exception{
        Channel channel = createChannel(1,true);
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(any(Integer.class), any(Integer.class))).thenReturn(channel);
        List<Channel> children = createChannelChildren(1,5);
        when(service.getChannelChildren(any(Integer.class))).thenReturn(children);
        ChannelListDirective directive = new ChannelListDirective(service);
        
        cfg.setSharedVariable("clist", directive);
        
        Template template = cfg.getTemplate(getTemplatePath("child.html"));
        Map<String,Object> params = templateParameters();
        String value = process(template, params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("频道_1_0频道_1_2频道_1_4", value);
    }
    
    /**
     * 初始数组频道mock
     */
    private void initMockArrayChannel(){
        ChannelPublishServiceable service = mock(ChannelPublishServiceable.class);
        when(service.getChannel(1, 1)).thenReturn(createChannel(1,true));
        when(service.getChannel(1, 2)).thenReturn(createChannel(2,true));
        when(service.getChannel(1, 3)).thenReturn(createChannel(3,true));
        when(service.getChannelByUrlOrPath(1,"db/test")).thenReturn(createChannel(3,true));
        ChannelListDirective directive = new ChannelListDirective(service);
        
        cfg.setSharedVariable("clist", directive);
    }
 
    @Test
    public void testArrayTemplate()throws Exception{
        initMockArrayChannel();
        
        Template template = cfg.getTemplate(getTemplatePath("array.html"));
        Map<String,Object> params = templateParameters();
        String value = process(template, params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("频道1频道2频道3", value);
    }
    
    @Test
    public void testLoopTempalte()throws Exception{
        initMockArrayChannel();
        
        Template template = cfg.getTemplate(getTemplatePath("loop.html"));
        Map<String,Object> params = templateParameters();
        String value = process(template, params);
        value = StringUtils.deleteWhitespace(value);
        Assert.assertEquals("频道1频道2", value);
    }
}
