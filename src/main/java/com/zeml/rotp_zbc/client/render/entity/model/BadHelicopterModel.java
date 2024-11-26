package com.zeml.rotp_zbc.client.render.entity.model;

import com.github.standobyte.jojo.capability.world.TimeStopHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zbc.entity.BadHelicopterEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BadHelicopterModel extends EntityModel<BadHelicopterEntity> {
    private final ModelRenderer body;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer rightDoor;
    private final ModelRenderer leftDoor;
    private final ModelRenderer turn;
    private final ModelRenderer turn3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer turn4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer turn2;
    private final ModelRenderer cube_r6;
    private final ModelRenderer backturn;
    private final ModelRenderer cube_r7;
    private final ModelRenderer cube_r8;
    private final ModelRenderer misile;
    private final ModelRenderer misile2;
    private final ModelRenderer misile3;
    private final ModelRenderer misile4;
    private final ModelRenderer misile5;
    private final ModelRenderer misile6;

    public BadHelicopterModel(){
        texWidth = 512;
        texHeight = 512;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);
        body.texOffs(78, 163).addBox(-14.0F, -32.0F, -9.0F, 28.0F, 32.0F, 11.0F, 0.0F, false);
        body.texOffs(0, 150).addBox(-14.0F, -32.0F, 32.0F, 28.0F, 32.0F, 11.0F, 0.0F, false);
        body.texOffs(102, 120).addBox(-12.0F, -14.0F, -38.0F, 24.0F, 14.0F, 29.0F, 0.0F, false);
        body.texOffs(206, 0).addBox(-9.0F, -14.0F, -55.0F, 18.0F, 14.0F, 17.0F, 0.0F, false);
        body.texOffs(159, 48).addBox(-9.0F, -34.0F, -12.0F, 18.0F, 5.0F, 25.0F, 0.0F, false);
        body.texOffs(84, 84).addBox(-14.0F, -6.0F, 2.0F, 28.0F, 6.0F, 30.0F, 0.0F, false);
        body.texOffs(73, 48).addBox(-14.0F, -32.0F, 2.0F, 28.0F, 6.0F, 30.0F, 0.0F, false);
        body.texOffs(202, 200).addBox(-10.0F, -19.0F, 43.0F, 20.0F, 19.0F, 14.0F, 0.0F, false);
        body.texOffs(0, 48).addBox(-8.0F, -19.0F, 57.0F, 16.0F, 14.0F, 41.0F, 0.0F, false);
        body.texOffs(130, 200).addBox(-5.0F, -19.0F, 98.0F, 10.0F, 10.0F, 26.0F, 0.0F, false);
        body.texOffs(0, 48).addBox(-1.0F, -58.0F, 122.0F, 2.0F, 19.0F, 18.0F, 0.0F, false);
        body.texOffs(211, 78).addBox(-38.0F, -12.0F, -28.0F, 26.0F, 2.0F, 15.0F, 0.0F, false);
        body.texOffs(208, 140).addBox(12.0F, -12.0F, -28.0F, 26.0F, 2.0F, 15.0F, 0.0F, false);
        body.texOffs(32, 206).addBox(0.0F, -22.0F, 115.0F, 26.0F, 2.0F, 15.0F, 0.0F, false);
        body.texOffs(206, 31).addBox(-26.0F, -22.0F, 115.0F, 26.0F, 2.0F, 15.0F, 0.0F, false);
        body.texOffs(0, 46).addBox(-1.0F, -46.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
        body.texOffs(211, 95).addBox(-7.0F, -48.0F, -7.0F, 14.0F, 2.0F, 14.0F, 0.0F, false);
        body.texOffs(0, 64).addBox(1.0F, -49.0F, 127.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, -13.0F, 119.0F);
        body.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.5236F, 0.0F, 0.0F);
        cube_r1.texOffs(96, 218).addBox(-1.0F, -34.0F, -13.0F, 2.0F, 35.0F, 18.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, -18.0F, -28.0F);
        body.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.7418F, 0.0F, 0.0F);
        cube_r2.texOffs(156, 163).addBox(-8.0F, -13.0F, -23.0F, 16.0F, 13.0F, 24.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, -15.0F, -10.0F);
        body.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0436F, 0.0F, 0.0F);
        cube_r3.texOffs(0, 103).addBox(-11.0F, -15.0F, -28.0F, 22.0F, 18.0F, 29.0F, 0.0F, false);

        rightDoor = new ModelRenderer(this);
        rightDoor.setPos(14.0F, -20.0F, 3.0F);
        body.addChild(rightDoor);
        rightDoor.texOffs(0, 193).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 20.0F, 30.0F, 0.0F, false);

        leftDoor = new ModelRenderer(this);
        leftDoor.setPos(-13.0F, -20.0F, 3.0F);
        body.addChild(leftDoor);
        leftDoor.texOffs(179, 90).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 20.0F, 30.0F, 0.0F, false);

        turn = new ModelRenderer(this);
        turn.setPos(0.0F, -39.0F, 0.0F);
        body.addChild(turn);
        turn.texOffs(0, 42).addBox(-50.0F, -2.0F, -3.0F, 100.0F, 0.0F, 6.0F, 0.0F, false);

        turn3 = new ModelRenderer(this);
        turn3.setPos(0.0F, 0.0F, 0.0F);
        turn.addChild(turn3);
        setRotationAngle(turn3, 0.0F, 0.1745F, 0.0F);
        turn3.texOffs(0, 18).addBox(-50.0F, -2.0F, -3.0F, 100.0F, 0.0F, 6.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, 0.0F, 0.0F);
        turn3.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, -1.5708F, 0.0F);
        cube_r4.texOffs(0, 12).addBox(-50.0F, -2.0F, -3.0F, 100.0F, 0.0F, 6.0F, 0.0F, false);

        turn4 = new ModelRenderer(this);
        turn4.setPos(0.0F, 0.0F, 0.0F);
        turn3.addChild(turn4);
        setRotationAngle(turn4, 0.0F, -0.1745F, 0.0F);


        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.0F, 0.0F, 0.0F);
        turn4.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, -1.5708F, 0.0F);
        cube_r5.texOffs(0, 0).addBox(-50.0F, -2.0F, -3.0F, 100.0F, 0.0F, 6.0F, 0.0F, false);

        turn2 = new ModelRenderer(this);
        turn2.setPos(0.0F, 0.0F, 0.0F);
        turn.addChild(turn2);
        setRotationAngle(turn2, 0.0F, -0.1745F, 0.0F);
        turn2.texOffs(0, 30).addBox(-50.0F, -2.0F, -3.0F, 100.0F, 0.0F, 6.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(0.0F, 0.0F, 0.0F);
        turn2.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, -1.5708F, 0.0F);
        cube_r6.texOffs(0, 24).addBox(-50.0F, -2.0F, -3.0F, 100.0F, 0.0F, 6.0F, 0.0F, false);

        backturn = new ModelRenderer(this);
        backturn.setPos(2.0F, -48.0F, 128.0F);
        body.addChild(backturn);
        backturn.texOffs(73, 74).addBox(5.0F, -2.0F, -1.0F, 0.0F, 3.0F, 10.0F, 0.0F, false);

        cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(4.0F, 0.0F, 0.0F);
        backturn.addChild(cube_r7);
        setRotationAngle(cube_r7, 2.0944F, 0.0F, 0.0F);
        cube_r7.texOffs(73, 65).addBox(1.0F, -2.0F, -1.0F, 0.0F, 3.0F, 10.0F, 0.0F, false);

        cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(4.0F, 0.0F, 0.0F);
        backturn.addChild(cube_r8);
        setRotationAngle(cube_r8, -2.0944F, 0.0F, 0.0F);
        cube_r8.texOffs(73, 62).addBox(1.0F, -2.0F, -1.0F, 0.0F, 3.0F, 10.0F, 0.0F, false);

        misile = new ModelRenderer(this);
        misile.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(misile);
        misile.texOffs(156, 174).addBox(32.0F, -10.0F, -25.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
        misile.texOffs(97, 120).addBox(32.5F, -9.5F, -25.5F, 3.0F, 3.0F, 9.0F, 0.0F, false);
        misile.texOffs(88, 60).addBox(32.0F, -10.0F, -17.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

        misile2 = new ModelRenderer(this);
        misile2.setPos(-5.0F, 0.0F, 0.0F);
        body.addChild(misile2);
        misile2.texOffs(170, 95).addBox(32.0F, -10.0F, -25.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
        misile2.texOffs(73, 120).addBox(32.5F, -9.5F, -25.5F, 3.0F, 3.0F, 9.0F, 0.0F, false);
        misile2.texOffs(88, 48).addBox(32.0F, -10.0F, -17.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

        misile3 = new ModelRenderer(this);
        misile3.setPos(-10.0F, 0.0F, 0.0F);
        body.addChild(misile3);
        misile3.texOffs(170, 84).addBox(32.0F, -10.0F, -25.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
        misile3.texOffs(0, 115).addBox(32.5F, -9.5F, -25.5F, 3.0F, 3.0F, 9.0F, 0.0F, false);
        misile3.texOffs(22, 60).addBox(32.0F, -10.0F, -17.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

        misile4 = new ModelRenderer(this);
        misile4.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(misile4);
        misile4.texOffs(159, 59).addBox(-37.0F, -10.0F, -25.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
        misile4.texOffs(0, 103).addBox(-36.5F, -9.5F, -25.5F, 3.0F, 3.0F, 9.0F, 0.0F, false);
        misile4.texOffs(6, 58).addBox(-37.0F, -10.0F, -17.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

        misile5 = new ModelRenderer(this);
        misile5.setPos(5.0F, 0.0F, 0.0F);
        body.addChild(misile5);
        misile5.texOffs(67, 150).addBox(-37.0F, -10.0F, -25.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
        misile5.texOffs(73, 60).addBox(-36.5F, -9.5F, -25.5F, 3.0F, 3.0F, 9.0F, 0.0F, false);
        misile5.texOffs(22, 54).addBox(-37.0F, -10.0F, -17.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

        misile6 = new ModelRenderer(this);
        misile6.setPos(10.0F, 0.0F, 0.0F);
        body.addChild(misile6);
        misile6.texOffs(73, 103).addBox(-37.0F, -10.0F, -25.0F, 4.0F, 4.0F, 7.0F, 0.0F, false);
        misile6.texOffs(73, 48).addBox(-36.5F, -9.5F, -25.5F, 3.0F, 3.0F, 9.0F, 0.0F, false);
        misile6.texOffs(22, 48).addBox(-37.0F, -10.0F, -17.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
    }


    @Override
    public void setupAnim(BadHelicopterEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        this.turn.yRot = 2*ageInTicks;
        this.turn2.yRot = 2*ageInTicks;
        this.body.yRot = netHeadYaw / (180F / (float) Math.PI);


        this.misile.visible = entity.getMissiles()>5;
        this.misile2.visible = entity.getMissiles()>4;
        this.misile3.visible = entity.getMissiles()>3;
        this.misile4.visible = entity.getMissiles()>2;
        this.misile5.visible = entity.getMissiles()>1;
        this.misile6.visible = entity.getMissiles()>0;

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.translate(0,1.25,0);
        matrixStack.scale(.16666F,.16666F,.16666F);
        body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
