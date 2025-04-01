package net.jess.coordsmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CoordsModClientConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Stats Display configs");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
