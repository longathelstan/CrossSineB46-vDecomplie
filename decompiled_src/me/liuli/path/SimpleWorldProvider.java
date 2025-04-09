/*
 * Decompiled with CFR 0.152.
 */
package me.liuli.path;

import java.util.ArrayList;
import me.liuli.path.Cell;
import me.liuli.path.IWorldProvider;

public class SimpleWorldProvider
implements IWorldProvider {
    private final ArrayList<Cell> walls = new ArrayList();

    public void addWall(Cell cell) {
        this.walls.add(cell);
    }

    @Override
    public boolean isBlocked(Cell cell) {
        return this.walls.contains(cell);
    }
}

