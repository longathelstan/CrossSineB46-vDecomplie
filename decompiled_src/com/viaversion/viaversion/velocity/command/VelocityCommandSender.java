/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.Objects;
import java.util.UUID;
import net.kyori.adventure.text.Component;

public final class VelocityCommandSender
implements ViaCommandSender {
    private final CommandSource source;

    public VelocityCommandSender(CommandSource source) {
        this.source = source;
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.source.hasPermission(permission);
    }

    @Override
    public void sendMessage(String msg) {
        this.source.sendMessage((Component)VelocityPlugin.COMPONENT_SERIALIZER.deserialize(msg));
    }

    @Override
    public UUID getUUID() {
        CommandSource commandSource = this.source;
        if (commandSource instanceof Player) {
            Player player = (Player)commandSource;
            return player.getUniqueId();
        }
        return new UUID(0L, 0L);
    }

    @Override
    public String getName() {
        CommandSource commandSource = this.source;
        if (commandSource instanceof Player) {
            Player player = (Player)commandSource;
            return player.getUsername();
        }
        return "?";
    }

    public CommandSource source() {
        return this.source;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VelocityCommandSender)) {
            return false;
        }
        VelocityCommandSender velocityCommandSender = (VelocityCommandSender)object;
        return Objects.equals(this.source, velocityCommandSender.source);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.source);
    }

    public String toString() {
        return String.format("%s[source=%s]", this.getClass().getSimpleName(), Objects.toString(this.source));
    }
}

