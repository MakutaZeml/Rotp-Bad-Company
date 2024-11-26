package com.zeml.rotp_zbc.capability.entity;

import com.zeml.rotp_zbc.network.AddonPackets;
import com.zeml.rotp_zbc.network.packets.server.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.INBTSerializable;

public class LivingData implements INBTSerializable<CompoundNBT> {
    private final LivingEntity entity;
    private int unitType = 0;
    private int prevUnitType = 0;
    private boolean landedMines = false;
    private boolean soldierStay = false;
    private boolean tankStay = false;
    private boolean copterStay = false;
    private boolean explosiveMissiles = true;

    private BlockPos soldierPostGoing = new BlockPos(0,0,0);
    private BlockPos tankPostGoing = new BlockPos(0,0,0);
    private BlockPos copterPostGoing = new BlockPos(0,0,0);

    private boolean soldierClose = false;
    private boolean tankClose = false;
    private  boolean copterClose = false;

    private int formation = 0;
    private int prevFormation = 0;
    public LivingData(LivingEntity entity) {
        this.entity = entity;
    }


    public void setSoldierStay(boolean soldierStay) {
        this.soldierStay = soldierStay;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SetSoldierStayPacket(entity.getId(),soldierStay), (ServerPlayerEntity) entity);
        }
    }
    public boolean getSoldierStay(){
        return  this.soldierStay;
    }

    public void setTankStay(boolean tankStay){
        this.tankStay = tankStay;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SetTankStayPacket(entity.getId(),tankStay), (ServerPlayerEntity) entity);
        }
    }

    public boolean getTankStay(){
        return this.tankStay;
    }


    public void setCopterStay(boolean copterStay){
        this.copterStay = copterStay;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SetCopterStayPacket(entity.getId(),copterStay),(ServerPlayerEntity) entity);
        }
    }
    public boolean getCopterStay(){
        return this.copterStay;
    }

    public void setUnitType(int unitType) {
        this.prevUnitType = this.unitType;
        this.unitType = unitType;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SetUnitTypePacket(entity.getId(),unitType), (ServerPlayerEntity) entity);
        }
    }
    public int getUnitType(){
        return this.unitType;
    }

    public int getPrevUnitType(){
        return this.prevUnitType;
    }

    public void setLandedMines(boolean landedMines){
        this.landedMines = landedMines;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new LandedMinesPacket(entity.getId(),landedMines),(ServerPlayerEntity) entity);
        }
    }

    public boolean getLandedMines(){
        return this.landedMines;
    }

    public void setFormation(int formation){
        this.prevFormation = this.formation;
        this.formation = formation;
        if(entity instanceof ServerPlayerEntity){

        }
    }
    public int getFormation(){
        return this.formation;
    }
    public int getPrevFormation(){
        return this.prevFormation;
    }


    public void setExplosiveMissiles(boolean explosiveMissiles) {
        this.explosiveMissiles = explosiveMissiles;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new MissileModePacket(entity.getId(),explosiveMissiles),(ServerPlayerEntity) entity);
        }
    }

    public boolean isExplosiveMissiles() {
        return explosiveMissiles;
    }


    public void setSoldierClose(boolean soldierClose) {
        this.soldierClose = soldierClose;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SoldierStayClosePacket(entity.getId(),soldierClose),(ServerPlayerEntity) entity);
        }
    }

    public boolean isSoldierClose() {
        return soldierClose;
    }

    public void setTankClose(boolean tankClose) {
        this.tankClose = tankClose;
        if(entity instanceof ServerPlayerEntity) {
            AddonPackets.sendToClient(new TankStayClosePacket(entity.getId(), tankClose), (ServerPlayerEntity) entity);
        }

    }

    public boolean isTankClose() {
        return tankClose;
    }

    public void setCopterClose(boolean copterClose) {
        this.copterClose = copterClose;
        if(entity instanceof ServerPlayerEntity) {
            AddonPackets.sendToClient(new CopterStayClosePacket(entity.getId(), copterClose), (ServerPlayerEntity) entity);
        }
    }

    public boolean isCopterClose() {
        return copterClose;
    }

    public void setSoldierPostGoing(BlockPos soldierPostGoing) {
        this.soldierPostGoing = soldierPostGoing;
    }

    public BlockPos getSoldierPostGoing() {
        return this.soldierPostGoing;
    }


    public void setTankPostGoing(BlockPos tankPostGoing) {
        this.tankPostGoing = tankPostGoing;
    }

    public BlockPos getTankPostGoing() {
        return this.tankPostGoing;
    }

    public void setCopterPostGoing(BlockPos copterPostGoing) {
        this.copterPostGoing = copterPostGoing;
    }

    public BlockPos getCopterPostGoing() {
        return this.copterPostGoing;
    }



    public void syncWithAnyPlayer(ServerPlayerEntity player) {

        //AddonPackets.sendToClient(new TrPickaxesThrownPacket(entity.getId(), pickaxesThrown), player);
    }

    // If there is data that should only be known to the player, and not to other ones, sync it here instead.
    public void syncWithEntityOnly(ServerPlayerEntity player) {
        AddonPackets.sendToClient(new SetSoldierStayPacket(player.getId(),this.soldierStay), player);
        AddonPackets.sendToClient(new SetTankStayPacket(player.getId(),this.tankStay), player);
        AddonPackets.sendToClient(new SetUnitTypePacket(player.getId(),this.unitType), player);
        AddonPackets.sendToClient(new SetCopterStayPacket(player.getId(),this.copterStay),player);
        AddonPackets.sendToClient(new LandedMinesPacket(player.getId(),this.landedMines),player);
        AddonPackets.sendToClient(new MissileModePacket(player.getId(),explosiveMissiles),player);
        AddonPackets.sendToClient(new SoldierStayClosePacket(player.getId(),soldierClose),player);
        AddonPackets.sendToClient(new TankStayClosePacket(player.getId(), tankClose), player);
        AddonPackets.sendToClient(new CopterStayClosePacket(player.getId(), copterClose), player);
    }




    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("SoldierStay",this.soldierStay);
        nbt.putBoolean("TankStay", this.tankStay);
        nbt.putBoolean("CopterStay", this.copterStay);
        nbt.putInt("Unit type", this.unitType);
        nbt.putBoolean("Mines",this.landedMines);
        nbt.putBoolean("Missile",this.explosiveMissiles);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.soldierStay = nbt.getBoolean("SoldierStay");
        this.tankStay = nbt.getBoolean("TankStay");
        this.copterStay = nbt.getBoolean("CopterStay");
        this.unitType = nbt.getInt("Unit type");
        this.landedMines = nbt.getBoolean("Mines");
        this.explosiveMissiles = nbt.getBoolean("Missile");
    }
}
