package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.init.InitStands;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class BreakOrderToSit extends StandEntityAction {
    public BreakOrderToSit(StandEntityAction.Builder builder){
        super(builder);
    }




    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            LazyOptional<LivingData> playerDataOptional = userPower.getUser().getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                switch (playerData.getUnitType()){
                    case 1:  playerData.setSoldierStay(false);
                        break;
                    case 2: playerData.setTankStay(false);
                        break;
                    case 3: playerData.setCopterStay(false);
                        break;
                    default:
                        playerData.setSoldierStay(false);
                        playerData.setTankStay(false);
                        playerData.setCopterStay(false);
                        break;
                }
            });
        }
    }



    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        TranslationTextComponent name = new TranslationTextComponent("entity.rotp_zbc.all");
        int type = ((BadCompanyStandType<?>) power.getType()).getUnitType(power);
        switch (type){
            case 1: name = new TranslationTextComponent("entity.rotp_zbc.bad_soldier");
                break;
            case 2: name = new TranslationTextComponent("entity.rotp_zbc.bad_tank");
                break;
            case 3: name = new TranslationTextComponent("entity.rotp_zbc.bad_helicopter");
                break;
        }
        return new TranslationTextComponent(key,name);
    }



}
