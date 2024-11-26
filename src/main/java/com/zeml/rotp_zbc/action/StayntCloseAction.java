package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class StayntCloseAction extends StandAction {
    public StayntCloseAction(StandAction.Builder builder){
        super(builder);
    }




    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            LazyOptional<LivingData> livingDataLazyOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            livingDataLazyOptional.ifPresent(livingData -> {
                int type = livingData.getUnitType();
                switch (type){
                    case 1: livingData.setSoldierClose(false);
                        break;
                    case 2: livingData.setTankClose(false);
                        break;
                    case 3: livingData.setCopterClose(false);
                        break;
                    default:
                        livingData.setSoldierClose(false);
                        livingData.setTankClose(false);
                        livingData.setCopterClose(false);
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
