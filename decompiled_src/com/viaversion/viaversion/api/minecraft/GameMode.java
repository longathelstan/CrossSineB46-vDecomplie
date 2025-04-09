/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

public enum GameMode {
    NOT_SET(""),
    SURVIVAL("Survival Mode"),
    CREATIVE("Creative Mode"),
    ADVENTURE("Adventure Mode"),
    SPECTATOR("Spectator Mode");

    private final String text;

    private GameMode(String text) {
        this.text = text;
    }

    public String text() {
        return this.text;
    }

    public static GameMode getById(int id) {
        GameMode gameMode;
        switch (id) {
            case -1: {
                gameMode = NOT_SET;
                break;
            }
            case 1: {
                gameMode = CREATIVE;
                break;
            }
            case 2: {
                gameMode = ADVENTURE;
                break;
            }
            case 3: {
                gameMode = SPECTATOR;
                break;
            }
            default: {
                gameMode = SURVIVAL;
            }
        }
        return gameMode;
    }
}

