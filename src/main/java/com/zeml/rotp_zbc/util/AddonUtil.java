package com.zeml.rotp_zbc.util;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.util.mc.MCUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class AddonUtil {



    @Nullable
    public static StandEntity standFromUser(@NotNull LivingEntity user){
       return user.level.getEntitiesOfClass(StandEntity.class,user.getBoundingBox().inflate(Double.MAX_VALUE),entity -> (entity.getUser() == user && entity.isAlive())).stream().findFirst().orElse(null);
    }


    @Nullable
    public static LivingEntity getLivingFromUUID(UUID uuid, Entity entity){
        return entity.level.getEntitiesOfClass(LivingEntity.class,entity.getBoundingBox().inflate(Double.MAX_VALUE),living -> (living.getUUID() == uuid && living.isAlive())).stream().findFirst().orElse(null);
    }

}
