package com.zeml.rotp_zbc.init;

import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.action.*;
import com.zeml.rotp_zbc.entity.stand.stands.BadCompanyStandEntity;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import com.zeml.rotp_zbc.power.impl.stand.type.BadCompanyStandType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpBadCompanyAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpBadCompanyAddon.MOD_ID);

    // ======================================== Bad Company ========================================

    public static final RegistryObject<StandAction> SELECT_UNITS = ACTIONS.register("units",
            ()->new SelectTroopType(new StandAction.Builder().resolveLevelToUnlock(2)));

    public static final RegistryObject<StandAction> DONT_SHOOT = ACTIONS.register("shoot",
            ()->new DontShootOrder(new StandAction.Builder().resolveLevelToUnlock(3)));

    public static final RegistryObject<StandAction> SELECT_FORMATION = ACTIONS.register("random",
            ()->new SelectFormation(new StandAction.Builder().resolveLevelToUnlock(1)));
    public static final RegistryObject<StandAction> ORDER_SIT = ACTIONS.register("order_wait",
            ()->new OrderToSit(new StandAction.Builder()));
    public static final RegistryObject<StandEntityAction> ORDER_UNSIT = ACTIONS.register("order_no_wait",
            ()->new BreakOrderToSit(new StandEntityAction.Builder()));
    public static final RegistryObject<StandEntityAction> PUT_MINES = ACTIONS.register("put_mines",
            ()->new PutMine(new StandEntityAction.Builder().resolveLevelToUnlock(1)));

    public static final RegistryObject<StandEntityAction> EXPLODE_MINE = ACTIONS.register("explode_mines",
            ()->new ExplodeMines(new StandEntityAction.Builder().resolveLevelToUnlock(1).shiftVariationOf(PUT_MINES)));

    public static final RegistryObject<StandAction> SET_TARGET = ACTIONS.register("target",
            ()->new SetTarget(new StandAction.Builder().resolveLevelToUnlock(3)));

    public static final RegistryObject<StandAction> MISSILE_MODE = ACTIONS.register("missile",
            ()->new MissilesMode(new StandAction.Builder().resolveLevelToUnlock(2)));

    public static final RegistryObject<StandEntityAction> GO_TO_BLOCK = ACTIONS.register("go_block",
            ()->new GoToBlockAction(new StandEntityAction.Builder().resolveLevelToUnlock(4)));

    public static final RegistryObject<StandAction> STAY_CLOSE = ACTIONS.register("close",
            ()->new StayCloseAction(new StandAction.Builder().resolveLevelToUnlock(1)));

    public static final RegistryObject<StandAction> STAYNT_CLOSE = ACTIONS.register("closent",
            ()->new StayntCloseAction(new StandAction.Builder()));
    public static final EntityStandRegistryObject<BadCompanyStandType<StandStats>, StandEntityType<BadCompanyStandEntity>> STAND_BAD_COMPANY =
            new EntityStandRegistryObject<>("bad_company",
                    STANDS,
                    () -> new BadCompanyStandType.Builder<StandStats>()
                            .color(0x237841)
                            .storyPartName(ModStandsInit.PART_4_NAME)
                            .leftClickHotbar(
                                    PUT_MINES.get(),
                                    DONT_SHOOT.get(),
                                    SET_TARGET.get()
                                    )
                            .rightClickHotbar(
                                    SELECT_UNITS.get(),
                                    SELECT_FORMATION.get(),
                                    STAY_CLOSE.get(),
                                    ORDER_SIT.get(),
                                    MISSILE_MODE.get(),
                                    GO_TO_BLOCK.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(4)
                                    .power(13.0)
                                    .speed(12.0)
                                    .range(10.0)
                                    .durability(13.0)
                                    .precision(10.0)
                                    .randomWeight(2)
                            )
                            .addOst(InitSounds.BAD_COMPANY_OST)
                            .disableManualControl().disableStandLeap()
                            .addSummonShout(InitSounds.BAD_COMPANY_USER)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<BadCompanyStandEntity>(BadCompanyStandEntity::new, 0.065F, 0.195F)
                            .summonSound(InitSounds.BAD_COMPANY_SUMMON)
                            .unsummonSound(InitSounds.BAD_COMPANY_UNSUMMON))
                    .withDefaultStandAttributes();
}
