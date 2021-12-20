package net.kaupenjoe.mccourse.painting;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<Motive> PAINTING_MOTIVES =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, MCCourseMod.MOD_ID);

    public static final RegistryObject<Motive> PLANT =
            PAINTING_MOTIVES.register("plant", () -> new Motive(16, 16));
    public static final RegistryObject<Motive> WANDERER =
            PAINTING_MOTIVES.register("wanderer", () -> new Motive(16, 32));
    public static final RegistryObject<Motive> SUNSET =
            PAINTING_MOTIVES.register("sunset", () -> new Motive(32, 16));

    public static void register(IEventBus eventBus) {
        PAINTING_MOTIVES.register(eventBus);
    }
}
