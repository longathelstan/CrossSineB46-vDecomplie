/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import javax.imageio.ImageIO;
import jline.internal.Nullable;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.ClickEvent;
import net.ccbluex.liquidbounce.event.KeyBindEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.client.Patcher;
import net.ccbluex.liquidbounce.features.module.modules.combat.TickBase;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.protocol.ProtocolMod;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.render.ImageUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public abstract class MixinMinecraft {
    @Shadow
    public GuiScreen field_71462_r;
    @Shadow
    private Entity field_175622_Z;
    @Shadow
    private boolean field_71431_Q;
    @Shadow
    public boolean field_71454_w;
    @Shadow
    private int field_71429_W;
    @Shadow
    public MovingObjectPosition field_71476_x;
    @Shadow
    public WorldClient field_71441_e;
    @Shadow
    public EntityPlayerSP field_71439_g;
    @Shadow
    public EffectRenderer field_71452_i;
    @Shadow
    public EntityRenderer field_71460_t;
    @Shadow
    public PlayerControllerMP field_71442_b;
    @Shadow
    public int field_71443_c;
    @Shadow
    public int field_71440_d;
    @Shadow
    public int field_71467_ac;
    @Shadow
    public GameSettings field_71474_y;
    @Shadow
    private Profiler field_71424_I;
    @Shadow
    private boolean field_71445_n;
    @Shadow
    @Final
    public Timer field_71428_T;
    @Shadow
    public long field_181543_z;
    @Shadow
    public boolean field_71415_G;
    @Shadow
    private PlayerUsageSnooper field_71427_U;
    @Shadow
    private Queue<FutureTask<?>> field_152351_aB;
    @Shadow
    public GuiAchievement field_71458_u;
    @Shadow
    public int field_71420_M;
    @Shadow
    public long field_71421_N;
    @Shadow
    private Framebuffer field_147124_at;
    @Shadow
    public long field_71419_L;
    @Shadow
    private IStream field_152353_at;
    @Shadow
    @Final
    public FrameTimer field_181542_y;
    @Shadow
    public String field_71426_K;
    @Shadow
    private IntegratedServer field_71437_Z;
    @Shadow
    private static int field_71470_ab;
    @Shadow
    @Final
    private static Logger field_147123_G;
    @Shadow
    private long field_83002_am;
    @Shadow
    public GuiIngame field_71456_v;
    @Shadow
    public TextureManager field_71446_o;
    @Shadow
    private int field_71457_ai;
    @Shadow
    public RenderGlobal field_71438_f;
    @Shadow
    private RenderManager field_175616_W;
    @Shadow
    private NetworkManager field_71453_ak;
    @Shadow
    public long field_71423_H;
    @Shadow
    private SoundHandler field_147127_av;
    @Shadow
    private MusicTicker field_147126_aw;
    @Unique
    @Nullable
    private WorldClient lastWorld = null;
    private long lastFrame = this.getTime();
    private static final String TARGET = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V";

    @Shadow
    protected abstract void func_71407_l();

    @Shadow
    private void func_147121_ag() {
    }

    @Shadow
    private void func_147112_ai() {
    }

    @Shadow
    public abstract IResourceManager func_110442_L();

    @Shadow
    public abstract void func_71400_g();

    @Shadow
    public abstract void func_71361_d(String var1);

    @Shadow
    public abstract boolean func_147107_h();

    @Shadow
    public abstract void func_175601_h();

    @Shadow
    public abstract boolean func_71356_B();

    @Shadow
    private void func_71366_a(long elapsedTicksTime) {
    }

    @Shadow
    public abstract void func_110436_a();

    @Shadow
    public abstract void func_152348_aa();

    @Shadow
    public abstract Entity func_175606_aa();

    @Shadow
    public abstract NetHandlerPlayClient func_147114_u();

    @Shadow
    public abstract void func_71381_h();

    @Shadow
    private void func_71383_b(int p_updateDebugProfilerName_1_) {
    }

    @Shadow
    public abstract void func_71385_j();

    @Shadow
    public void func_147108_a(GuiScreen p_displayGuiScreen_1_) {
    }

    @Inject(method={"run"}, at={@At(value="HEAD")})
    private void init(CallbackInfo callbackInfo) {
        if (this.field_71443_c < 1067) {
            this.field_71443_c = 1067;
        }
        if (this.field_71440_d < 622) {
            this.field_71440_d = 622;
        }
    }

    @Inject(method={"startGame"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal=2, shift=At.Shift.AFTER)})
    private void startGame(CallbackInfo callbackInfo) {
        CrossSine.INSTANCE.initClient();
    }

    @Inject(method={"createDisplay"}, at={@At(value="INVOKE", target="Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift=At.Shift.AFTER)})
    private void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle((String)"Initialzing Minecraft");
    }

    @Inject(method={"displayGuiScreen"}, at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", shift=At.Shift.AFTER)})
    private void displayGuiScreen(CallbackInfo callbackInfo) {
        if (!CrossSine.INSTANCE.getDestruced()) {
            if (this.field_71462_r instanceof GuiMainMenu || this.field_71462_r != null && this.field_71462_r.getClass().getName().startsWith("net.labymod") && this.field_71462_r.getClass().getSimpleName().equals("ModGuiMainMenu")) {
                this.field_71462_r = CrossSine.mainMenu;
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
                this.field_71462_r.func_146280_a(Minecraft.func_71410_x(), scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
                this.field_71454_w = false;
            }
            CrossSine.eventManager.callEvent(new ScreenEvent(this.field_71462_r));
        }
    }

    public long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Overwrite
    private void func_71411_J() throws IOException {
        long currentTime = this.getTime();
        int deltaTime = (int)(currentTime - this.lastFrame);
        this.lastFrame = currentTime;
        RenderUtils.deltaTime = deltaTime;
        long i = System.nanoTime();
        this.field_71424_I.func_76320_a("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.func_71400_g();
        }
        if (this.field_71445_n && this.field_71441_e != null) {
            float f = this.field_71428_T.field_74281_c;
            this.field_71428_T.func_74275_a();
            this.field_71428_T.field_74281_c = f;
        } else {
            this.field_71428_T.func_74275_a();
        }
        this.field_71424_I.func_76320_a("scheduledExecutables");
        Queue<FutureTask<?>> f = this.field_152351_aB;
        synchronized (f) {
            while (!this.field_152351_aB.isEmpty()) {
                Util.func_181617_a(this.field_152351_aB.poll(), (Logger)field_147123_G);
            }
        }
        this.field_71424_I.func_76319_b();
        long l = System.nanoTime();
        this.field_71424_I.func_76320_a("tick");
        for (int j = 0; j < this.field_71428_T.field_74280_b; ++j) {
            if (Minecraft.func_71410_x().field_71439_g != null) {
                TickBase tickBase;
                boolean skip = false;
                if (j == 0 && (tickBase = CrossSine.moduleManager.getModule(TickBase.class)).getState()) {
                    int extraTicks = tickBase.getExtraTicks();
                    if (extraTicks == -1) {
                        skip = true;
                    } else if (extraTicks > 0) {
                        for (int aa = 0; aa < extraTicks; ++aa) {
                            this.func_71407_l();
                        }
                        tickBase.setFreezing(true);
                    }
                }
                if (skip) continue;
                this.func_71407_l();
                continue;
            }
            this.func_71407_l();
        }
        this.field_71424_I.func_76318_c("preRenderErrors");
        long i1 = System.nanoTime() - l;
        this.func_71361_d("Pre render");
        this.field_71424_I.func_76318_c("sound");
        this.field_147127_av.func_147691_a((EntityPlayer)this.field_71439_g, this.field_71428_T.field_74281_c);
        this.field_71424_I.func_76319_b();
        this.field_71424_I.func_76320_a("render");
        GlStateManager.func_179094_E();
        GlStateManager.func_179086_m((int)16640);
        this.field_147124_at.func_147610_a(true);
        this.field_71424_I.func_76320_a("display");
        GlStateManager.func_179098_w();
        if (this.field_71439_g != null && this.field_71439_g.func_70094_T()) {
            this.field_71474_y.field_74320_O = 0;
        }
        this.field_71424_I.func_76319_b();
        if (!this.field_71454_w) {
            this.field_71424_I.func_76318_c("gameRenderer");
            this.field_71460_t.func_181560_a(this.field_71428_T.field_74281_c, i);
            this.field_71424_I.func_76319_b();
            FMLCommonHandler.instance().onRenderTickEnd(this.field_71428_T.field_74281_c);
        }
        this.field_71424_I.func_76319_b();
        if (this.field_71474_y.field_74330_P && this.field_71474_y.field_74329_Q && !this.field_71474_y.field_74319_N) {
            if (!this.field_71424_I.field_76327_a) {
                this.field_71424_I.func_76317_a();
            }
            this.field_71424_I.field_76327_a = true;
            this.func_71366_a(i1);
        } else {
            this.field_71424_I.field_76327_a = false;
            this.field_71421_N = System.nanoTime();
        }
        this.field_71458_u.func_146254_a();
        this.field_147124_at.func_147609_e();
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        this.field_147124_at.func_147615_c(this.field_71443_c, this.field_71440_d);
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        this.field_71460_t.func_152430_c(this.field_71428_T.field_74281_c);
        GlStateManager.func_179121_F();
        this.field_71424_I.func_76320_a("root");
        this.func_175601_h();
        Thread.yield();
        this.field_71424_I.func_76320_a("stream");
        this.field_71424_I.func_76320_a("update");
        this.field_71424_I.func_76318_c("submit");
        this.field_71424_I.func_76319_b();
        this.field_71424_I.func_76319_b();
        this.func_71361_d("Post render");
        ++this.field_71420_M;
        this.field_71445_n = this.func_71356_B() && this.field_71462_r != null && this.field_71462_r.func_73868_f() && !this.field_71437_Z.func_71344_c();
        long k = System.nanoTime();
        this.field_181542_y.func_181747_a(k - this.field_181543_z);
        this.field_181543_z = k;
        while (Minecraft.func_71386_F() >= this.field_71419_L + 1000L) {
            field_71470_ab = this.field_71420_M;
            Object[] objectArray = new Object[8];
            objectArray[0] = field_71470_ab;
            objectArray[1] = RenderChunk.field_178592_a;
            objectArray[2] = RenderChunk.field_178592_a != 1 ? "s" : "";
            objectArray[3] = (float)this.field_71474_y.field_74350_i == GameSettings.Options.FRAMERATE_LIMIT.func_148267_f() ? "inf" : Integer.valueOf(this.field_71474_y.field_74350_i);
            objectArray[4] = this.field_71474_y.field_74352_v ? " vsync" : "";
            Object object = objectArray[5] = this.field_71474_y.field_74347_j ? "" : " fast";
            objectArray[6] = this.field_71474_y.field_74345_l == 0 ? "" : (this.field_71474_y.field_74345_l == 1 ? " fast-clouds" : " fancy-clouds");
            objectArray[7] = OpenGlHelper.func_176075_f() ? " vbo" : "";
            this.field_71426_K = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", objectArray);
            RenderChunk.field_178592_a = 0;
            this.field_71419_L += 1000L;
            this.field_71420_M = 0;
            this.field_71427_U.func_76471_b();
            if (this.field_71427_U.func_76468_d()) continue;
            this.field_71427_U.func_76463_a();
        }
        if (this.func_147107_h()) {
            this.field_71424_I.func_76320_a("fpslimit_wait");
            Display.sync((int)this.func_90020_K());
            this.field_71424_I.func_76319_b();
        }
        this.field_71424_I.func_76319_b();
    }

    @Inject(method={"runTick"}, at={@At(value="HEAD")})
    private void runTick(CallbackInfo callbackInfo) {
        StaticStorage.scaledResolution = new ScaledResolution((Minecraft)this);
    }

    @Inject(method={"runTick"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/settings/KeyBinding;isPressed()Z", ordinal=0)})
    private void changeItem(CallbackInfo info) {
        for (int k = 0; k < 9; ++k) {
            if (!this.field_71474_y.field_151456_ac[k].func_151468_f()) continue;
            if (this.field_71439_g.func_175149_v()) {
                this.field_71456_v.func_175187_g().func_175260_a(k);
                continue;
            }
            if (SlotUtils.INSTANCE.getChanged()) {
                SlotUtils.INSTANCE.setPrevSlot(k);
                continue;
            }
            this.field_71439_g.field_71071_by.field_70461_c = k;
        }
    }

    @Inject(method={"runTick"}, at={@At(value="INVOKE", target="Lorg/lwjgl/input/Mouse;getEventDWheel()I")}, cancellable=true)
    private void onRunTick(CallbackInfo ci) {
        int j = Mouse.getEventDWheel();
        if (j != 0) {
            Minecraft mc = (Minecraft)this;
            if (mc.field_71439_g.func_175149_v()) {
                int n = j = j < 0 ? -1 : 1;
                if (mc.field_71456_v.func_175187_g().func_175262_a()) {
                    mc.field_71456_v.func_175187_g().func_175259_b(-j);
                } else {
                    float f = MathHelper.func_76131_a((float)(mc.field_71439_g.field_71075_bZ.func_75093_a() + (float)j * 0.005f), (float)0.0f, (float)0.2f);
                    mc.field_71439_g.field_71075_bZ.func_75092_a(f);
                }
            } else {
                SlotUtils.INSTANCE.scrollChangeItem(j);
            }
        }
    }

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void startVia(GameConfiguration p_i45547_1_, CallbackInfo ci) {
        ProtocolBase.init(ProtocolMod.PLATFORM);
    }

    @Inject(method={"runTick"}, at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;joinPlayerCounter:I", ordinal=0)})
    private void onTick(CallbackInfo callbackInfo) {
        CrossSine.eventManager.callEvent(new TickEvent());
    }

    @Redirect(method={"runTick"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V"))
    public void runTick_setKeyBindState(int keybind, boolean state) {
        KeyBinding.func_74510_a((int)keybind, (boolean)state);
    }

    @Inject(method={"dispatchKeypresses"}, at={@At(value="HEAD")})
    private void onKey(CallbackInfo callbackInfo) {
        KeyBindEvent event = new KeyBindEvent();
        try {
            if (Keyboard.getEventKeyState() && this.field_71462_r == null) {
                CrossSine.eventManager.callEvent(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            if (Keyboard.getEventKeyState()) {
                event.setKey(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey());
                CrossSine.eventManager.callEvent(event);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Inject(method={"shutdown"}, at={@At(value="HEAD")})
    private void shutdown(CallbackInfo callbackInfo) {
        CrossSine.INSTANCE.stopClient();
    }

    @Overwrite
    public void func_147116_af() {
        CrossSine.eventManager.callEvent(new ClickEvent());
        CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
        if (this.field_71429_W <= 0) {
            if (this.field_71476_x != null && Objects.requireNonNull(this.field_71476_x.field_72313_a) != MovingObjectPosition.MovingObjectType.ENTITY) {
                this.field_71439_g.func_71038_i();
            }
            if (this.field_71476_x != null) {
                switch (this.field_71476_x.field_72313_a) {
                    case ENTITY: {
                        ProtocolFixer.sendFixedAttack((EntityPlayer)this.field_71439_g, this.field_71476_x.field_72308_g, false);
                        break;
                    }
                    case BLOCK: {
                        BlockPos blockpos = this.field_71476_x.func_178782_a();
                        if (this.field_71441_e.func_180495_p(blockpos).func_177230_c().func_149688_o() != Material.field_151579_a) {
                            this.field_71442_b.func_180511_b(blockpos, this.field_71476_x.field_178784_b);
                            break;
                        }
                    }
                    default: {
                        if (!((Boolean)Objects.requireNonNull(CrossSine.moduleManager.getModule(Patcher.class)).getHitDelayFix().get()).booleanValue()) break;
                        this.field_71429_W = 0;
                    }
                }
            }
        }
    }

    @Inject(method={"sendClickBlockToController"}, at={@At(value="HEAD")}, cancellable=true)
    private void sendClickBlockToController(boolean leftClick, CallbackInfo ci) {
        if (!leftClick) {
            this.field_71429_W = 0;
        }
        if (this.field_71429_W <= 0) {
            if (leftClick && this.field_71476_x != null && this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos blockPos = this.field_71476_x.func_178782_a();
                if (this.field_71439_g.func_71039_bw() && ProtocolFixer.newerThanOrEqualsTo1_8()) {
                    return;
                }
                if (this.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149688_o() != Material.field_151579_a && this.field_71442_b.func_180512_c(blockPos, this.field_71476_x.field_178784_b)) {
                    this.field_71452_i.func_180532_a(blockPos, this.field_71476_x.field_178784_b);
                    this.field_71439_g.func_71038_i();
                }
            } else {
                this.field_71442_b.func_78767_c();
            }
        }
        ci.cancel();
    }

    @Inject(method={"rightClickMouse"}, at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I", shift=At.Shift.AFTER)})
    private void rightClickMouse(CallbackInfo callbackInfo) {
        CrossSine.eventManager.callEvent(new ClickEvent());
        CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
    }

    @Inject(method={"middleClickMouse"}, at={@At(value="HEAD")})
    private void middleClickMouse(CallbackInfo ci) {
        CPSCounter.registerClick(CPSCounter.MouseButton.MIDDLE);
    }

    @Inject(method={"runTick"}, at={@At(value="HEAD")})
    private void runTickPre(CallbackInfo ci) {
        if (this.lastWorld != Minecraft.func_71410_x().field_71441_e && PlayerUtils.NotNull()) {
            CrossSine.eventManager.callEvent(new WorldEvent());
        }
        this.lastWorld = Minecraft.func_71410_x().field_71441_e;
    }

    @Inject(method={"setWindowIcon"}, at={@At(value="HEAD")}, cancellable=true)
    private void setWindowIcon(CallbackInfo callbackInfo) {
        try {
            if (Util.func_110647_a() != Util.EnumOS.OSX) {
                BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream("/assets/minecraft/crosssine/misc/icon.png"));
                ByteBuffer bytebuffer = ImageUtils.readImageToBuffer(ImageUtils.resizeImage(image, 16, 16));
                if (bytebuffer == null) {
                    throw new Exception("Error when loading image.");
                }
                Display.setIcon((ByteBuffer[])new ByteBuffer[]{bytebuffer, ImageUtils.readImageToBuffer(image)});
                callbackInfo.cancel();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method={"toggleFullscreen()V"}, at={@At(value="INVOKE", target="Lorg/lwjgl/opengl/Display;setFullscreen(Z)V", shift=At.Shift.AFTER, remap=false)}, require=1, allow=1)
    private void toggleFullscreen(CallbackInfo callbackInfo) {
        if (!this.field_71431_Q) {
            Display.setResizable((boolean)false);
            Display.setResizable((boolean)true);
        }
    }

    @Overwrite
    public int func_90020_K() {
        return !PlayerUtils.NotNull() ? 120 : this.field_71474_y.field_74350_i;
    }
}

