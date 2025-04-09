/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.IdAndData;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.Protocolr1_1Tor1_2_1_3;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.model.PendingBlockEntry;
import net.raphimc.vialegacy.protocol.release.r1_2_1_3tor1_2_4_5.packet.ClientboundPackets1_2_1;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class PendingBlocksTracker
extends StoredObject {
    private final List<PendingBlockEntry> pendingBlockEntries = new LinkedList<PendingBlockEntry>();

    public PendingBlocksTracker(UserConnection user) {
        super(user);
    }

    public void clear() {
        this.pendingBlockEntries.clear();
    }

    public void addPending(BlockPosition position, IdAndData block) {
        this.pendingBlockEntries.add(new PendingBlockEntry(position, block));
    }

    public void markReceived(BlockPosition position) {
        this.markReceived(position, position);
    }

    public void markReceived(BlockPosition startPos, BlockPosition endPos) {
        Iterator<PendingBlockEntry> it = this.pendingBlockEntries.iterator();
        while (it.hasNext()) {
            BlockPosition pendingBlockPos = it.next().getPosition();
            if (pendingBlockPos.x() < startPos.x() || pendingBlockPos.y() < startPos.y() || pendingBlockPos.z() < startPos.z() || pendingBlockPos.x() > endPos.x() || pendingBlockPos.y() > endPos.y() || pendingBlockPos.z() > endPos.z()) continue;
            it.remove();
        }
    }

    public void tick() {
        Iterator<PendingBlockEntry> it = this.pendingBlockEntries.iterator();
        while (it.hasNext()) {
            PendingBlockEntry pendingBlockEntry = it.next();
            if (!pendingBlockEntry.decrementAndCheckIsExpired()) continue;
            it.remove();
            PacketWrapper blockChange = PacketWrapper.create(ClientboundPackets1_2_1.BLOCK_UPDATE, this.user());
            blockChange.write(Types1_7_6.BLOCK_POSITION_UBYTE, pendingBlockEntry.getPosition());
            blockChange.write(Types.UNSIGNED_BYTE, (short)pendingBlockEntry.getBlock().getId());
            blockChange.write(Types.UNSIGNED_BYTE, Short.valueOf(pendingBlockEntry.getBlock().getData()));
            blockChange.send(Protocolr1_1Tor1_2_1_3.class);
        }
    }
}

