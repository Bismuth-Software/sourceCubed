package net.ashley.sourcecubed.core.common.mixin;

import net.ashley.sourcecubed.core.common.ISourceCubedEntity;
import net.ashley.sourcecubed.core.client.SourcePlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
@Implements({@Interface(iface = ISourceCubedEntity.class, prefix = "sqe$")})
public abstract class EntityMixin implements ISourceCubedEntity {

    private int sourcecubedDisableMovementTicks;

    public int sqe$getDisabledMovementTicks_()
    {
        return sourcecubedDisableMovementTicks;
    }

    public void sqe$setDisabledMovementTicks_(int amt)
    {
        sourcecubedDisableMovementTicks = amt;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void beforeOnLivingUpdate(CallbackInfo ci) {
        if(sourcecubedDisableMovementTicks > 0)
            --sourcecubedDisableMovementTicks;
    }

    @Inject(method = "moveRelative", at = @At("HEAD"), cancellable = true)
    public void moveRelativeBase(float friction, Vec3 relative, CallbackInfo ci) {
        if(SourcePlayer.moveRelativeBase((Entity)(Object) this, this, (float) relative.x, (float) relative.y, (float) relative.z, friction))
            ci.cancel();
    }

    @Inject(method = "onInsideBubbleColumn", at = @At("HEAD"))
    public void onInsideBubbleColumn(boolean p_20322_, CallbackInfo ci) {
        sourcecubedDisableMovementTicks = 2;
    }
}