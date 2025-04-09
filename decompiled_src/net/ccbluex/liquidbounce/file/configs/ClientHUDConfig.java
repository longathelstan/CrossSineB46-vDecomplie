/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.client.ArrayListModule;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.KeyStrokes;
import net.ccbluex.liquidbounce.features.module.modules.client.RotationModule;
import net.ccbluex.liquidbounce.features.module.modules.client.ScoreboardModule;
import net.ccbluex.liquidbounce.features.module.modules.client.SessionInfo;
import net.ccbluex.liquidbounce.features.module.modules.client.TargetHUD;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/ClientHUDConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "config", "", "saveConfig", "CrossSine"})
public final class ClientHUDConfig
extends FileConfig {
    public ClientHUDConfig(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        super(file);
    }

    @Override
    public void loadConfig(@NotNull String config) {
        String string;
        Intrinsics.checkNotNullParameter(config, "config");
        JsonObject json = new JsonParser().parse(config).getAsJsonObject();
        if (json.has("ArrayList-State")) {
            ArrayListModule.INSTANCE.setState(json.get("ArrayList-State").getAsBoolean());
        }
        if (json.has("ArrayList-Background")) {
            ArrayListModule.INSTANCE.getBackgroundValue().set(json.get("ArrayList-Background").getAsBoolean());
        }
        if (json.has("ArrayList-Rounded")) {
            ArrayListModule.INSTANCE.getRounded().set(json.get("ArrayList-Rounded").getAsBoolean());
        }
        if (json.has("ArrayList-Mode-Rounded")) {
            Value<String> value = ArrayListModule.INSTANCE.getModeList();
            string = json.get("ArrayList-Mode-Rounded").getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "json.get(\"ArrayList-Mode-Rounded\").asString");
            value.set(string);
        }
        if (json.has("ArrayList-Rect-Rounded")) {
            ArrayListModule.INSTANCE.getRectRound().set(json.get("ArrayList-Rect-Rounded").getAsBoolean());
        }
        if (json.has("ArrayList-Rect")) {
            Value<String> value = ArrayListModule.INSTANCE.getRectValue();
            string = json.get("ArrayList-Rect").getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "json.get(\"ArrayList-Rect\").asString");
            value.set(string);
        }
        if (json.has("ArrayList-Animation")) {
            ListValue listValue = ArrayListModule.INSTANCE.getAnimationMode();
            string = json.get("ArrayList-Animation").getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "json.get(\"ArrayList-Animation\").asString");
            listValue.set(string);
        }
        if (json.has("ArrayList-AnimationSpeed")) {
            ArrayListModule.INSTANCE.getAnimationSpeed().set(Float.valueOf(json.get("ArrayList-AnimationSpeed").getAsFloat()));
        }
        if (json.has("ArrayList-Case")) {
            ListValue listValue = ArrayListModule.INSTANCE.getCaseValue();
            string = json.get("ArrayList-Case").getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "json.get(\"ArrayList-Case\").asString");
            listValue.set(string);
        }
        if (json.has("ArrayList-Render")) {
            ArrayListModule.INSTANCE.getNoRender().set(json.get("ArrayList-Render").getAsBoolean());
        }
        if (json.has("Scoreboard-State")) {
            ScoreboardModule.INSTANCE.setState(json.get("Scoreboard-State").getAsBoolean());
        }
        if (json.has("Scoreboard-Shadow")) {
            ScoreboardModule.INSTANCE.getTextShadow().set(json.get("Scoreboard-Shadow").getAsBoolean());
        }
        if (json.has("Scoreboard-Number")) {
            ScoreboardModule.INSTANCE.getShowNumber().set(json.get("Scoreboard-Number").getAsBoolean());
        }
        if (json.has("Scoreboard-PosX")) {
            ScoreboardModule.INSTANCE.setPosX(json.get("Scoreboard-PosX").getAsFloat());
        }
        if (json.has("Scoreboard-PosY")) {
            ScoreboardModule.INSTANCE.setPosY(json.get("Scoreboard-PosY").getAsFloat());
        }
        if (json.has("TargetHUD-State")) {
            TargetHUD.INSTANCE.setState(json.get("TargetHUD-State").getAsBoolean());
        }
        if (json.has("TargetHUD-Mode")) {
            ListValue listValue = TargetHUD.INSTANCE.getMode();
            string = json.get("TargetHUD-Mode").getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "json.get(\"TargetHUD-Mode\").asString");
            listValue.set(string);
        }
        if (json.has("TargetHUD-Follow")) {
            TargetHUD.INSTANCE.getFollowTarget().set(json.get("TargetHUD-Follow").getAsBoolean());
        }
        if (json.has("TargetHUD-PosX")) {
            TargetHUD.INSTANCE.setPosX(json.get("TargetHUD-PosX").getAsFloat());
        }
        if (json.has("TargetHUD-PosY")) {
            TargetHUD.INSTANCE.setPosY(json.get("TargetHUD-PosY").getAsFloat());
        }
        if (json.has("KeyStrokes-State")) {
            KeyStrokes.INSTANCE.setState(json.get("KeyStrokes-State").getAsBoolean());
        }
        if (json.has("KeyStrokes-Color")) {
            KeyStrokes.INSTANCE.getKeyColor().set(json.get("KeyStrokes-Color").getAsBoolean());
        }
        if (json.has("KeyStrokes-Mouse")) {
            KeyStrokes.INSTANCE.getShowMouse().set(json.get("KeyStrokes-Mouse").getAsBoolean());
        }
        if (json.has("KeyStrokes-CPS")) {
            KeyStrokes.INSTANCE.getShowCPS().set(json.get("KeyStrokes-CPS").getAsBoolean());
        }
        if (json.has("KeyStrokes-Space")) {
            KeyStrokes.INSTANCE.getShowSpace().set(json.get("KeyStrokes-Space").getAsBoolean());
        }
        if (json.has("KeyStrokes-Line")) {
            KeyStrokes.INSTANCE.getLineSpace().set(json.get("KeyStrokes-Line").getAsBoolean());
        }
        if (json.has("KeyStrokes-Rounded")) {
            KeyStrokes.INSTANCE.getRoundValue().set(Float.valueOf(json.get("KeyStrokes-Rounded").getAsFloat()));
        }
        if (json.has("KeyStrokes-Background")) {
            KeyStrokes.INSTANCE.getBackgroundAlpha().set(Float.valueOf(json.get("KeyStrokes-Background").getAsFloat()));
        }
        if (json.has("KeyStrokes-Size")) {
            KeyStrokes.INSTANCE.getSizeBox().set(Float.valueOf(json.get("KeyStrokes-Size").getAsFloat()));
        }
        if (json.has("KeyStrokes-posX")) {
            KeyStrokes.INSTANCE.setPosX(json.get("KeyStrokes-posX").getAsFloat());
        }
        if (json.has("KeyStrokes-posY")) {
            KeyStrokes.INSTANCE.setPosY(json.get("KeyStrokes-posY").getAsFloat());
        }
        if (json.has("Interface-State")) {
            Interface.INSTANCE.setState(json.get("Interface-State").getAsBoolean());
        }
        if (json.has("Interface-DynamicIsland")) {
            Interface.INSTANCE.getDynamicIsland().set(json.get("Interface-DynamicIsland").getAsBoolean());
        }
        if (json.has("Interface-Notification")) {
            Interface.INSTANCE.getNotification().set(json.get("Interface-Notification").getAsBoolean());
        }
        if (json.has("Interface-InvAnimation")) {
            Interface.INSTANCE.getInventoryAnimation().set(json.get("Interface-InvAnimation").getAsBoolean());
        }
        if (json.has("Interface-Shader")) {
            Interface.INSTANCE.getShaderValue().set(json.get("Interface-Shader").getAsBoolean());
        }
        if (json.has("Interface-Dynamic")) {
            Interface.INSTANCE.getDynamicIsland().set(json.get("Interface-Dynamic").getAsBoolean());
        }
        if (json.has("Interface-HotBar")) {
            Interface.INSTANCE.getRoundedHotbar().set(json.get("Interface-HotBar").getAsBoolean());
        }
        if (json.has("Interface-PlayerState")) {
            Interface.INSTANCE.getPlayerStats().set(json.get("Interface-PlayerState").getAsBoolean());
        }
        if (json.has("Session-PosX")) {
            SessionInfo.INSTANCE.setPosX(json.get("Session-PosX").getAsFloat());
        }
        if (json.has("Session-PosY")) {
            SessionInfo.INSTANCE.setPosY(json.get("Session-PosY").getAsFloat());
        }
        if (json.has("RotationState")) {
            RotationModule.INSTANCE.setState(json.get("RotationState").getAsBoolean());
        }
        if (json.has("RotationSmooth")) {
            RotationModule.INSTANCE.getSmoothValue().set(json.get("RotationSmooth").getAsBoolean());
        }
    }

    @Override
    @NotNull
    public String saveConfig() {
        JsonObject json = new JsonObject();
        json.addProperty("ArrayList-State", Boolean.valueOf(ArrayListModule.INSTANCE.getState()));
        json.addProperty("ArrayList-Background", (Boolean)ArrayListModule.INSTANCE.getBackgroundValue().get());
        json.addProperty("ArrayList-Rounded", ArrayListModule.INSTANCE.getRounded().get());
        json.addProperty("ArrayList-Rect-Rounded", ArrayListModule.INSTANCE.getRectRound().get());
        json.addProperty("ArrayList-Mode-Rounded", ArrayListModule.INSTANCE.getModeList().get());
        json.addProperty("ArrayList-Rect", ArrayListModule.INSTANCE.getRectValue().get());
        json.addProperty("ArrayList-Case", (String)ArrayListModule.INSTANCE.getCaseValue().get());
        json.addProperty("ArrayList-Animation", (String)ArrayListModule.INSTANCE.getAnimationMode().get());
        json.addProperty("ArrayList-AnimationSpeed", (Number)ArrayListModule.INSTANCE.getAnimationSpeed().get());
        json.addProperty("ArrayList-Render", (Boolean)ArrayListModule.INSTANCE.getNoRender().get());
        json.addProperty("Scoreboard-State", Boolean.valueOf(ScoreboardModule.INSTANCE.getState()));
        json.addProperty("Scoreboard-Shadow", (Boolean)ScoreboardModule.INSTANCE.getTextShadow().get());
        json.addProperty("Scoreboard-Number", (Boolean)ScoreboardModule.INSTANCE.getShowNumber().get());
        json.addProperty("Scoreboard-PosX", (Number)Float.valueOf(ScoreboardModule.INSTANCE.getPosX()));
        json.addProperty("Scoreboard-PosY", (Number)Float.valueOf(ScoreboardModule.INSTANCE.getPosX()));
        json.addProperty("TargetHUD-State", Boolean.valueOf(TargetHUD.INSTANCE.getState()));
        json.addProperty("TargetHUD-Mode", (String)TargetHUD.INSTANCE.getMode().get());
        json.addProperty("TargetHUD-Follow", (Boolean)TargetHUD.INSTANCE.getFollowTarget().get());
        json.addProperty("TargetHUD-PosX", (Number)Float.valueOf(TargetHUD.INSTANCE.getPosX()));
        json.addProperty("TargetHUD-PosY", (Number)Float.valueOf(TargetHUD.INSTANCE.getPosY()));
        json.addProperty("KeyStrokes-State", Boolean.valueOf(KeyStrokes.INSTANCE.getState()));
        json.addProperty("KeyStrokes-Color", (Boolean)KeyStrokes.INSTANCE.getKeyColor().get());
        json.addProperty("KeyStrokes-Mouse", (Boolean)KeyStrokes.INSTANCE.getShowMouse().get());
        json.addProperty("KeyStrokes-CPS", KeyStrokes.INSTANCE.getShowCPS().get());
        json.addProperty("KeyStrokes-Space", (Boolean)KeyStrokes.INSTANCE.getShowSpace().get());
        json.addProperty("KeyStrokes-Line", KeyStrokes.INSTANCE.getLineSpace().get());
        json.addProperty("KeyStrokes-Rounded", (Number)KeyStrokes.INSTANCE.getRoundValue().get());
        json.addProperty("KeyStrokes-Background", (Number)KeyStrokes.INSTANCE.getBackgroundAlpha().get());
        json.addProperty("KeyStrokes-Size", (Number)KeyStrokes.INSTANCE.getSizeBox().get());
        json.addProperty("KeyStrokes-posX", (Number)Float.valueOf(KeyStrokes.INSTANCE.getPosX()));
        json.addProperty("KeyStrokes-posY", (Number)Float.valueOf(KeyStrokes.INSTANCE.getPosY()));
        json.addProperty("Interface-State", Boolean.valueOf(Interface.INSTANCE.getState()));
        json.addProperty("Interface-DynamicIsland", (Boolean)Interface.INSTANCE.getDynamicIsland().get());
        json.addProperty("Interface-Notification", (Boolean)Interface.INSTANCE.getNotification().get());
        json.addProperty("Interface-InvAnimation", (Boolean)Interface.INSTANCE.getInventoryAnimation().get());
        json.addProperty("Interface-Shader", (Boolean)Interface.INSTANCE.getShaderValue().get());
        json.addProperty("Interface-Dynamic", (Boolean)Interface.INSTANCE.getDynamicIsland().get());
        json.addProperty("Interface-HotBar", (Boolean)Interface.INSTANCE.getRoundedHotbar().get());
        json.addProperty("Interface-PlayerState", (Boolean)Interface.INSTANCE.getPlayerStats().get());
        json.addProperty("Session-PosX", (Number)Float.valueOf(SessionInfo.INSTANCE.getPosX()));
        json.addProperty("Session-PosY", (Number)Float.valueOf(SessionInfo.INSTANCE.getPosY()));
        json.addProperty("RotationState", Boolean.valueOf(RotationModule.INSTANCE.getState()));
        json.addProperty("RotationSmooth", (Boolean)RotationModule.INSTANCE.getSmoothValue().get());
        String string = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
        Intrinsics.checkNotNullExpressionValue(string, "FileManager.PRETTY_GSON.toJson(json)");
        return string;
    }
}

