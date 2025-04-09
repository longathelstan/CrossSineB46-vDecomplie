/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.Check;
import net.ccbluex.liquidbounce.features.module.modules.world.hackercheck.CheckManager;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.world.World;

@ModuleInfo(name="HackerDetector", category=ModuleCategory.WORLD)
public class HackerDetector
extends Module {
    public static final HackerDetector INSTANCE = new HackerDetector();
    public final ConcurrentHashMap<Integer, CheckManager> playersChecks = new ConcurrentHashMap();
    private final BoolValue warningValue = new BoolValue("Warning", true);
    private final IntegerValue warningVLValue = new IntegerValue("WarningVL", 30, 20, 400);
    private final BoolValue alertValue = new BoolValue("Alert", false);
    private final BoolValue debugValue = new BoolValue("Debug", false){

        @Override
        protected void onChanged(Boolean oldValue, Boolean newValue) {
            Check.debug = newValue;
        }
    };
    public final BoolValue autoBlockValue = new BoolValue("AutoBlock", true);
    public final BoolValue reachValue = new BoolValue("Reach", true);
    public final BoolValue velocityValue = new BoolValue("Velocity", true);
    public final BoolValue noSlowValue = new BoolValue("NoSlow", true);
    public final BoolValue scaffoldValue = new BoolValue("Scaffold", true);
    public final BoolValue rotationValue = new BoolValue("Rotation", true);
    private final BoolValue checkName = new BoolValue("Check-Name", false);
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private List<EntityPlayer> playerList = new ArrayList<EntityPlayer>();
    private final String dictionary = HttpUtils.INSTANCE.get("https://raw.githubusercontent.com/dwyl/english-words/refs/heads/master/words.txt");

    @EventTarget
    public final void onRender2D(Render2DEvent event) {
        if (((Boolean)this.checkName.get()).booleanValue()) {
            for (EntityPlayer player : HackerDetector.mc.field_71441_e.field_73010_i) {
                if (!EntityUtils.INSTANCE.isSelected((Entity)player, true) || this.playerList.contains(player) || player == HackerDetector.mc.field_71439_g) continue;
                try {
                    ClientUtils.INSTANCE.displayChatMessage("\u00a7l\u00a77[\u00a7l\u00a79HackDetector\u00a7l\u00a77]\u00a7r Checking " + player.func_70005_c_());
                    if (player.func_70005_c_().contains(this.dictionary)) {
                        ClientUtils.INSTANCE.displayChatMessage("\u00a7l\u00a77[\u00a7l\u00a79HackDetector\u00a7l\u00a77]\u00a7r Successfully");
                        this.playerList.add(player);
                        continue;
                    }
                    ClientUtils.INSTANCE.displayChatMessage("\u00a7l\u00a77[\u00a7l\u00a79HackDetector\u00a7l\u00a77]\u00a7r " + player.func_70005_c_() + " is suspicious");
                    this.playerList.add(player);
                }
                catch (Throwable throwable) {
                    ClientUtils.INSTANCE.logError("Failed to check player", throwable);
                    ClientUtils.INSTANCE.displayChatMessage("Failed to check player");
                }
            }
        } else if (!this.playerList.isEmpty()) {
            this.playerList.clear();
        }
        this.singleThreadExecutor.execute(() -> {
            for (CheckManager manager : this.playersChecks.values()) {
                manager.livingUpdate();
            }
            Enumeration<Integer> iter = this.playersChecks.keys();
            LinkedList<Integer> cache = new LinkedList<Integer>();
            while (iter.hasMoreElements()) {
                Integer i = iter.nextElement();
                Entity e = HackerDetector.mc.field_71441_e.func_73045_a(i.intValue());
                if (e != null && !e.field_70128_L) continue;
                cache.add(i);
            }
            for (Integer i : cache) {
                this.playersChecks.remove(i);
            }
            for (EntityPlayer player : HackerDetector.mc.field_71441_e.field_73010_i) {
                if (!(player instanceof EntityOtherPlayerMP) || this.playersChecks.containsKey(player.func_145782_y()) || player.field_70128_L || player.func_145782_y() == HackerDetector.mc.field_71439_g.func_145782_y()) continue;
                this.playersChecks.put(player.func_145782_y(), new CheckManager((EntityOtherPlayerMP)player));
            }
        });
    }

    @EventTarget
    public final void onPacket(PacketEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getPacket() instanceof S14PacketEntity || event.getPacket() instanceof S18PacketEntityTeleport) {
            this.singleThreadExecutor.execute(() -> {
                int id;
                int z;
                int y;
                int x;
                if (event.getPacket() instanceof S14PacketEntity) {
                    S14PacketEntity packet = (S14PacketEntity)event.getPacket();
                    x = packet.func_149062_c();
                    y = packet.func_149061_d();
                    z = packet.func_149064_e();
                    id = packet.func_149065_a((World)HackerDetector.mc.field_71441_e).func_145782_y();
                } else {
                    S18PacketEntityTeleport packet = (S18PacketEntityTeleport)event.getPacket();
                    Entity entityIn = HackerDetector.mc.field_71441_e.func_73045_a(packet.func_149451_c());
                    x = packet.func_149449_d() - entityIn.field_70118_ct;
                    y = packet.func_149448_e() - entityIn.field_70117_cu;
                    z = packet.func_149446_f() - entityIn.field_70116_cv;
                    id = packet.func_149451_c();
                }
                this.playersChecks.get(id).positionUpdate((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
            });
        }
    }

    @EventTarget
    public final void onWorld(WorldEvent event) {
        this.playerList.clear();
    }

    @Override
    public void onEnable() {
        Check.debug = (Boolean)this.debugValue.get();
    }

    @Override
    public void onDisable() {
        this.playersChecks.clear();
    }

    public static String completeMessage(String player, String module, double vl, String value) {
        value = value.replaceAll("%player%", player);
        value = value.replaceAll("%module%", module);
        value = value.replaceAll("%vl%", String.valueOf(vl));
        return value;
    }

    public void warning(String player, String module, double vl) {
        ClientUtils.INSTANCE.displayChatMessage("\u00a7l\u00a77[\u00a7l\u00a79HackDetector\u00a7l\u00a77]\u00a7r " + HackerDetector.completeMessage(player, module, vl, "%player% detected for \u00a7C%module% \u00a7F(\u00a77%vl%\u00a7F)"));
        HackerDetector.mc.field_71439_g.func_85030_a("note.pling", 1.0f, 1.0f);
    }

    public boolean shouldWarning() {
        return (Boolean)this.warningValue.get();
    }

    public boolean reachedVL(double totalVL) {
        return totalVL >= (double)((Integer)this.warningVLValue.get()).intValue();
    }

    public static boolean catchPlayer(String player, String module, double totalVL) {
        if (INSTANCE.reachedVL(totalVL)) {
            if (INSTANCE.shouldWarning()) {
                INSTANCE.warning(player, module, totalVL);
            }
            return true;
        }
        return false;
    }

    public static boolean shouldAlert() {
        return (Boolean)HackerDetector.INSTANCE.alertValue.get();
    }
}

