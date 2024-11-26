package com.zeml.rotp_zbc.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.entity.BadCompanyUnitEntity;
import com.zeml.rotp_zbc.entity.BadSoldierEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class BadSoldierModel<T extends BadSoldierEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    private final ModelRenderer body;
    public final ModelRenderer torso;
    private final ModelRenderer rArm;
    private final ModelRenderer lArm;
    private final ModelRenderer head;
    private final ModelRenderer rLeg;
    private final ModelRenderer lLeg;
    public float swimAmount;


    public BadSoldierModel(){
        texWidth = 64;
        texHeight = 64;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);


        torso = new ModelRenderer(this);
        torso.setPos(0.0F, -24.0F, 0.0F);
        body.addChild(torso);
        torso.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        torso.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);

        rArm = new ModelRenderer(this);
        rArm.setPos(-5.0F, 2.0F, 0.0F);
        torso.addChild(rArm);
        rArm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        rArm.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        lArm = new ModelRenderer(this);
        lArm.setPos(5.0F, 2.0F, 0.0F);
        torso.addChild(lArm);
        lArm.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        lArm.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        torso.addChild(head);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

        rLeg = new ModelRenderer(this);
        rLeg.setPos(-1.9F, -12.0F, 0.0F);
        body.addChild(rLeg);
        rLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        rLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        lLeg = new ModelRenderer(this);
        lLeg.setPos(1.9F, -12.0F, 0.0F);
        body.addChild(lLeg);
        lLeg.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        lLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
    }
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body, this.rArm, this.lArm, this.rLeg, this.lLeg, this.torso);
    }



    @Override
    public void setupAnim(BadSoldierEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rLeg.xRot = MathHelper.cos(limbSwing * 4.0F) * -1.0F * limbSwingAmount;
        this.lLeg.xRot = MathHelper.cos(limbSwing * 4.0F) * 1.0F * limbSwingAmount;
        BadSoldierEntity.ArmPose armPose = entity.getArmPose();
        switch (armPose){
            case GUN:
                this.torso.yRot =0.959931088597F;
                this.rArm.xRot = -1.36990892989F;
                this.rArm.yRot =-0.514872129338F;
                this.rArm.zRot = -0.100007366139F;
                this.lArm.xRot = -1.5271630955F;
                this.head.yRot = -0.959931088597F;
                break;
            case CLIM:
                this.rArm.xRot = -2.57436064669F+ 0.785398163397F *MathHelper.cos((float) entity.position().y*2);
                this.lArm.xRot = -2.57436064669F- 0.785398163397F*MathHelper.cos((float) entity.position().y*2);
                break;
            case PARACHUTE:
                this.rArm.xRot = -2.88381275906F;
                this.rArm.yRot = 0.0262113547065F;
                this.rArm.zRot = -0.304341788317F;
                this.lArm.xRot = -2.79920268758F;
                this.lArm.yRot = 0.0790721417616F;
                this.lArm.zRot = 0.437794898912F;
                break;
            case GUN_PARACHUTE:
                this.rArm.xRot =-1.4398966329F;
                this.rArm.yRot = 0;
                this.rArm.zRot = 0;
                this.lArm.xRot = -2.79920268758F;
                this.lArm.yRot = 0.0790721417616F;
                this.lArm.zRot = 0.437794898912F;
                break;
            default:
                this.torso.yRot =0;
                this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
                this.lArm.xRot = MathHelper.cos(limbSwing * 4*0.6662F) * limbSwingAmount;
                this.rArm.xRot = MathHelper.cos(limbSwing *4* 0.6662F + (float) Math.PI) * limbSwingAmount;
                this.rArm.yRot = 0;
                this.rArm.zRot = 0;
                this.lArm.yRot = 0;
                this.lArm.zRot = 0;
                break;
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.translate(0,1,0);
        matrixStack.scale(.3333F,.3333F,.3333F);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    private ModelRenderer getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.lArm : this.rArm;
    }

    @Override
    public void translateToHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
        this.getArm(p_225599_1_).translateAndRotate(p_225599_2_);
    }

    @Override
    public ModelRenderer getHead() {
        return this.head;
    }
}
