package com.zeml.rotp_zbc.client.render.entity.renderer.projectile;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.zeml.rotp_zbc.client.render.entity.model.projectile.BadBulletModel;
import com.zeml.rotp_zbc.entity.damaging.projectile.BadBulletEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BadBulletRenderer extends SimpleEntityRenderer<BadBulletEntity, BadBulletModel> {
    public BadBulletRenderer(EntityRendererManager rendererManager){
        super(rendererManager,new BadBulletModel(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/projectiles/hamon_bubble.png"));
    }
}
