/*
 * Decompiled with CFR 0.152.
 */
package me.liuli.path;

import java.util.ArrayList;
import java.util.Collections;
import me.liuli.path.Cell;
import me.liuli.path.IWorldProvider;

public class Pathfinder {
    public static final Cell[] COMMON_NEIGHBORS = new Cell[]{new Cell(1, 0, 0), new Cell(-1, 0, 0), new Cell(0, 1, 0), new Cell(0, -1, 0), new Cell(0, 0, 1), new Cell(0, 0, -1)};
    public static final Cell[] DIAGONAL_NEIGHBORS = new Cell[]{new Cell(1, 1, 0), new Cell(-1, -1, 0), new Cell(1, -1, 0), new Cell(-1, 1, 0), new Cell(0, 1, 1), new Cell(0, -1, -1), new Cell(0, 1, -1), new Cell(0, -1, 1), new Cell(1, 0, 0), new Cell(-1, 0, 0), new Cell(0, 1, 0), new Cell(0, -1, 0), new Cell(0, 0, 1), new Cell(0, 0, -1)};
    private final Cell start;
    private final Cell end;
    private final Cell[] neighbours;
    private final IWorldProvider world;

    public Pathfinder(Cell start, Cell end, Cell[] neighbours, IWorldProvider world) {
        this.start = start;
        this.end = end;
        this.neighbours = neighbours;
        this.world = world;
    }

    public Cell getStart() {
        return this.start;
    }

    public Cell getEnd() {
        return this.end;
    }

    public Cell[] getNeighbours() {
        return this.neighbours;
    }

    public IWorldProvider getWorld() {
        return this.world;
    }

    public ArrayList<Cell> findPath() {
        return this.findPath(Integer.MAX_VALUE);
    }

    public ArrayList<Cell> findPath(int maxLoops) {
        ArrayList<Cell> open = new ArrayList<Cell>();
        ArrayList<Cell> closed = new ArrayList<Cell>();
        open.add(this.start);
        Cell current = null;
        for (int loops = 0; !open.isEmpty() && loops < maxLoops; ++loops) {
            current = (Cell)open.get(0);
            int currentIdx = 0;
            for (int i = 1; i < open.size(); ++i) {
                if (((Cell)open.get((int)i)).f >= current.f) continue;
                current = (Cell)open.get(i);
                currentIdx = i;
            }
            open.remove(currentIdx);
            closed.add(current);
            if (current.equals(this.end)) break;
            ArrayList<Cell> children = new ArrayList<Cell>();
            for (Cell neighbor : this.neighbours) {
                Cell child = new Cell(current.x + neighbor.x, current.y + neighbor.y, current.z + neighbor.z);
                child.parent = current;
                if (this.world.isBlocked(child)) continue;
                children.add(child);
            }
            for (Cell child : children) {
                if (closed.contains(child)) continue;
                child.g = current.g + 1;
                child.h = (int)(Math.pow(child.x - this.end.x, 2.0) + Math.pow(child.y - this.end.y, 2.0) + Math.pow(child.z - this.end.z, 2.0));
                child.f = child.g + child.h;
                if (open.contains(child) && ((Cell)open.get((int)open.indexOf((Object)child))).g > child.g) continue;
                open.add(child);
            }
        }
        ArrayList<Cell> path = new ArrayList<Cell>();
        Cell cur = current;
        while (cur != null) {
            path.add(cur);
            cur = cur.parent;
        }
        Collections.reverse(path);
        return path;
    }
}

