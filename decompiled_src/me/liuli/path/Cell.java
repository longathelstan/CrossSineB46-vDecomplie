/*
 * Decompiled with CFR 0.152.
 */
package me.liuli.path;

public class Cell {
    public final int x;
    public final int y;
    public final int z;
    public int g = 0;
    public int h = 0;
    public int f = 0;
    public Cell parent;

    public Cell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object o) {
        if (o instanceof Cell) {
            Cell c = (Cell)o;
            return c.x == this.x && c.y == this.y && c.z == this.z;
        }
        return false;
    }

    public int hashCode() {
        return this.x * 31 + this.y * 31 + this.z * 31;
    }

    public String toString() {
        return "Cell(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}

