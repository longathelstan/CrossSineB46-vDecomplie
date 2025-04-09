/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public final class BukkitCommandSender
implements ViaCommandSender {
    private final CommandSender sender;

    public BukkitCommandSender(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.sender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String msg) {
        this.sender.sendMessage(msg);
    }

    @Override
    public UUID getUUID() {
        CommandSender commandSender = this.sender;
        if (commandSender instanceof Entity) {
            Entity entity = (Entity)commandSender;
            return entity.getUniqueId();
        }
        return new UUID(0L, 0L);
    }

    @Override
    public String getName() {
        return this.sender.getName();
    }

    public CommandSender sender() {
        return this.sender;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BukkitCommandSender)) {
            return false;
        }
        BukkitCommandSender bukkitCommandSender = (BukkitCommandSender)object;
        return Objects.equals(this.sender, bukkitCommandSender.sender);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.sender);
    }

    public String toString() {
        return String.format("%s[sender=%s]", this.getClass().getSimpleName(), Objects.toString(this.sender));
    }
}

