package net.ashley.sourcecubed.core.common;

public interface ISourceCubedEntity {
    int getDisabledMovementTicks_();

    void setDisabledMovementTicks_(int amt);

    default boolean shouldReturnMovement_() {
        return getDisabledMovementTicks_() > 0;
    }
}