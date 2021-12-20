package net.kaupenjoe.mccourse.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CobaltBlasterBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CobaltBlasterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(13, 0, 0, 16, 3, 3),
            Block.box(13, 0, 13, 16, 3, 16),
            Block.box(0, 0, 13, 3, 3, 16),
            Block.box(0, 0, 0, 3, 3, 3),
            Block.box(3, 2, 4, 13, 6, 5),
            Block.box(2, 0, 1, 14, 2, 14),
            Block.box(3, 0, 15, 13, 2, 16),
            Block.box(3, 2, 5, 13, 14, 14),
            Block.box(3, 0, 14, 13, 7, 15),
            Block.box(4, 13, 7, 12, 15, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(13, 0, 13, 16, 3, 16),
            Block.box(0, 0, 13, 3, 3, 16),
            Block.box(0, 0, 0, 3, 3, 3),
            Block.box(13, 0, 0, 16, 3, 3),
            Block.box(11, 2, 3, 12, 6, 13),
            Block.box(2, 0, 2, 15, 2, 14),
            Block.box(0, 0, 3, 1, 2, 13),
            Block.box(2, 2, 3, 11, 14, 13),
            Block.box(1, 0, 3, 2, 7, 13),
            Block.box(3, 13, 4, 9, 15, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(0, 0, 13, 3, 3, 16),
            Block.box(0, 0, 0, 3, 3, 3),
            Block.box(13, 0, 0, 16, 3, 3),
            Block.box(13, 0, 13, 16, 3, 16),
            Block.box(3, 2, 11, 13, 6, 12),
            Block.box(2, 0, 2, 14, 2, 15),
            Block.box(3, 0, 0, 13, 2, 1),
            Block.box(3, 2, 2, 13, 14, 11),
            Block.box(3, 0, 1, 13, 7, 2),
            Block.box(4, 13, 3, 12, 15, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(0, 0, 0, 3, 3, 3),
            Block.box(13, 0, 0, 16, 3, 3),
            Block.box(13, 0, 13, 16, 3, 16),
            Block.box(0, 0, 13, 3, 3, 16),
            Block.box(4, 2, 3, 5, 6, 13),
            Block.box(1, 0, 2, 14, 2, 14),
            Block.box(15, 0, 3, 16, 2, 13),
            Block.box(5, 2, 3, 14, 14, 13),
            Block.box(14, 0, 3, 15, 7, 13),
            Block.box(7, 13, 4, 13, 15, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPE_N;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
