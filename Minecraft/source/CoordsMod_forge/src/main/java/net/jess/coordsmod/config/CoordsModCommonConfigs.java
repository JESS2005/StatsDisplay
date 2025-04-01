package net.jess.coordsmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CoordsModCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> DEFAULT_PORT;

    static {
        BUILDER.push("Stats Display configs");

        DEFAULT_PORT = BUILDER.comment("Default port used").define("Port","None");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
