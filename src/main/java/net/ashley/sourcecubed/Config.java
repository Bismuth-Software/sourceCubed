package net.ashley.sourcecubed;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@Mod(value = SourceCubed.ID) @EventBusSubscriber(modid = SourceCubed.ID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static void setEnabled(boolean enabled) {
        ENABLED.set(enabled);
        ENABLED.save();
    }
    public static boolean isEnabled() {
        return ENABLED.get();
    }

    //Booleans
    private static final ModConfigSpec.BooleanValue ENABLED = BUILDER
            .comment("Turns off/on the custom movement for the client (essentially the saved value of the menu toggle button)")
            .define("enabled", true);

    private static final ModConfigSpec.BooleanValue UNCAPPED_BUNNYHOP_ENABLED = BUILDER
            .comment("If enabled, the soft and hard caps will not be applied at all")
            .define("uncappedBunnyhopEnabled", true);
    public static boolean uncappedBunnyhopEnabled;

    private static final ModConfigSpec.BooleanValue LONG_JUMPING_ENABLED = BUILDER
            .comment("If enabled, holding sneak while jumping will lunge you forward based on momentum. Has lower gravity in the End dimension.")
            .define("longJumpEnabled", true);
    public static boolean longJumpingEnabled;

    private static final ModConfigSpec.BooleanValue TRIMPING_ENABLED = BUILDER
            .comment("If enabled, holding sneak while jumping will convert your horizontal speed into vertical speed")
            .define("trimpEnabled", true);
    public static boolean trimpingEnabled;

    private static final ModConfigSpec.BooleanValue SHARKING_ENABLED = BUILDER
            .comment("If enabled, holding jump while swimming at the surface of water allows you to glide")
            .define("sharkingEnabled", true);
    public static boolean sharkingEnabled;

    //Doubles
    private static final ModConfigSpec.DoubleValue ACCELERATE = BUILDER
            .comment("A higher value means you accelerate faster on the ground")
            .defineInRange("groundAccelerate", 10D, 0D, Double.MAX_VALUE);
    public static double accelerate;

    private static final ModConfigSpec.DoubleValue AIR_ACCELERATE = BUILDER
            .comment("A higher value means you can turn more sharply in the air without losing speed")
            .defineInRange("airAccelerate", 15.0D, 0D, Double.MAX_VALUE);
    public static double airAccelerate;

    private static final ModConfigSpec.DoubleValue MAX_AIR_ACCEL_PER_TICK = BUILDER
            .comment("A higher value means faster air acceleration")
            .defineInRange("maxAirAccelerationPerTick", 0.05D, 0D, Double.MAX_VALUE);
    public static double maxAirAccelPerTick;


    private static final ModConfigSpec.DoubleValue HARD_CAP = BUILDER
            .comment("See 'Uncapped Bunnyhopping'; jumping while above the hard cap speed (moveSpeed*hardCapThreshold) sets your speed to the hard cap speed")
            .defineInRange("hardCapThreshold", 2D, 0D, Double.MAX_VALUE);
    public static float hardCap;

    private static final ModConfigSpec.DoubleValue SOFT_CAP = BUILDER
            .comment("See 'Uncapped Bunnyhopping' and 'Soft Cap Degen'; soft cap speed = (moveSpeed*softCapThreshold)")
            .defineInRange("softCapThreshold", 1.4D, 0D, Double.MAX_VALUE);
    public static float softCap;

    private static final ModConfigSpec.DoubleValue SOFT_CAP_DEGEN = BUILDER
            .comment("The modifier used to calculate speed lost when jumping above the soft cap. Only matters if softCapThreshold is enabled.")
            .defineInRange("softCapDegen", 0.65D, 0D, Double.MAX_VALUE);
    public static float softCapDegen;


    private static final ModConfigSpec.DoubleValue LONG_JUMP_FORWARD_STRENGTH = BUILDER
            .comment("How strong the base forward momentum boost from the long jump is. Note that part of your speed gets added to the final momentum boost")
            .defineInRange("longJumpForwardStrength", 1.0D, 0D, Double.MAX_VALUE);
    public static double longJumpForwardStrength;

    private static final ModConfigSpec.DoubleValue LONG_JUMP_VERTICAL_STRENGTH = BUILDER
            .comment("How strong the vertical momentum boost from the long jump is")
            .defineInRange("normalLongJumpVerticalStrength", 0.45D, 0D, Double.MAX_VALUE);
    public static double longJumpVerticalStrength;

    private static final ModConfigSpec.DoubleValue END_LONG_JUMP_VERTICAL_STRENGTH = BUILDER
            .comment("How strong the vertical momentum boost from the long jump is in the End dimension")
            .defineInRange("endLongJumpVerticalStrength", 0.55D, 0D, Double.MAX_VALUE);
    public static double endLongJumpVerticalStrength;

    private static final ModConfigSpec.LongValue LONG_JUMP_COOLDOWN = BUILDER
            .comment("The cooldown for the long jump ability, in milliseconds")
            .defineInRange("normalLongJumpCooldown", 850, 0, Long.MAX_VALUE);
    public static long longJumpCooldown;


    private static final ModConfigSpec.DoubleValue TRIMP_MULTIPLIER = BUILDER
            .comment("A lower value means less horizontal speed converted to vertical speed and vice versa")
            .defineInRange("trimpMultiplier", 1.4D, 0D, Double.MAX_VALUE);
    public static double trimpMult;


    private static final ModConfigSpec.DoubleValue SHARKING_WATER_FRICTION = BUILDER
            .comment("Amount of friction while sharking (between 0 and 1)")
            .defineInRange("sharkingWaterFriction", 0.7D, 0D, 1D);
    public static double sharkingWaterFriction;

    private static final ModConfigSpec.DoubleValue SHARKING_SURFACE_TENSION = BUILDER
            .comment("Amount of downward momentum you lose while entering water. A higher value means that you are able to shark after hitting the water from higher up")
            .defineInRange("sharkingSurfaceTension", 0.2D, 0D, Double.MAX_VALUE);
    public static double sharkingSurfTension;


    private static final ModConfigSpec.DoubleValue INCREASED_FALL_DISTANCE = BUILDER
            .comment("Increases the distance needed to fall in order to take fall damage; this is a server-side setting")
            .defineInRange("fallDistanceThresholdIncrease", 1.5D, 0D, Double.MAX_VALUE);
    public static float increasedFallDistance;

    static final ModConfigSpec SPEC = BUILDER.build();
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        SourceCubed.LOGGER.debug("Loaded HalfCraft config file {}", configEvent.getConfig().getFileName());

        sharkingEnabled = SHARKING_ENABLED.get();
        longJumpingEnabled = LONG_JUMPING_ENABLED.get();
        longJumpForwardStrength = LONG_JUMP_FORWARD_STRENGTH.get();
        longJumpVerticalStrength = LONG_JUMP_VERTICAL_STRENGTH.get();
        endLongJumpVerticalStrength = END_LONG_JUMP_VERTICAL_STRENGTH.get();
        longJumpCooldown = LONG_JUMP_COOLDOWN.get();
        trimpingEnabled = TRIMPING_ENABLED.get();
        uncappedBunnyhopEnabled = UNCAPPED_BUNNYHOP_ENABLED.get();
        accelerate = ACCELERATE.get();
        airAccelerate = AIR_ACCELERATE.get();
        sharkingSurfTension = 1.0D - SHARKING_SURFACE_TENSION.get();
        trimpMult = TRIMP_MULTIPLIER.get();
        sharkingWaterFriction = 1.0D - SHARKING_WATER_FRICTION.get() * 0.05D;
        maxAirAccelPerTick = MAX_AIR_ACCEL_PER_TICK.get();
        softCap = SOFT_CAP.get().floatValue() * 0.125F;
        hardCap = HARD_CAP.get().floatValue() * 0.125F;
        softCapDegen = SOFT_CAP_DEGEN.get().floatValue();
        increasedFallDistance = INCREASED_FALL_DISTANCE.get().floatValue();
    }
}