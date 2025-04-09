/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.BlockData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.StairConnectionHandler;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.blockconnections.WrappedBlockData;
import java.util.Arrays;

public abstract class AbstractFenceConnectionHandler
implements ConnectionHandler {
    private static final StairConnectionHandler STAIR_CONNECTION_HANDLER = new StairConnectionHandler();
    private final IntSet blockStates = new IntOpenHashSet();
    private final int[] connectedBlockStates = new int[this.statesSize()];
    private final int blockConnectionsTypeId;

    protected AbstractFenceConnectionHandler(String blockConnections) {
        this.blockConnectionsTypeId = blockConnections != null ? BlockData.connectionTypeId(blockConnections) : -1;
        Arrays.fill(this.connectedBlockStates, -1);
    }

    ConnectionData.ConnectorInitAction getInitAction(String key) {
        AbstractFenceConnectionHandler handler = this;
        return blockData -> {
            if (key.equals(blockData.getMinecraftKey())) {
                if (blockData.hasData("waterlogged") && blockData.getValue("waterlogged").equals("true")) {
                    return;
                }
                this.blockStates.add(blockData.getSavedBlockStateId());
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                byte internalStateId = this.getStates(blockData);
                this.connectedBlockStates[internalStateId] = blockData.getSavedBlockStateId();
            }
        };
    }

    protected byte getStates(WrappedBlockData blockData) {
        byte states = 0;
        if (blockData.getValue("east").equals("true")) {
            states = (byte)(states | 1);
        }
        if (blockData.getValue("north").equals("true")) {
            states = (byte)(states | 2);
        }
        if (blockData.getValue("south").equals("true")) {
            states = (byte)(states | 4);
        }
        if (blockData.getValue("west").equals("true")) {
            states = (byte)(states | 8);
        }
        return states;
    }

    protected byte getStates(UserConnection user, BlockPosition position) {
        byte states = 0;
        boolean pre1_12 = user.getProtocolInfo().serverProtocolVersion().olderThan(ProtocolVersion.v1_12);
        if (this.connects(BlockFace.EAST, this.getBlockData(user, position.getRelative(BlockFace.EAST)), pre1_12)) {
            states = (byte)(states | 1);
        }
        if (this.connects(BlockFace.NORTH, this.getBlockData(user, position.getRelative(BlockFace.NORTH)), pre1_12)) {
            states = (byte)(states | 2);
        }
        if (this.connects(BlockFace.SOUTH, this.getBlockData(user, position.getRelative(BlockFace.SOUTH)), pre1_12)) {
            states = (byte)(states | 4);
        }
        if (this.connects(BlockFace.WEST, this.getBlockData(user, position.getRelative(BlockFace.WEST)), pre1_12)) {
            states = (byte)(states | 8);
        }
        return states;
    }

    protected byte statesSize() {
        return 16;
    }

    @Override
    public int getBlockData(UserConnection user, BlockPosition position) {
        return STAIR_CONNECTION_HANDLER.connect(user, position, ConnectionHandler.super.getBlockData(user, position));
    }

    @Override
    public int connect(UserConnection user, BlockPosition position, int blockState) {
        int newBlockState = this.connectedBlockStates[this.getStates(user, position)];
        return newBlockState == -1 ? blockState : newBlockState;
    }

    protected boolean connects(BlockFace side, int blockState, boolean pre1_12) {
        if (this.blockStates.contains(blockState)) {
            return true;
        }
        if (this.blockConnectionsTypeId == -1) {
            return false;
        }
        BlockData blockData = (BlockData)ConnectionData.blockConnectionData.get(blockState);
        return blockData != null && blockData.connectsTo(this.blockConnectionsTypeId, side.opposite(), pre1_12);
    }

    public IntSet getBlockStates() {
        return this.blockStates;
    }
}

