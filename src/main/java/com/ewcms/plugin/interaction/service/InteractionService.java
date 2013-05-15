/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.plugin.interaction.service;

import com.ewcms.plugin.interaction.dao.InteractionDAO;
import com.ewcms.plugin.interaction.dao.SpeakDAO;
import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.plugin.interaction.model.Speak;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class InteractionService implements InteractionServiceable {

    @Autowired
    private InteractionDAO interactionDAO;
    @Autowired
    private SpeakDAO speakDAO;

    @Override
    public Interaction getInteraction(Integer id) {
    	if (id == null) return new Interaction();
        return interactionDAO.get(id);
    }

    @Override
    public void interactionChecked(Integer id, Boolean checked) {
        Interaction interaction = interactionDAO.get(id);
        if (interaction == null) {
            return;
        }
        interaction.setChecked(checked);
        interactionDAO.persist(interaction);
    }

    @Override
    public void interactionReplay(Integer id, String replay, Date date, Date replayDate, String content, String title, Integer type) {
        Interaction interaction = interactionDAO.get(id);
        
        if (interaction == null) {
            return;
        }
        if (date != null){
        	interaction.setDate(date);
        }
        
        if (replay == null || replay.trim().length() == 0) {
            interaction.setState(0);
            interaction.setReplay(null);
            interaction.setReplayDate(null);
        } else {
            interaction.setState(1);
            interaction.setReplay(replay);
            if (replayDate == null){
            	interaction.setReplayDate(new Date());
            }else{
            	interaction.setReplayDate(replayDate);
            }
        }

        interaction.setContent(content);
        interaction.setTitle(title);
        interaction.setType(type);
        interactionDAO.persist(interaction);
    }

    @Override
    public void interactionOrgan(Integer id, Integer organId, String organName) {
        Interaction interaction = interactionDAO.get(id);
        if (interaction == null) {
            return;
        }
        interaction.setOrganId(organId);
        interaction.setOrganName(organName);
        interactionDAO.persist(interaction);
    }

    @Override
    public void speakChecked(Integer id, boolean checked) {
        Speak speak = speakDAO.get(id);
        if (speak == null) {
            return;
        }
        speak.setChecked(checked);
        speakDAO.persist(speak);
    }

    @Override
    public void interactionBackRatio(Integer id) {
        interactionDAO.interactionBackRatio(id);
    }

	@Override
	public void deleteInteraction(List<Integer> ids) {
		for (Integer id : ids){
			interactionDAO.removeByPK(id);
		}
	}

	@Override
	public void deleteSpeak(int[] ids) {
		for (int id : ids){
			speakDAO.removeByPK(id);
		}
	}
}
