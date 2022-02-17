package net.kaupenjoe.mccourse.world.feature;

import net.kaupenjoe.mccourse.config.MCCourseCommonConfigs;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {
    public static final PlacedFeature CHERRY_BLOSSOM_PLACED =
            PlacementUtils.register("cherry_blossom_placed",
            ModConfiguredFeature.CHERRY_BLOSSOM_TREE_CHECKED.placed(VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(1, 0.1f, 2))));

    public static final PlacedFeature PINK_ROSE_PLACED = PlacementUtils.register("pink_rose_placed",
            ModConfiguredFeature.PINK_ROSE.placed(RarityFilter.onAverageOnceEvery(16),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

    public static final PlacedFeature COBALT_ORE_PLACED = PlacementUtils.register("cobalt_ore_placed",
            ModConfiguredFeature.COBALT_ORE.placed(ModOrePlacement
                    .commonOrePlacement(MCCourseCommonConfigs.COBALT_ORE_VEINS_PER_CHUNK.get(), // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

}
