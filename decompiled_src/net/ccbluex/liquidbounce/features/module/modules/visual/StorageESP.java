/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.Stealer;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="StorageESP", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/StorageESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "dispenserValue", "enderChestValue", "furnaceValue", "hopperValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "outlineWidthValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getColor", "Ljava/awt/Color;", "tileEntity", "Lnet/minecraft/tileentity/TileEntity;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"})
public final class StorageESP
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<Float> outlineWidthValue;
    @NotNull
    private final BoolValue chestValue;
    @NotNull
    private final BoolValue enderChestValue;
    @NotNull
    private final BoolValue furnaceValue;
    @NotNull
    private final BoolValue dispenserValue;
    @NotNull
    private final BoolValue hopperValue;

    public StorageESP() {
        String[] stringArray = new String[]{"Box", "OtherBox", "Outline", "ShaderOutline", "ShaderGlow", "2D", "WireFrame"};
        this.modeValue = new ListValue("Mode", stringArray, "Outline");
        this.outlineWidthValue = new FloatValue("Outline-Width", 3.0f, 0.5f, 5.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ StorageESP this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return StorageESP.access$getModeValue$p(this.this$0).equals("Outline");
            }
        });
        this.chestValue = new BoolValue("Chest", true);
        this.enderChestValue = new BoolValue("EnderChest", true);
        this.furnaceValue = new BoolValue("Furnace", true);
        this.dispenserValue = new BoolValue("Dispenser", true);
        this.hopperValue = new BoolValue("Hopper", true);
    }

    private final Color getColor(TileEntity tileEntity) {
        if (((Boolean)this.chestValue.get()).booleanValue() && tileEntity instanceof TileEntityChest && !Stealer.INSTANCE.getAuraclickedBlocks().contains(tileEntity.func_174877_v())) {
            return new Color(0, 66, 255);
        }
        if (((Boolean)this.enderChestValue.get()).booleanValue() && tileEntity instanceof TileEntityEnderChest && !Stealer.INSTANCE.getAuraclickedBlocks().contains(tileEntity.func_174877_v())) {
            return Color.MAGENTA;
        }
        if (((Boolean)this.furnaceValue.get()).booleanValue() && tileEntity instanceof TileEntityFurnace) {
            return Color.BLACK;
        }
        if (((Boolean)this.dispenserValue.get()).booleanValue() && tileEntity instanceof TileEntityDispenser) {
            return Color.BLACK;
        }
        if (((Boolean)this.hopperValue.get()).booleanValue() && tileEntity instanceof TileEntityHopper) {
            return Color.GRAY;
        }
        return null;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        try {
            String mode2 = (String)this.modeValue.get();
            float gamma = MinecraftInstance.mc.field_71474_y.field_74333_Y;
            MinecraftInstance.mc.field_71474_y.field_74333_Y = 100000.0f;
            for (TileEntity tileEntity : MinecraftInstance.mc.field_71441_e.field_147482_g) {
                Intrinsics.checkNotNullExpressionValue(tileEntity, "tileEntity");
                if (this.getColor(tileEntity) == null) continue;
                String string = mode2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                switch (string) {
                    case "otherbox": 
                    case "box": {
                        Color color;
                        RenderUtils.drawBlockBox(tileEntity.func_174877_v(), color, !StringsKt.equals(mode2, "otherbox", true), true, ((Number)this.outlineWidthValue.get()).floatValue(), 1.0f);
                        break;
                    }
                    case "2d": {
                        Color color;
                        RenderUtils.draw2D(tileEntity.func_174877_v(), color.getRGB(), Color.BLACK.getRGB());
                        break;
                    }
                    case "outline": {
                        Color color;
                        RenderUtils.drawBlockBox(tileEntity.func_174877_v(), color, true, false, ((Number)this.outlineWidthValue.get()).floatValue(), 1.0f);
                        break;
                    }
                    case "wireframe": {
                        Color color;
                        GL11.glPushMatrix();
                        GL11.glPushAttrib((int)1048575);
                        GL11.glPolygonMode((int)1032, (int)6913);
                        GL11.glDisable((int)3553);
                        GL11.glDisable((int)2896);
                        GL11.glDisable((int)2929);
                        GL11.glEnable((int)2848);
                        GL11.glEnable((int)3042);
                        GL11.glBlendFunc((int)770, (int)771);
                        RenderUtils.glColor(color);
                        GL11.glLineWidth((float)1.5f);
                        TileEntityRendererDispatcher.field_147556_a.func_180546_a(tileEntity, event.getPartialTicks(), -1);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                    }
                }
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            MinecraftInstance.mc.field_71474_y.field_74333_Y = gamma;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        FramebufferShader framebufferShader;
        Intrinsics.checkNotNullParameter(event, "event");
        String mode2 = (String)this.modeValue.get();
        RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
        float partialTicks = event.getPartialTicks();
        String string = mode2;
        if (Intrinsics.areEqual(string, "shaderoutline")) {
            framebufferShader = OutlineShader.OUTLINE_SHADER;
        } else if (Intrinsics.areEqual(string, "shaderglow")) {
            framebufferShader = GlowShader.GLOW_SHADER;
        } else {
            return;
        }
        FramebufferShader shader = framebufferShader;
        Map entityMap = new HashMap();
        for (TileEntity entry : MinecraftInstance.mc.field_71441_e.field_147482_g) {
            Color color;
            Intrinsics.checkNotNullExpressionValue(entry, "tileEntity");
            if (this.getColor(entry) == null) continue;
            if (!entityMap.containsKey(color)) {
                Map map = entityMap;
                ArrayList arrayList = new ArrayList();
                map.put(color, arrayList);
            }
            Object v = entityMap.get(color);
            Intrinsics.checkNotNull(v);
            ((ArrayList)v).add(entry);
        }
        for (Map.Entry entry : entityMap.entrySet()) {
            Color key = (Color)entry.getKey();
            ArrayList value = (ArrayList)entry.getValue();
            shader.startDraw(partialTicks);
            for (TileEntity tileEntity : value) {
                TileEntityRendererDispatcher.field_147556_a.func_147549_a(tileEntity, (double)tileEntity.func_174877_v().func_177958_n() - renderManager.field_78725_b, (double)tileEntity.func_174877_v().func_177956_o() - renderManager.field_78726_c, (double)tileEntity.func_174877_v().func_177952_p() - renderManager.field_78723_d, partialTicks);
            }
            shader.stopDraw(key, StringsKt.equals(mode2, "shaderglow", true) ? 2.5f : 1.5f, 1.0f);
        }
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(StorageESP $this) {
        return $this.modeValue;
    }
}

