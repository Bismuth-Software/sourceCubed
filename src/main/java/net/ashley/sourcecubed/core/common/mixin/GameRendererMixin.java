package net.ashley.sourcecubed.core.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.ashley.sourcecubed.core.common.helper.PickHelper;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
abstract class GameRendererMixin {
    @ModifyVariable(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;", at = @At(value = "STORE"))
    private HitResult pick$0(HitResult hitResult, Entity entity, double blockInteractionRange, double entityInteractionRange, float partialTick, @Share("originalHitResult") LocalRef<HitResult> originalHitResult) {
        double range = Math.max(blockInteractionRange, entityInteractionRange);
        HitResult newHitResult = PickHelper.pick(entity, range, partialTick);
        Vec3 eyepos = entity.getEyePosition(partialTick);
        if (newHitResult.getLocation().distanceToSqr(eyepos) > hitResult.getLocation().distanceToSqr(eyepos)) {
            originalHitResult.set(hitResult);
            return newHitResult;
        } else {
            return hitResult;
        }
    }

    @ModifyReturnValue(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;", at = @At("TAIL"))
    private HitResult pick$1(HitResult hitResult, Entity entity, double blockInteractionRange, double entityInteractionRange, float partialTick, @Share("originalHitResult") LocalRef<HitResult> originalHitResult) {
        if (originalHitResult.get() != null && hitResult.getType() != HitResult.Type.ENTITY) {
            Vec3 eyePosition = entity.getEyePosition(partialTick);
            if (originalHitResult.get().getLocation().distanceToSqr(eyePosition) < hitResult.getLocation().distanceToSqr(eyePosition)) {
                return originalHitResult.get();
            }
        }
        return hitResult;
    }
}