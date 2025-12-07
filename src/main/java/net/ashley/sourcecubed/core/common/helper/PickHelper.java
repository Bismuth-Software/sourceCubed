package net.ashley.sourcecubed.core.common.helper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PickHelper {
    public static HitResult pick(Entity entity, double pickRange, float partialTick) {
        Vec3 eyePosition = entity.getEyePosition(partialTick);
        Vec3 viewVector = entity.getViewVector(partialTick);
        Vec3 vec3 = eyePosition.add(viewVector.x * pickRange, viewVector.y * pickRange, viewVector.z * pickRange);
        return entity.level().clip(new ClipContext(eyePosition, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
    }
}