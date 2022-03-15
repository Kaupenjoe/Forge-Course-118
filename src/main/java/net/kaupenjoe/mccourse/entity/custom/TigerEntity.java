package net.kaupenjoe.mccourse.entity.custom;

import net.kaupenjoe.mccourse.entity.ModEntityTypes;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Predicate;

public class TigerEntity extends TamableAnimal implements PlayerRideable, IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT
                || entitytype == EntityType.COW || entitytype == EntityType.CHICKEN || entitytype == EntityType.PIG ||
                entitytype == ModEntityTypes.RACCOON.get();
    };

    public TigerEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
    }

    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(TigerEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob p_146744_) {
        return ModEntityTypes.TIGER.get().create(serverLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == Items.BEEF;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<TigerEntity>
                (this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tiger.walk", true));
            return PlayState.CONTINUE;
        }

        if (this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tiger.sitting", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tiger.idle", true));

        return PlayState.CONTINUE;
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this,0,1));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(ModItems.TURNIP.get()), false));
        this.targetSelector.addGoal(7, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand pHand) {
        ItemStack itemstack = player.getItemInHand(pHand);
        Item item = itemstack.getItem();
        Item tameableItem = ModItems.TURNIP.get();

        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || item == tameableItem
                    && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if(player.isCrouching() && pHand == InteractionHand.MAIN_HAND) {
                    setSitting(!isSitting());
                }

                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float)item.getFoodProperties().getNutrition());
                    return InteractionResult.SUCCESS;
                }
                player.startRiding(this);
            } else if (item == tameableItem && !this.isOnFire()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();

                    this.setTarget((LivingEntity)null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }
                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(player, pHand);
        }
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double)f, pTravelVector.y, (double)f1));
                } else if (livingentity instanceof Player) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                this.calculateEntityAnimation(this, false);
                this.tryCheckInsideBlocks();
            } else {
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(pLivingEntity);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for(Pose pose : pLivingEntity.getDismountPoses()) {
                AABB axisalignedbb = pLivingEntity.getLocalBoundsForPose(pose);

                for(int[] aint1 : aint) {
                    blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
                    double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutable, d0);
                        if (DismountHelper.canDismountTo(this.level, pLivingEntity, axisalignedbb.move(vec3))) {
                            pLivingEntity.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(pLivingEntity);
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}