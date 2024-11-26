package com.zeml.rotp_zbc.client.render.entity.model.stand;

import com.zeml.rotp_zbc.entity.stand.stands.BadCompanyStandEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import net.minecraft.util.math.RayTraceResult;

public class BadCompanyStandModel extends HumanoidStandModel<BadCompanyStandEntity> {

	public BadCompanyStandModel() {
		super();

		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;

		head.texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.3F, false);
		torso.texOffs(106, 0).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 7.0F, 3.0F, 0.1F, false);
		torso.texOffs(106, 20).addBox(-4.0F, 7.0F, 2.0F, 8.0F, 5.0F, 3.0F, 0.3F, false);

	}


	@Override
	protected ModelPose initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, 0f, -20f, 0f),
				RotationAngle.fromDegrees(leftArm,32.5f, 0f, 0f),
				RotationAngle.fromDegrees(leftForeArm,0f, 0f, 77.5f),
				RotationAngle.fromDegrees(rightArm,35f, 0f, 0f),
				RotationAngle.fromDegrees(rightForeArm,0f, 0f, -67.5f),
				RotationAngle.fromDegrees(rightLeg,0f, 0f, 2.5f)


		});
	}


}