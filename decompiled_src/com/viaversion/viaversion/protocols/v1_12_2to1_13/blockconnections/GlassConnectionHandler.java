/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.AbstractFenceConnectionHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import java.util.ArrayList;
import java.util.List;

public class GlassConnectionHandler
extends AbstractFenceConnectionHandler {
    static List<ConnectionData.ConnectorInitAction> init() {
        ArrayList<ConnectionData.ConnectorInitAction> actions = new ArrayList<ConnectionData.ConnectorInitAction>(18);
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:white_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:orange_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:magenta_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:light_blue_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:yellow_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:lime_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:pink_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:gray_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:light_gray_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:cyan_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:purple_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:blue_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:brown_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:green_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:red_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:black_stained_glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:glass_pane"));
        actions.add(new GlassConnectionHandler("pane").getInitAction("minecraft:iron_bars"));
        return actions;
    }

    public GlassConnectionHandler(String blockConnections) {
        super(blockConnections);
    }

    @Override
    protected byte getStates(UserConnection user, BlockPosition position) {
        int states = super.getStates(user, position);
        if (states != 0) {
            return (byte)states;
        }
        ProtocolInfo protocolInfo = user.getProtocolInfo();
        return (byte)(protocolInfo.serverProtocolVersion().olderThanOrEqualTo(ProtocolVersion.v1_8) && protocolInfo.serverProtocolVersion().isKnown() ? 15 : states);
    }
}

