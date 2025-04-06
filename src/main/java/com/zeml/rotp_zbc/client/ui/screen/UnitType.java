package com.zeml.rotp_zbc.client.ui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum UnitType {

    ALL(new TranslationTextComponent("entity.rotp_zbc.all_c"), 0),
    SOLDIER(new TranslationTextComponent("entity.rotp_zbc.bad_soldier"),  1),
    TANK(new TranslationTextComponent("entity.rotp_zbc.bad_tank"),  2),
    HELICOPTER(new TranslationTextComponent("entity.rotp_zbc.bad_helicopter"),3)

    ;
    public static final UnitType[] VALUES =values();
    final ITextComponent name;
    final int unitType;
    public void drawIcon(UnitType type, int x, int y) {
        UnitIcon.renderIcon(type, new MatrixStack(), x, y);
    }

    UnitType(ITextComponent name, int unitType){
        this.name = name;
        this.unitType = unitType;
    }

    public ITextComponent getName() {
        return this.name;
    }

    public int getUnitType() {
        return this.unitType;
    }
    public static UnitType getByUnitType(int unitType){
        List<UnitType> types = Arrays.stream(values()).filter(type -> type.getUnitType() == unitType).collect(Collectors.toList());
        Optional<UnitType> matchType = types.stream().findFirst();
        return matchType.orElse(null);
    }

    public static Optional<UnitType> getFromUnitType(UnitType type) {
        if (type == null) {
            return Optional.empty();
        }
        switch(type) {
            case ALL:
                return Optional.of(ALL);
            case SOLDIER:
                return Optional.of(SOLDIER);
            case TANK:
                return Optional.of(TANK);
            case HELICOPTER:
                return Optional.of(HELICOPTER);
            default:
                return Optional.empty();
        }
    }
}
