package net.kaupenjoe.mccourse.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class KaupenTitleScreen extends TitleScreen {
    private static final ResourceLocation SPLASH =
            new ResourceLocation(MCCourseMod.MOD_ID, "textures/gui/background/kaupenmenu.jpg");

    private static final ResourceLocation MINECRAFT_LOGO =
            new ResourceLocation("textures/gui/title/minecraft.png");

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        int width = this.width;
        int height = this.height;

        drawCustomTitleScreen(poseStack, width, height);
        drawMinecraftLogo(poseStack);

        ForgeHooksClient.renderMainMenu(this, poseStack, this.getMinecraft().font, width, height, -1);
        drawString(poseStack, this.getMinecraft().font, "Copyright Mojang AB. Do not distribute!",
                width - this.font.width("Copyright Mojang AB. Do not distribute!") - 2,
                height - 10, 0xFFFFFFFF);
        for (Widget widget : this.renderables) {
            widget.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    private void drawMinecraftLogo(@NotNull PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MINECRAFT_LOGO);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0f);

        this.blitOutlineBlack(this.width / 2 - 137, 30, (p_169447_, p_169448_) -> {
            this.blit(poseStack, p_169447_ + 0, p_169448_, 0, 0, 99, 44);
            this.blit(poseStack, p_169447_ + 99, p_169448_, 129, 0, 27, 44);
            this.blit(poseStack, p_169447_ + 99 + 26, p_169448_, 126, 0, 3, 44);
            this.blit(poseStack, p_169447_ + 99 + 26 + 3, p_169448_, 99, 0, 26, 44);
            this.blit(poseStack, p_169447_ + 155, p_169448_, 0, 45, 155, 44);
        });
    }

    private void drawCustomTitleScreen(@NotNull PoseStack poseStack, int width, int height) {
        RenderSystem.enableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SPLASH);
        blit(poseStack, 0, 0, 0, 0, this.width, this.height, width, height);
    }
}

