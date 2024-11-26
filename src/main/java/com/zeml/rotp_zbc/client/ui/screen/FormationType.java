package com.zeml.rotp_zbc.client.ui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum FormationType {
    RANDOM(new TranslationTextComponent("gui.rotp_zbc.random"), 0),
    CIRCLE(new TranslationTextComponent("gui.rotp_zbc.circle"),  1),
    LINE(new TranslationTextComponent("gui.rotp_zbc.line"),  2),


    ;
    public static final FormationType[] VALUES = values();
    final ITextComponent name;
    final int formationType;
    public void drawIcon(FormationType type, int x, int y) {
        FormationIcon.renderIcon(type, new MatrixStack(), x, y);
    }

    FormationType(ITextComponent name, int FormationType){
        this.name = name;
        this.formationType = FormationType;
    }

    public ITextComponent getName() {
        return this.name;
    }

    public int getFormationType() {
        return this.formationType;
    }
    public static FormationType getByFormationType(int FormationType){
        List<FormationType> types = Arrays.stream(values()).filter(type -> type.getFormationType() == FormationType).collect(Collectors.toList());
        Optional<FormationType> matchType = types.stream().findFirst();
        System.out.println(types.stream().count());
        return matchType.orElse(null);
    }

    public static Optional<FormationType> getFromFormationType(FormationType type) {
        if (type == null) {
            return Optional.empty();
        }

        switch (type) {
            case CIRCLE:
                return Optional.of(CIRCLE);
            case LINE:
                return Optional.of(LINE);
            default:
                return Optional.of(RANDOM);
        }
    }
}
