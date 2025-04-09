/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.util;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VersionRange {
    private final ProtocolVersion min;
    private final ProtocolVersion max;
    private final List<VersionRange> ranges;

    private VersionRange(ProtocolVersion min, ProtocolVersion max) {
        this.min = min;
        this.max = max;
        this.ranges = new ArrayList<VersionRange>();
    }

    public static VersionRange andNewer(ProtocolVersion version) {
        return new VersionRange(version, null);
    }

    public static VersionRange single(ProtocolVersion version) {
        return new VersionRange(version, version);
    }

    public static VersionRange andOlder(ProtocolVersion version) {
        return new VersionRange(null, version);
    }

    public static VersionRange of(ProtocolVersion min, ProtocolVersion max) {
        return new VersionRange(min, max);
    }

    public static VersionRange all() {
        return new VersionRange(null, null);
    }

    public VersionRange add(VersionRange range) {
        this.ranges.add(range);
        return this;
    }

    public boolean contains(ProtocolVersion version) {
        for (VersionRange range : this.ranges) {
            if (!range.contains(version)) continue;
            return true;
        }
        if (this.min == null && this.max == null) {
            return true;
        }
        if (this.min == null) {
            return version.olderThanOrEqualTo(this.max);
        }
        if (this.max == null) {
            return version.newerThanOrEqualTo(this.min);
        }
        return version.newerThanOrEqualTo(this.min) && version.olderThanOrEqualTo(this.max);
    }

    public ProtocolVersion getMin() {
        return this.min;
    }

    public ProtocolVersion getMax() {
        return this.max;
    }

    public String toString() {
        if (this.min == null && this.max == null) {
            return "*";
        }
        StringBuilder rangeString = new StringBuilder();
        if (!this.ranges.isEmpty()) {
            for (VersionRange range : this.ranges) {
                rangeString.append(", ").append(range.toString());
            }
        }
        if (this.min == null) {
            StringBuilder stringBuilder = rangeString;
            String string = this.max.getName();
            return "<= " + string + stringBuilder;
        }
        if (this.max == null) {
            StringBuilder stringBuilder = rangeString;
            String string = this.min.getName();
            return ">= " + string + stringBuilder;
        }
        if (Objects.equals(this.min, this.max)) {
            return this.min.getName();
        }
        StringBuilder stringBuilder = rangeString;
        String string = this.max.getName();
        String string2 = this.min.getName();
        return string2 + " - " + string + stringBuilder;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        VersionRange that = (VersionRange)object;
        return this.min == that.min && this.max == that.max && Objects.equals(this.ranges, that.ranges);
    }

    public int hashCode() {
        return Objects.hash(this.min, this.max, this.ranges);
    }

    public static VersionRange fromString(String str) {
        if ("*".equals(str)) {
            return VersionRange.all();
        }
        if (str.contains(",")) {
            String[] rangeParts = str.split(", ");
            VersionRange versionRange = null;
            for (String part : rangeParts) {
                if (versionRange == null) {
                    versionRange = VersionRange.parseSinglePart(part);
                    continue;
                }
                versionRange.add(VersionRange.parseSinglePart(part));
            }
            return versionRange;
        }
        return VersionRange.parseSinglePart(str);
    }

    private static VersionRange parseSinglePart(String part) {
        if (part.startsWith("<= ")) {
            return VersionRange.andOlder(ProtocolVersion.getClosest(part.substring(3)));
        }
        if (part.startsWith(">= ")) {
            return VersionRange.andNewer(ProtocolVersion.getClosest(part.substring(3)));
        }
        if (part.contains(" - ")) {
            String[] rangeParts = part.split(" - ");
            ProtocolVersion min = ProtocolVersion.getClosest(rangeParts[0]);
            ProtocolVersion max = ProtocolVersion.getClosest(rangeParts[1]);
            return VersionRange.of(min, max);
        }
        return VersionRange.single(ProtocolVersion.getClosest(part));
    }
}

