/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.plugin.interaction;

import com.ewcms.plugin.interaction.model.Interaction;
import com.ewcms.plugin.interaction.service.InteractionServiceable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author wangwei
 */
@Service
public class InteractionFac implements InteractionFacable {

    @Autowired
    private InteractionServiceable interactionService;

    @Override
    public void interactionChecked(Integer id,Boolean checked) {
        interactionService.interactionChecked(id,checked);
    }

    @Override
    public void interactionReplay(Integer id, String replay) {
        interactionService.interactionReplay(id, replay);
    }

    @Override
    public void speakChecked(Integer id,boolean checked) {
        interactionService.speakChecked(id,checked);
    }

    @Override
    public Interaction getInteraction(Integer id) {
        return interactionService.getInteraction(id);
    }

    @Override
    public void interactionOrgan(Integer id, Integer organId, String organName) {
        interactionService.interactionOrgan(id, organId,organName);
    }

    @Override
    public void interactionBackRatio(Integer id) {
        interactionService.interactionBackRatio(id);
    }

}
