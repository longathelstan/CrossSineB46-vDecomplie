/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.client.altmanager;

import java.awt.Color;
import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.account.MinecraftAccount;
import me.liuli.elixir.compat.Session;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiAdd;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDirectLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.MicrosoftLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.SessionGui;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.cookie.CookieUtil;
import net.ccbluex.liquidbounce.utils.cookie.LoginData;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u0000 %2\u00020\u0001:\u0002%&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0012H\u0014J \u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\tH\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0016J\b\u0010\u001d\u001a\u00020\u0015H\u0016J\u0018\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0019H\u0014J \u0010\"\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0019H\u0014J\b\u0010$\u001a\u00020\u0015H\u0016R\u0012\u0010\u0004\u001a\u00060\u0005R\u00020\u0000X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006'"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "altsList", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList;", "altsSlider", "Lnet/minecraftforge/fml/client/config/GuiSlider;", "currentX", "", "currentY", "status", "", "getStatus", "()Ljava/lang/String;", "setStatus", "(Ljava/lang/String;)V", "stylisedAltsButton", "Lnet/minecraft/client/gui/GuiButton;", "unformattedAltsButton", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "updateScreen", "Companion", "GuiList", "CrossSine"})
public final class GuiAltManager
extends GuiScreen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final GuiScreen prevGui;
    @NotNull
    private String status;
    private GuiList altsList;
    private GuiSlider altsSlider;
    private GuiButton stylisedAltsButton;
    private GuiButton unformattedAltsButton;
    private float currentX;
    private float currentY;
    private static int altsLength = 16;
    private static boolean unformattedAlts = true;
    private static boolean stylisedAlts = true;
    @NotNull
    private static net.minecraft.util.Session originalSession;

    public GuiAltManager(@NotNull GuiScreen prevGui) {
        Intrinsics.checkNotNullParameter(prevGui, "prevGui");
        this.prevGui = prevGui;
        this.status = "\u00a77Idle";
    }

    @NotNull
    public final String getStatus() {
        return this.status;
    }

    public final void setStatus(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.status = string;
    }

    public void func_73866_w_() {
        GuiButton it;
        GuiButton guiButton;
        GuiList guiList;
        this.altsList = new GuiList(this);
        GuiList guiList2 = this.altsList;
        if (guiList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList2 = null;
        }
        guiList2.func_148134_d(7, 8);
        GuiList guiList3 = this.altsList;
        if (guiList3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList3 = null;
        }
        guiList3.func_148144_a(-1, false, 0, 0);
        GuiList guiList4 = this.altsList;
        if (guiList4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList4 = null;
        }
        if ((guiList = this.altsList) == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        guiList4.func_148145_f(-1 * guiList.field_148149_f);
        int j = 22;
        this.field_146292_n.add(new GuiButton(1, this.field_146294_l - 80, j + 24, 70, 20, "Add"));
        this.field_146292_n.add(new GuiButton(2, this.field_146294_l - 80, j + 48, 70, 20, "Remove"));
        this.field_146292_n.add(new GuiButton(0, this.field_146294_l - 80, this.field_146295_m - 65, 70, 20, "Back"));
        this.field_146292_n.add(new GuiButton(3, 5, j + 24, 90, 20, "Login"));
        this.field_146292_n.add(new GuiButton(6, 5, j + 48, 90, 20, "DirectLogin"));
        this.field_146292_n.add(new GuiButton(4, 5, j + 72, 90, 20, "RandomAlt"));
        this.field_146292_n.add(new GuiButton(92, 5, j + 96, 90, 20, "Microsoft"));
        this.field_146292_n.add(new GuiButton(93, 5, j + 120, 90, 20, "CookiesLogin"));
        this.field_146292_n.add(new GuiButton(89, 5, j + 144, 90, 20, "RandomCrack"));
        GuiButton guiButton2 = guiButton = new GuiButton(81, 5, j + 168, 90, 20, stylisedAlts ? "Stylised" : "Legecy");
        List list = this.field_146292_n;
        boolean bl = false;
        this.stylisedAltsButton = it;
        list.add(guiButton);
        it = guiButton = new GuiButton(82, 5, j + 192, 90, 20, unformattedAlts ? "UNFORMATTEDALTS" : "FORMATTEDALTS");
        list = this.field_146292_n;
        boolean bl2 = false;
        this.unformattedAltsButton = it;
        list.add(guiButton);
        it = guiButton = new GuiSlider(-1, 5, j + 216, 90, 20, "length (", ")", 6.0, 16.0, (double)altsLength, false, true, GuiAltManager::initGui$lambda-2);
        list = this.field_146292_n;
        boolean bl3 = false;
        this.altsSlider = it;
        list.add(guiButton);
        this.field_146292_n.add(new GuiButton(123, 5, j + 240, 90, 20, "TokenLogin"));
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        int h = this.field_146295_m;
        int w = this.field_146294_l;
        float xDiff = ((float)(mouseX - h / 2) - this.currentX) / (float)new ScaledResolution(this.field_146297_k).func_78325_e();
        float yDiff = ((float)(mouseY - w / 2) - this.currentY) / (float)new ScaledResolution(this.field_146297_k).func_78325_e();
        this.currentX += xDiff * 0.3f;
        this.currentY += yDiff * 0.3f;
        GlStateManager.func_179109_b((float)(this.currentX / 30.0f), (float)(this.currentY / 15.0f), (float)0.0f);
        RenderUtils.drawImage(new ResourceLocation("crosssine/background.png"), -30, -30, this.field_146294_l + 60, this.field_146295_m + 60);
        GlStateManager.func_179109_b((float)(-this.currentX / 30.0f), (float)(-this.currentY / 15.0f), (float)0.0f);
        ParticleUtils.INSTANCE.drawParticles(mouseX, mouseY);
        GuiList guiList = this.altsList;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        guiList.func_148128_a(mouseX, mouseY, partialTicks);
        Fonts.fontTenacityBold35.drawCenteredString("AltManager", this.field_146294_l / 2, 6.0f, 0xFFFFFF);
        Fonts.fontTenacityBold35.drawCenteredString("Alts", this.field_146294_l / 2, 18.0f, 0xFFFFFF);
        Fonts.fontTenacityBold35.drawCenteredString(this.status, this.field_146294_l / 2, 32.0f, 0xFFFFFF);
        Fonts.fontTenacityBold35.func_175063_a(Intrinsics.stringPlus("UserName : ", this.field_146297_k.func_110432_I().func_111285_a()), 6.0f, 6.0f, 0xFFFFFF);
        Fonts.fontTenacityBold35.func_175063_a(this.field_146297_k.func_110432_I().func_148254_d().length() >= 32 ? "Premuim" : "Cracked", 6.0f, 15.0f, 0xFFFFFF);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    /*
     * Unable to fully structure code
     */
    protected void func_146284_a(@NotNull GuiButton button) {
        Intrinsics.checkNotNullParameter(button, "button");
        if (!button.field_146124_l) {
            return;
        }
        switch (button.field_146127_k) {
            case 0: {
                this.field_146297_k.func_147108_a(this.prevGui);
                break;
            }
            case 1: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiAdd(this));
                break;
            }
            case 2: {
                v0 = this.altsList;
                if (v0 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    v0 = null;
                }
                if (v0.getSelectedSlot() == -1) ** GOTO lbl-1000
                v1 = this.altsList;
                if (v1 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    v1 = null;
                }
                v2 = v1.getSelectedSlot();
                v3 = this.altsList;
                if (v3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    v3 = null;
                }
                if (v2 < v3.func_148127_b()) {
                    v4 = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
                    v5 = this.altsList;
                    if (v5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        v5 = null;
                    }
                    v4.remove(v5.getSelectedSlot());
                    CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
                    v6 = "\u00a7aRemove";
                } else lbl-1000:
                // 2 sources

                {
                    v6 = "\u00a7cNeed Select";
                }
                this.status = v6;
                break;
            }
            case 3: {
                v7 = this.altsList;
                if (v7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    v7 = null;
                }
                if (v7.getSelectedSlot() != -1) {
                    v8 = this.altsList;
                    if (v8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        v8 = null;
                    }
                    v9 = v8.getSelectedSlot();
                    v10 = this.altsList;
                    if (v10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        v10 = null;
                    }
                    if (v9 < v10.func_148127_b()) {
                        new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, actionPerformed$lambda-4(net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager ), ()V)((GuiAltManager)this)).start();
                        break;
                    }
                }
                this.status = "\u00a7cNeed Select";
                break;
            }
            case 4: {
                if (CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size() <= 0) {
                    this.status = "\u00a7cEmpty List";
                    return;
                }
                randomInteger = new Random().nextInt(CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size());
                v11 = this.altsList;
                if (v11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    v11 = null;
                }
                if (randomInteger < v11.func_148127_b()) {
                    v12 = this.altsList;
                    if (v12 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        v12 = null;
                    }
                    v12.setSelectedSlot(randomInteger);
                }
                new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, actionPerformed$lambda-5(int net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager ), ()V)((int)randomInteger, (GuiAltManager)this)).start();
                break;
            }
            case 6: {
                this.field_146297_k.func_147108_a((GuiScreen)new GuiDirectLogin(this));
                break;
            }
            case 89: {
                new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, actionPerformed$lambda-6(), ()V)()).start();
                break;
            }
            case 92: {
                this.field_146297_k.func_147108_a((GuiScreen)new MicrosoftLogin(this));
                break;
            }
            case 82: {
                GuiAltManager.unformattedAlts = GuiAltManager.unformattedAlts == false;
                v13 = this.unformattedAltsButton;
                if (v13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("unformattedAltsButton");
                    v13 = null;
                }
                v13.field_146126_j = GuiAltManager.unformattedAlts != false ? "UNFORMATTEDALTS" : "FORMATTEDALTS";
                break;
            }
            case 81: {
                GuiAltManager.stylisedAlts = GuiAltManager.stylisedAlts == false;
                v14 = this.stylisedAltsButton;
                if (v14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("stylisedAltsButton");
                    v14 = null;
                }
                v14.field_146126_j = GuiAltManager.stylisedAlts != false ? "Stylised" : "Legecy";
                break;
            }
            case 93: {
                new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, actionPerformed$lambda-7(net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager ), ()V)((GuiAltManager)this)).start();
                break;
            }
            case 123: {
                this.field_146297_k.func_147108_a((GuiScreen)new SessionGui(this));
            }
        }
    }

    public void func_73876_c() {
        super.func_73876_c();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        switch (keyCode) {
            case 1: {
                CrossSine.INSTANCE.getFileManager().saveConfig(CrossSine.INSTANCE.getFileManager().getSpecialConfig());
                this.field_146297_k.func_147108_a(this.prevGui);
                return;
            }
            case 200: {
                GuiList guiList;
                int i;
                GuiList guiList2 = this.altsList;
                if (guiList2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList2 = null;
                }
                if ((i = guiList2.getSelectedSlot() - 1) < 0) {
                    i = 0;
                }
                if ((guiList = this.altsList) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                guiList.func_148144_a(i, false, 0, 0);
                break;
            }
            case 208: {
                GuiList guiList;
                GuiList guiList3 = this.altsList;
                if (guiList3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList3 = null;
                }
                int i = guiList3.getSelectedSlot() + 1;
                GuiList guiList4 = this.altsList;
                if (guiList4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList4 = null;
                }
                if (i >= guiList4.func_148127_b()) {
                    GuiList guiList5 = this.altsList;
                    if (guiList5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("altsList");
                        guiList5 = null;
                    }
                    i = guiList5.func_148127_b() - 1;
                }
                if ((guiList = this.altsList) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                guiList.func_148144_a(i, false, 0, 0);
                break;
            }
            case 28: {
                GuiList guiList;
                GuiList guiList6 = this.altsList;
                if (guiList6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList6 = null;
                }
                if ((guiList = this.altsList) == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                guiList6.func_148144_a(guiList.getSelectedSlot(), true, 0, 0);
                break;
            }
            case 209: {
                GuiList guiList = this.altsList;
                if (guiList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                guiList.func_148145_f(this.field_146295_m - 100);
                break;
            }
            case 201: {
                GuiList guiList = this.altsList;
                if (guiList == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList = null;
                }
                guiList.func_148145_f(-this.field_146295_m + 100);
                return;
            }
        }
        super.func_73869_a(typedChar, keyCode);
    }

    public void func_146274_d() {
        super.func_146274_d();
        GuiList guiList = this.altsList;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        guiList.func_178039_p();
    }

    private static final void initGui$lambda-2(GuiSlider it) {
        altsLength = it.getValueInt();
    }

    private static final void actionPerformed$lambda-4(GuiAltManager this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
        List<MinecraftAccount> list = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
        GuiList guiList = this$0.altsList;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("altsList");
            guiList = null;
        }
        MinecraftAccount minecraftAccount = list.get(guiList.getSelectedSlot());
        this$0.status = "\u00a7aLogging in";
        this$0.status = Companion.login(minecraftAccount);
    }

    private static final void actionPerformed$lambda-5(int $randomInteger, GuiAltManager this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
        MinecraftAccount minecraftAccount = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().get($randomInteger);
        this$0.status = "\u00a7aLoggin in";
        this$0.status = Companion.login(minecraftAccount);
    }

    private static final void actionPerformed$lambda-6() {
        LoginUtils.INSTANCE.randomCracked();
    }

    private static final void actionPerformed$lambda-7(GuiAltManager this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
        this$0.status = EnumChatFormatting.YELLOW + "Waiting for login...";
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        JFileChooser chooser = new JFileChooser();
        String[] stringArray = new String[]{"txt"};
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", stringArray);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == 0) {
            try {
                this$0.status = EnumChatFormatting.YELLOW + "Logging in...";
                LoginData loginData = CookieUtil.INSTANCE.loginWithCookie(chooser.getSelectedFile());
                if (loginData == null) {
                    this$0.status = EnumChatFormatting.RED + "Failed to login with cookie!";
                    return;
                }
                this$0.status = EnumChatFormatting.GREEN + "Logged in to " + loginData.username;
                this$0.field_146297_k.field_71449_j = new net.minecraft.util.Session(loginData.username, loginData.uuid, loginData.mcToken, "legacy");
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static {
        net.minecraft.util.Session session = Minecraft.func_71410_x().field_71449_j;
        Intrinsics.checkNotNullExpressionValue(session, "getMinecraft().session");
        originalSession = session;
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0014J8\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006H\u0014J(\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0019\u001a\u00020\u0006H\u0016J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u0006H\u0014R\u001c\u0010\u0005\u001a\u00020\u00068FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u001b"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$GuiList;", "Lnet/minecraft/client/gui/GuiSlot;", "prevGui", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;Lnet/minecraft/client/gui/GuiScreen;)V", "selectedSlot", "", "getSelectedSlot", "()I", "setSelectedSlot", "(I)V", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "var1", "doubleClick", "", "var3", "getSize", "isSelected", "CrossSine"})
    private final class GuiList
    extends GuiSlot {
        private int selectedSlot;

        public GuiList(GuiScreen prevGui) {
            Intrinsics.checkNotNullParameter((Object)GuiAltManager.this, "this$0");
            Intrinsics.checkNotNullParameter(prevGui, "prevGui");
            super(GuiAltManager.this.field_146297_k, prevGui.field_146294_l, prevGui.field_146295_m, 40, prevGui.field_146295_m - 40, 30);
        }

        public final int getSelectedSlot() {
            if (this.selectedSlot > CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size()) {
                this.selectedSlot = -1;
            }
            return this.selectedSlot;
        }

        public final void setSelectedSlot(int n) {
            this.selectedSlot = n;
        }

        protected boolean func_148131_a(int id) {
            return this.getSelectedSlot() == id;
        }

        public int func_148127_b() {
            return CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().size();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void func_148144_a(int var1, boolean doubleClick, int var3, int var4) {
            this.selectedSlot = var1;
            if (!doubleClick) return;
            GuiList guiList = GuiAltManager.this.altsList;
            if (guiList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("altsList");
                guiList = null;
            }
            if (guiList.getSelectedSlot() != -1) {
                GuiList guiList2 = GuiAltManager.this.altsList;
                if (guiList2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList2 = null;
                }
                int n = guiList2.getSelectedSlot();
                GuiList guiList3 = GuiAltManager.this.altsList;
                if (guiList3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("altsList");
                    guiList3 = null;
                }
                if (n < guiList3.func_148127_b()) {
                    new Thread(() -> GuiList.elementClicked$lambda-0(GuiAltManager.this)).start();
                    return;
                }
            }
            GuiAltManager.this.setStatus("\u00a7cNeed Select");
        }

        protected void func_180791_a(int id, int x, int y, int var4, int var5, int var6) {
            MinecraftAccount minecraftAccount = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts().get(id);
            Fonts.fontTenacityBold35.drawCenteredString(minecraftAccount.getName(), (float)this.field_148155_a / 2.0f, (float)y + 2.0f, Color.WHITE.getRGB(), true);
            Fonts.fontTenacityBold35.drawCenteredString(minecraftAccount.getType(), (float)this.field_148155_a / 2.0f, (float)y + 15.0f, Color.LIGHT_GRAY.getRGB(), true);
        }

        protected void func_148123_a() {
        }

        private static final void elementClicked$lambda-0(GuiAltManager this$0) {
            Intrinsics.checkNotNullParameter((Object)this$0, "this$0");
            List<MinecraftAccount> list = CrossSine.INSTANCE.getFileManager().getAccountsConfig().getAltManagerMinecraftAccounts();
            GuiList guiList = this$0.altsList;
            if (guiList == null) {
                Intrinsics.throwUninitializedPropertyAccessException("altsList");
                guiList = null;
            }
            MinecraftAccount minecraftAccount = list.get(guiList.getSelectedSlot());
            this$0.setStatus("\u00a7aLoggin in");
            this$0.setStatus(Intrinsics.stringPlus("\u00a7c", Companion.login(minecraftAccount)));
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager$Companion;", "", "()V", "altsLength", "", "getAltsLength", "()I", "setAltsLength", "(I)V", "originalSession", "Lnet/minecraft/util/Session;", "getOriginalSession", "()Lnet/minecraft/util/Session;", "setOriginalSession", "(Lnet/minecraft/util/Session;)V", "stylisedAlts", "", "getStylisedAlts", "()Z", "setStylisedAlts", "(Z)V", "unformattedAlts", "getUnformattedAlts", "setUnformattedAlts", "login", "", "account", "Lme/liuli/elixir/account/MinecraftAccount;", "CrossSine"})
    public static final class Companion {
        private Companion() {
        }

        public final int getAltsLength() {
            return altsLength;
        }

        public final void setAltsLength(int n) {
            altsLength = n;
        }

        public final boolean getUnformattedAlts() {
            return unformattedAlts;
        }

        public final void setUnformattedAlts(boolean bl) {
            unformattedAlts = bl;
        }

        public final boolean getStylisedAlts() {
            return stylisedAlts;
        }

        public final void setStylisedAlts(boolean bl) {
            stylisedAlts = bl;
        }

        @NotNull
        public final net.minecraft.util.Session getOriginalSession() {
            return originalSession;
        }

        public final void setOriginalSession(@NotNull net.minecraft.util.Session session) {
            Intrinsics.checkNotNullParameter(session, "<set-?>");
            originalSession = session;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final String login(@NotNull MinecraftAccount account) {
            String string;
            Intrinsics.checkNotNullParameter(account, "account");
            try {
                void it;
                Minecraft mc = Minecraft.func_71410_x();
                Session session = account.getSession();
                Minecraft minecraft = mc;
                boolean bl = false;
                minecraft.field_71449_j = new net.minecraft.util.Session(it.getUsername(), it.getUuid(), it.getToken(), it.getType());
                CrossSine.INSTANCE.getEventManager().callEvent(new SessionEvent());
                string = Intrinsics.stringPlus("Name Chagned\u00a7F", mc.field_71449_j.func_111285_a());
            }
            catch (Exception e) {
                e.printStackTrace();
                string = "ERROR";
            }
            return string;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

