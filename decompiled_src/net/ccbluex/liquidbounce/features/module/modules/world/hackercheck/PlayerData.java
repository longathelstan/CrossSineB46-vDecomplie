/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.hackercheck;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;

public class PlayerData {
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public boolean k;
    public int h;
    public int i;
    public double posY;
    public double posZ;
    public double posX;
    public double deltaXZ;

    public void update(EntityPlayer entityPlayer) {
        int ticksExisted = entityPlayer.field_70173_aa;
        this.posX = entityPlayer.field_70165_t - entityPlayer.field_70142_S;
        this.posY = entityPlayer.field_70163_u - entityPlayer.field_70137_T;
        this.posZ = entityPlayer.field_70161_v - entityPlayer.field_70136_U;
        this.deltaXZ = Math.max(Math.abs(this.posX), Math.abs(this.posZ));
        if (this.deltaXZ >= 0.07) {
            ++this.c;
            this.e = ticksExisted;
        } else {
            this.c = 0;
        }
        if (Math.abs(this.posY) >= 0.1) {
            this.b = ticksExisted;
        }
        if (entityPlayer.func_70093_af()) {
            this.f = ticksExisted;
        }
        this.d = entityPlayer.field_82175_bq && entityPlayer.func_70632_aY() ? ++this.d : 0;
        this.i = entityPlayer.func_70051_ag() && entityPlayer.func_71039_bw() ? ++this.i : 0;
        if (entityPlayer.field_70125_A >= 70.0f && entityPlayer.func_70694_bm() != null && entityPlayer.func_70694_bm().func_77973_b() instanceof ItemBlock) {
            if (entityPlayer.field_110158_av == 1) {
                this.h = !this.k && entityPlayer.func_70093_af() ? ++this.h : 0;
            }
        } else {
            this.h = 0;
        }
    }

    public void updateSneak(EntityPlayer entityPlayer) {
        this.k = entityPlayer.func_70093_af();
    }
}

