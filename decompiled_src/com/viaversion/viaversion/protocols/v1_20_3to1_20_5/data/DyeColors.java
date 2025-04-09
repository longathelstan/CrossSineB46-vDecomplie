/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data;

public final class DyeColors {
    public static String colorById(int id) {
        String string;
        switch (id) {
            case 1: {
                string = "orange";
                break;
            }
            case 2: {
                string = "magenta";
                break;
            }
            case 3: {
                string = "light_blue";
                break;
            }
            case 4: {
                string = "yellow";
                break;
            }
            case 5: {
                string = "lime";
                break;
            }
            case 6: {
                string = "pink";
                break;
            }
            case 7: {
                string = "gray";
                break;
            }
            case 8: {
                string = "light_gray";
                break;
            }
            case 9: {
                string = "cyan";
                break;
            }
            case 10: {
                string = "purple";
                break;
            }
            case 11: {
                string = "blue";
                break;
            }
            case 12: {
                string = "brown";
                break;
            }
            case 13: {
                string = "green";
                break;
            }
            case 14: {
                string = "red";
                break;
            }
            case 15: {
                string = "black";
                break;
            }
            default: {
                string = "white";
            }
        }
        return string;
    }
}

