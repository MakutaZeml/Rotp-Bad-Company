package com.zeml.rotp_zbc.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.capability.entity.LivingData;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;


public class MissilesMode extends StandAction {

    public MissilesMode(StandAction.Builder builder){
        super(builder);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            LazyOptional<LivingData> livingDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            livingDataOptional.ifPresent(livingData -> livingData.setExplosiveMissiles(!livingData.isExplosiveMissiles()));
        }
    }


    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key) {
        boolean on = !((BadCompanyStandType<?>) power.getType()).getMissileMode(power) ;
        TranslationTextComponent mode = on?  new TranslationTextComponent("gui.rotp_zbc.activate"): new TranslationTextComponent("gui.rotp_zbc.deactivate");
        return new TranslationTextComponent(key,mode);
    }



    ResourceLocation EXPLOSION = new ResourceLocation(RotpBadCompanyAddon.MOD_ID,"textures/action/missile_de.png");


    @Override
    protected ResourceLocation getIconTexturePath(@Nullable IStandPower power) {
        return !((BadCompanyStandType<?>)power.getType()).getMissileMode(power)?super.getIconTexturePath(power): StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                .getStandSkin(power.getStandInstance().get()), EXPLOSION);
    }


}
