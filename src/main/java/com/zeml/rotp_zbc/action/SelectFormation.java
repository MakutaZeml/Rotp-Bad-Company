package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.client.ui.screen.ChangeFormationScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class SelectFormation extends StandAction {
    public SelectFormation(StandAction.Builder builder){
        super(builder);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(world.isClientSide && user == com.github.standobyte.jojo.client.ClientUtil.getClientPlayer()){
            ChangeFormationScreen.openWindowOnClick();
        }
    }
}
