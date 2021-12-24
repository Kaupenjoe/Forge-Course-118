package net.kaupenjoe.mccourse.world.feature;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final PlacedFeature CHERRY_BLOSSOM_PLACED =
            PlacementUtils.register("cherry_blossom_placed",
            ModConfiguredFeature.CHERRY_BLOSSOM_TREE_CHECKED.placed(VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(1, 0.1f, 2))));



}
