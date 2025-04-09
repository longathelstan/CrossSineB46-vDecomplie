/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.viaversion.viaversion.api.platform.providers.Provider;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Pattern;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.util.UuidUtil;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;

public abstract class GameProfileFetcher
implements Provider {
    protected static final Pattern PATTERN_CONTROL_CODE = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    static final ThreadPoolExecutor LOADING_POOL = (ThreadPoolExecutor)Executors.newFixedThreadPool(2, new ThreadFactoryBuilder().setNameFormat("ViaLegacy GameProfile Loader #%d").setDaemon(true).build());
    final LoadingCache<String, UUID> UUID_CACHE = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build((CacheLoader)new CacheLoader<String, UUID>(){

        public UUID load(String key) throws Exception {
            return GameProfileFetcher.this.loadMojangUUID(key);
        }
    });
    final LoadingCache<UUID, GameProfile> GAMEPROFILE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build((CacheLoader)new CacheLoader<UUID, GameProfile>(){

        public GameProfile load(UUID key) throws Exception {
            return GameProfileFetcher.this.loadGameProfile(key);
        }
    });

    public boolean isUUIDLoaded(String playerName) {
        return this.UUID_CACHE.getIfPresent((Object)playerName) != null;
    }

    public UUID getMojangUUID(String playerName) {
        playerName = PATTERN_CONTROL_CODE.matcher(playerName).replaceAll("");
        try {
            return (UUID)this.UUID_CACHE.get((Object)playerName);
        }
        catch (Throwable e) {
            while (e instanceof ExecutionException || e instanceof UncheckedExecutionException || e instanceof CompletionException) {
                e = e.getCause();
            }
            String string = e.getClass().getName();
            String string2 = playerName;
            ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Failed to load uuid for player '" + string2 + "' (" + string + ")");
            UUID uuid = UuidUtil.createOfflinePlayerUuid(playerName);
            this.UUID_CACHE.put((Object)playerName, (Object)uuid);
            return uuid;
        }
    }

    public CompletableFuture<UUID> getMojangUUIDAsync(String playerName) {
        CompletableFuture<UUID> future = new CompletableFuture<UUID>();
        if (this.isUUIDLoaded(playerName)) {
            future.complete(this.getMojangUUID(playerName));
        } else {
            LOADING_POOL.submit(() -> future.complete(this.getMojangUUID(playerName)));
        }
        return future;
    }

    public boolean isGameProfileLoaded(UUID uuid) {
        return this.GAMEPROFILE_CACHE.getIfPresent((Object)uuid) != null;
    }

    public GameProfile getGameProfile(UUID uuid) {
        try {
            GameProfile value = (GameProfile)this.GAMEPROFILE_CACHE.get((Object)uuid);
            if (GameProfile.NULL.equals(value)) {
                return null;
            }
            return value;
        }
        catch (Throwable e) {
            while (e instanceof ExecutionException || e instanceof UncheckedExecutionException || e instanceof CompletionException) {
                e = e.getCause();
            }
            String string = e.getClass().getName();
            UUID uUID = uuid;
            ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Failed to load game profile for uuid '" + uUID + "' (" + string + ")");
            this.GAMEPROFILE_CACHE.put((Object)uuid, (Object)GameProfile.NULL);
            return null;
        }
    }

    public CompletableFuture<GameProfile> getGameProfileAsync(UUID uuid) {
        CompletableFuture<GameProfile> future = new CompletableFuture<GameProfile>();
        if (this.isGameProfileLoaded(uuid)) {
            future.complete(this.getGameProfile(uuid));
        } else {
            LOADING_POOL.submit(() -> future.complete(this.getGameProfile(uuid)));
        }
        return future;
    }

    public abstract UUID loadMojangUUID(String var1) throws Exception;

    public abstract GameProfile loadGameProfile(UUID var1) throws Exception;
}

