package net.kaupenjoe.mccourse.entity.custom;

import net.kaupenjoe.mccourse.entity.ModEntityTypes;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ModBoatEntity {
    // private static final EntityDataAccessor<Integer> DATA_ID_TYPE =
    //         SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.INT);
//
    // public ModBoatEntity(EntityType<? extends ModBoatEntity> entityType, Level level) {
    //     super(entityType, level);
    //     this.blocksBuilding = true;
    // }
//
    // public ModBoatEntity(Level worldIn, double x, double y, double z) {
    //     this(ModEntityTypes.BOAT_ENTITY.get(), worldIn);
    //     this.setPos(x, y, z);
    //     this.xo = x;
    //     this.yo = y;
    //     this.zo = z;
    // }
//
    // protected void addAdditionalSaveData(CompoundTag compound) {
    //     compound.putString("Type", this.getModBoatType().getName());
    // }
//
    // protected void readAdditionalSaveData(CompoundTag compound) {
    //     if (compound.contains("Type", 8)) {
    //         this.setBoatType(ModBoatEntity.Type.byName(compound.getString("Type")));
    //     }
    // }
//
    // @Override
    // protected void defineSynchedData() {
    //     super.defineSynchedData();
    //     this.entityData.define(DATA_ID_TYPE, Type.CHERRY_BLOSSOM.ordinal());
    // }
//
    // @Override
    // public Item getDropItem() {
    //     switch (this.getModBoatType()) {
    //         case CHERRY_BLOSSOM:
    //             return ModItems.CHERRY_BLOSSOM_BOAT.get();
    //         default:
    //             return Items.OAK_BOAT;
    //     }
    // }
//
    // public void setBoatType(ModBoatEntity.Type boatType) {
    //     this.entityData.set(DATA_ID_TYPE, boatType.ordinal());
    // }
//
    // public ModBoatEntity.Type getModBoatType() {
    //     return ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    // }
//
    // public enum Type {
    //     CHERRY_BLOSSOM("cherry_blossom");
//
    //     private final String name;
    //     Type(String name) {
    //         this.name = name;
    //     }
//
    //     public String getName() {
    //         return this.name;
    //     }
//
    //     public static ModBoatEntity.Type byId(int id) {
    //         ModBoatEntity.Type[] types = values();
    //         if (id < 0 || id >= types.length) {
    //             id = 0;
    //         }
//
    //         return types[id];
    //     }
//
    //     public static ModBoatEntity.Type byName(String nameIn) {
    //         ModBoatEntity.Type[] types = values();
//
    //         for (int i = 0; i < types.length; ++i) {
    //             if (types[i].getName().equals(nameIn)) {
    //                 return types[i];
    //             }
    //         }
//
    //         return types[0];
    //     }
    // }
}