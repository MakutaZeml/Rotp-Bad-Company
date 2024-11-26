package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import com.zeml.rotp_zbc.init.InitStands;
import com.zeml.rotp_zbc.init.InitStatusEffect;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrderToSit extends StandAction {
    public OrderToSit(StandAction.Builder builder){
        super(builder);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        int type = ((BadCompanyStandType<?>) power.getType()).getUnitType(power);
        if(type == 1){
            return ((BadCompanyStandType<?>) power.getType()).getSoldierStay(power)? InitStands.ORDER_UNSIT.get() : super.replaceAction(power, target);
        } else if (type == 2) {
            return ((BadCompanyStandType<?>) power.getType()).getTankStay(power)? InitStands.ORDER_UNSIT.get() : super.replaceAction(power, target);
        }else if(type ==3){
            return ((BadCompanyStandType<?>) power.getType()).getCopterStay(power)? InitStands.ORDER_UNSIT.get() : super.replaceAction(power, target);
        }
        return ((BadCompanyStandType<?>) power.getType()).getSoldierStay(power) && ((BadCompanyStandType<?>) power.getType()).getTankStay(power)
                &&((BadCompanyStandType<?>) power.getType()).getCopterStay(power)? InitStands.ORDER_UNSIT.get(): super.replaceAction(power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            LazyOptional<LivingData> playerDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                switch (playerData.getUnitType()){
                    case 1:  playerData.setSoldierStay(true);
                        break;
                    case 2: playerData.setTankStay(true);
                        break;
                    case 3: playerData.setCopterStay(true);
                        break;
                    default:
                        playerData.setSoldierStay(true);
                        playerData.setTankStay(true);
                        playerData.setCopterStay(true);
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
        return new StandEntityAction[] {InitStands.ORDER_UNSIT.get()};
    }
}
