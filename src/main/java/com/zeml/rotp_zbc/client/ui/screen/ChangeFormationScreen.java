package com.zeml.rotp_zbc.client.ui.screen;

import com.github.standobyte.jojo.client.InputHandler;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.zeml.rotp_zbc.RotpBadCompanyAddon;
import com.zeml.rotp_zbc.capability.entity.LivingDataProvider;
import com.zeml.rotp_zbc.network.AddonPackets;
import com.zeml.rotp_zbc.network.packets.client.PlayerFormationPacket;
import com.zeml.rotp_zbc.network.packets.client.PlayerTroopTypePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Optional;

public class ChangeFormationScreen extends Screen {
    private Optional<FormationType> previousHovered;
    private Optional<FormationType> currentlyHovered = Optional.empty();
    private static final int ALL_SLOTS_WIDTH = FormationType.values().length * 30 - 5;
    private int firstMouseX;
    private int firstMouseY;
    private boolean setFirstMousePos;
    private final List<SelectorFormationWidget> slots = Lists.newArrayList();
    private static final ITextComponent SELECT_FORMATION = new TranslationTextComponent("action.rotp_zbc.random", (new TranslationTextComponent("action.rotp_zbc.random")).withStyle(TextFormatting.WHITE));
    public static final ResourceLocation UNIT_CHANGE_MENU = new ResourceLocation(RotpBadCompanyAddon.MOD_ID, "textures/gui/troop_gui.png");
    public ChangeFormationScreen(){
        super(NarratorChatListener.NO_TITLE);
        this.previousHovered = getPreviousHovered();
    }
    public static void openWindowOnClick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null) {
            Screen screen = new ChangeFormationScreen();
            mc.setScreen(screen);
        }
    }
    public Optional<FormationType> getPreviousHovered(){
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.getCapability(LivingDataProvider.CAPABILITY).resolve().isPresent()){
            int prevType = mc.player
                    .getCapability(LivingDataProvider.CAPABILITY)
                    .resolve()
                    .get()
                    .getPrevFormation();
            if (prevType > -1){
                return FormationType.getFromFormationType(FormationType.getByFormationType(prevType));
            }
        }
        return FormationType.getFromFormationType(FormationType.RANDOM);
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        InputHandler.MouseButton button = InputHandler.MouseButton.getButtonFromId(pKeyCode);
        if (button == InputHandler.MouseButton.LEFT){
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    protected void init() {
        super.init();
        this.currentlyHovered =
                this.previousHovered
                        .isPresent()
                        ? this.previousHovered
                        : FormationType.getFromFormationType(FormationType.getByFormationType(minecraft.player
                        .getCapability(LivingDataProvider.CAPABILITY)
                        .resolve()
                        .get()
                        .getFormation()));

        this.slots.clear();
        for (int i = 0; i < FormationType.VALUES.length; ++i) {
            FormationType type = FormationType.VALUES[i];
            this.slots.add(new SelectorFormationWidget(type, this.width / 2 - ALL_SLOTS_WIDTH / 2 + i * 30, this.height / 2 - 30));
        }
    }


    public void render(MatrixStack matrixStack, int x, int y, float p_230430_4_) {
        matrixStack.pushPose();
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bind(UNIT_CHANGE_MENU);
        int i = this.width / 2 - 62;
        int j = this.height / 2 - 30 - 27;
        blit(matrixStack, i, j, 0.0F, 0.0F, 125, 75, 128, 128);
        matrixStack.popPose();
        super.render(matrixStack, x, y, p_230430_4_);
        this.currentlyHovered.ifPresent((p_238712_2_) -> drawCenteredString(matrixStack, this.font, p_238712_2_.getName(), this.width / 2, this.height / 2 - 30 - 20, -1));
        drawCenteredString(matrixStack, this.font, SELECT_FORMATION, this.width / 2, this.height / 2 + 5, 16777215);
        if (!this.setFirstMousePos) {
            this.firstMouseX = x;
            this.firstMouseY = y;
            this.setFirstMousePos = true;
        }

        boolean flag = this.firstMouseX == x && this.firstMouseY == y;

        for(SelectorFormationWidget selectorWidget : this.slots) {
            selectorWidget.render(matrixStack, x, y, p_230430_4_);
            this.currentlyHovered.ifPresent((type) -> selectorWidget.setSelected(type == selectorWidget.icon));
            if (!flag && selectorWidget.isHovered()) {
                this.currentlyHovered = Optional.of(selectorWidget.icon);
            }
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int buttonId) {
        if (super.mouseClicked(mouseX, mouseY, buttonId)) {
            return true;
        }
        InputHandler.MouseButton button = InputHandler.MouseButton.getButtonFromId(buttonId);
        if (button == InputHandler.MouseButton.LEFT){
            switchToHoveredFormationTypeAndClose(this.minecraft, this.currentlyHovered);
        }
        return false;
    }
    private void switchToHoveredFormationTypeAndClose(Minecraft minecraft, Optional<FormationType> hovered){
        if (hovered.isPresent()){
            AddonPackets.sendToServer(new PlayerFormationPacket(hovered.get().getFormationType()));
            RotpBadCompanyAddon.getLogger().info(hovered.get().formationType);
            previousHovered = hovered;
        }
        else if (previousHovered.isPresent()){
            AddonPackets.sendToServer(new PlayerFormationPacket(previousHovered.get().getFormationType()));
            RotpBadCompanyAddon.getLogger().info(previousHovered.get().formationType);
        }
        minecraft.setScreen(null);
    }

}