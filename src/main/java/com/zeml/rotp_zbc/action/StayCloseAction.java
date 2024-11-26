package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.init.InitStands;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class StayCloseAction extends StandAction {

    public StayCloseAction(StandAction.Builder builder){
        super(builder);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        int type = ((BadCompanyStandType<?>) power.getType()).getUnitType(power);
        if(type == 1){
            return ((BadCompanyStandType<?>) power.getType()).getSoldierClose(power)? InitStands.STAYNT_CLOSE.get() : super.replaceAction(power, target);
        } else if (type == 2) {
            return ((BadCompanyStandType<?>) power.getType()).getTankClose(power)? InitStands.STAYNT_CLOSE.get() : super.replaceAction(power, target);
        }else if(type ==3){
            return ((BadCompanyStandType<?>) power.getType()).getCopterClose(power)? InitStands.STAYNT_CLOSE.get() : super.replaceAction(power, target);
        }
        return ((BadCompanyStandType<?>) power.getType()).getSoldierClose(power) && ((BadCompanyStandType<?>) power.getType()).getTankClose(power)
                &&((BadCompanyStandType<?>) power.getType()).getCopterClose(power)? InitStands.STAYNT_CLOSE.get(): super.replaceAction(power, target);
    }


    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            LazyOptional<LivingData> livingDataLazyOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            livingDataLazyOptional.ifPresent(livingData -> {
                int type = livingData.getUnitType();
                switch (type){
                    case 1: livingData.setSoldierClose(true);
                        break;
                    case 2: livingData.setTankClose(true);
                        break;
                    case 3: livingData.setCopterClose(true);
                        break;
                    default:
                        livingData.setSoldierClose(true);
                        livingData.setTankClose(true);
                        livingData.setCopterClose(true);
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

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] {InitStands.STAYNT_CLOSE.get()};
    }
}
