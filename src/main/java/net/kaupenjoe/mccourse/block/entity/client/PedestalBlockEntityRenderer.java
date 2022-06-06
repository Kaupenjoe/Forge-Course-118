package net.kaupenjoe.mccourse.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.kaupenjoe.mccourse.block.entity.PedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PedestalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = pBlockEntity.getInventory().getStackInSlot(0);
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.925f + 0.05 * Math.cos(2 * pBlockEntity.getRotation() * Math.PI / 180), 0.5f);
        pPoseStack.scale(0.25f, 0.25f, 0.25f);
        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(pBlockEntity.getRotation()));
        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.NONE, pPackedOverlay, pPackedLight, pPoseStack, pBufferSource, 0);
        pPoseStack.popPose();
        pBlockEntity.addRotation(-1f);
    }
}
