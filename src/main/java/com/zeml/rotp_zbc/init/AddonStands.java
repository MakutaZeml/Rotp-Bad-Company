package com.zeml.rotp_zbc.init;

import com.zeml.rotp_zbc.entity.stand.stands.BadCompanyStandEntity;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;

public class AddonStands {

    public static final EntityStandSupplier<BadCompanyStandType<StandStats>, StandEntityType<BadCompanyStandEntity>>
            BAD_COMPANY = new EntityStandSupplier<>(InitStands.STAND_BAD_COMPANY);
}