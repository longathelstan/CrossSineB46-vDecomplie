/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.protocol.api;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.IOException;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

public class ProtocolSelector
extends GuiScreen {
    private final GuiScreen parent;
    private final boolean simple;
    private final FinishedCallback finishedCallback;
    private SlotList list;

    public ProtocolSelector(GuiScreen parent) {
        this(parent, false, (version, unused) -> ProtocolBase.getManager().setTargetVersion(version));
    }

    public ProtocolSelector(GuiScreen parent, boolean simple, FinishedCallback finishedCallback) {
        this.parent = parent;
        this.simple = simple;
        this.finishedCallback = finishedCallback;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(new GuiButton(1, 5, this.field_146295_m - 25, 60, 20, "Done"));
        this.list = new SlotList(this.field_146297_k, this.field_146294_l, this.field_146295_m, -26 + this.field_146289_q.field_78288_b * 3, this.field_146295_m, this.field_146289_q.field_78288_b);
    }

    protected void func_146284_a(GuiButton button) {
        this.list.func_148147_a(button);
        if (button.field_146127_k == 1) {
            this.field_146297_k.func_147108_a(this.parent);
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.field_146297_k.func_147108_a(this.parent);
        }
    }

    public void func_146274_d() throws IOException {
        this.list.func_178039_p();
        super.func_146274_d();
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.list.func_148128_a(mouseX, mouseY, partialTicks);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    public static interface FinishedCallback {
        public void finished(ProtocolVersion var1, GuiScreen var2);
    }

    class SlotList
    extends GuiSlot {
        public SlotList(Minecraft client, int width, int height, int top, int bottom, int slotHeight) {
            super(client, width, height, top, bottom, slotHeight);
        }

        protected int func_148127_b() {
            return ProtocolBase.versions.size();
        }

        protected void func_148144_a(int index2, boolean b, int i1, int i2) {
            ProtocolSelector.this.finishedCallback.finished(ProtocolBase.versions.get(index2), ProtocolSelector.this.parent);
        }

        protected boolean func_148131_a(int index2) {
            return false;
        }

        protected void func_148123_a() {
            ProtocolSelector.this.func_146276_q_();
        }

        protected void func_180791_a(int index2, int x, int y, int slotHeight, int mouseX, int mouseY) {
            ProtocolVersion version;
            ProtocolVersion targetVersion = ProtocolBase.getManager().getTargetVersion();
            String color = targetVersion == (version = ProtocolBase.versions.get(index2)) ? (ProtocolSelector.this.simple ? ChatFormatting.GOLD.toString() : ChatFormatting.GREEN.toString()) : (ProtocolSelector.this.simple ? ChatFormatting.WHITE.toString() : ChatFormatting.DARK_RED.toString());
            ProtocolSelector.this.func_73732_a(this.field_148161_k.field_71466_p, color + version.getName(), this.field_148155_a / 2, y - 1, -1);
        }
    }
}

