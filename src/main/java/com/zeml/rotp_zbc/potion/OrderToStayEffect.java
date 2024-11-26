package com.zeml.rotp_zbc.potion;

import com.github.standobyte.jojo.potion.IApplicableEffect;
import com.github.standobyte.jojo.potion.ImmobilizeEffect;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import net.minecraft.entity.LivingEntity;

public class OrderToStayEffect extends ImmobilizeEffect implements IApplicableEffect {
    public OrderToStayEffect(int liquidColor) {
        super(liquidColor);
    }

    @Override
    public boolean isApplicable(LivingEntity entity) {
        return entity instanceof BadCompanyUnitEntity;
    }
}
