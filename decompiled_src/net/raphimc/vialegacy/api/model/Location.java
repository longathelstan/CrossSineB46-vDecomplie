/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.model;

import com.viaversion.viaversion.api.minecraft.BlockPosition;
import java.util.Objects;

public class Location {
    private double x;
    private double y;
    private double z;

    public Location(BlockPosition position) {
        this(position.x(), position.y(), position.z());
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return this.z;
    }

    public double distanceTo(Location p2) {
        return Math.sqrt(Math.pow(p2.getX() - this.x, 2.0) + Math.pow(p2.getY() - this.y, 2.0) + Math.pow(p2.getZ() - this.z, 2.0));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location)o;
        return Double.compare(location.x, this.x) == 0 && Double.compare(location.y, this.y) == 0 && Double.compare(location.z, this.z) == 0;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    public String toString() {
        double d = this.z;
        double d2 = this.y;
        double d3 = this.x;
        return "Location{x=" + d3 + ", y=" + d2 + ", z=" + d + "}";
    }
}

