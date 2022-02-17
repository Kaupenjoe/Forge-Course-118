package net.kaupenjoe.mccourse.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MCCourseCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> COBALT_ORE_VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> COBALT_ORE_VEIN_SIZE;

    static {
        BUILDER.push("Configs for MCCourseMod");

        COBALT_ORE_VEINS_PER_CHUNK = BUILDER.comment("How many Cobalt Ore Veins spawn per chunk!")
                .define("Veins Per Chunk", 7);
        COBALT_ORE_VEIN_SIZE = BUILDER.comment("How many Cobalt Ore Blocks spawn in one Vein!")
                .define("Vein Size", 9);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
