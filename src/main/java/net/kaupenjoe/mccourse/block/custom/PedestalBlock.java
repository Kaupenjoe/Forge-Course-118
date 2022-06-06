package net.kaupenjoe.mccourse.block.custom;

import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {
    public PedestalBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntities.PEDESTAL.get().create(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PedestalBlockEntity) {
                ((PedestalBlockEntity) blockEntity).drops();
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult result) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof PedestalBlockEntity pedestal) {
            ItemStackHandler pedestalInventory = pedestal.getInventory();
            ItemStack itemInPedestal = pedestalInventory.getStackInSlot(0);

            if (itemInPedestal.isEmpty()) {
                ItemStack heldItem = player.getItemInHand(hand);

                if (!heldItem.isEmpty()) {
                    setPedestalToPlayerItem(player.getItemInHand(hand).copy(), hand, pedestalInventory);
                    player.getItemInHand(hand).shrink(1);

                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            else {
                spawnItemFromPedestal(level, player, itemInPedestal);
                deletePedestalItem(pedestalInventory);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private void spawnItemFromPedestal(Level level, Player player, ItemStack itemInPedestal) {
        ItemEntity item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), itemInPedestal.copy());
        item.setNoPickUpDelay();
        level.addFreshEntity(item);
    }

    private void setPedestalToPlayerItem(ItemStack stack, InteractionHand hand, ItemStackHandler pedestalInventory) {
        stack.setCount(1);
        pedestalInventory.setStackInSlot(0, stack);
    }

    private void deletePedestalItem(ItemStackHandler pedestalInventory) {
        setPedestalToPlayerItem(ItemStack.EMPTY, null, pedestalInventory);
    }
}
