/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.version;

import com.google.common.collect.Range;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ProtocolVersionRange {
    private List<Range<ProtocolVersion>> ranges;

    private ProtocolVersionRange(List<Range<ProtocolVersion>> ranges) {
        if (ranges != null) {
            this.ranges = new ArrayList<Range<ProtocolVersion>>(ranges);
        }
    }

    public static ProtocolVersionRange all() {
        return new ProtocolVersionRange(null);
    }

    public static ProtocolVersionRange of(ProtocolVersion min, ProtocolVersion max) {
        return new ProtocolVersionRange(Collections.singletonList(Range.open((Comparable)min, (Comparable)max)));
    }

    public static ProtocolVersionRange of(Range<ProtocolVersion> range) {
        return new ProtocolVersionRange(Collections.singletonList(range));
    }

    public static ProtocolVersionRange of(List<Range<ProtocolVersion>> ranges) {
        return new ProtocolVersionRange(ranges);
    }

    public ProtocolVersionRange add(Range<ProtocolVersion> range) {
        if (this.ranges == null) {
            throw new UnsupportedOperationException("Range already contains all versions. Cannot add a new range.");
        }
        this.ranges.add(range);
        return this;
    }

    public boolean contains(ProtocolVersion version) {
        if (this.ranges == null) {
            return true;
        }
        for (Range<ProtocolVersion> range : this.ranges) {
            if (!range.contains((Comparable)version)) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.ranges != null) {
            StringBuilder rangeString = new StringBuilder();
            int i = 0;
            for (Range<ProtocolVersion> range : this.ranges) {
                ProtocolVersion max;
                ++i;
                ProtocolVersion min = range.hasLowerBound() ? (ProtocolVersion)range.lowerEndpoint() : null;
                ProtocolVersion protocolVersion = max = range.hasUpperBound() ? (ProtocolVersion)range.upperEndpoint() : null;
                if (min == null) {
                    rangeString.append("<= ").append(max.getName());
                } else if (max == null) {
                    rangeString.append(">= ").append(min.getName());
                } else if (Objects.equals(min, max)) {
                    rangeString.append(min.getName());
                } else {
                    rangeString.append(min.getName()).append(" - ").append(max.getName());
                }
                if (i == this.ranges.size()) continue;
                rangeString.append(", ");
            }
            return rangeString.toString();
        }
        return "*";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        ProtocolVersionRange that = (ProtocolVersionRange)object;
        return Objects.equals(this.ranges, that.ranges);
    }

    public int hashCode() {
        return Objects.hash(this.ranges);
    }

    public static ProtocolVersionRange fromString(String str) {
        if ("*".equals(str)) {
            return ProtocolVersionRange.all();
        }
        if (str.contains(",")) {
            String[] rangeParts = str.split(", ");
            ProtocolVersionRange versionRange = null;
            for (String part : rangeParts) {
                if (versionRange == null) {
                    versionRange = ProtocolVersionRange.of(ProtocolVersionRange.parseSinglePart(part));
                    continue;
                }
                versionRange.add(ProtocolVersionRange.parseSinglePart(part));
            }
            return versionRange;
        }
        return ProtocolVersionRange.of(ProtocolVersionRange.parseSinglePart(str));
    }

    private static Range<ProtocolVersion> parseSinglePart(String part) {
        if (part.startsWith("<= ")) {
            return Range.atMost((Comparable)ProtocolVersion.getClosest(part.substring(3)));
        }
        if (part.startsWith(">= ")) {
            return Range.atLeast((Comparable)ProtocolVersion.getClosest(part.substring(3)));
        }
        if (part.contains(" - ")) {
            String[] rangeParts = part.split(" - ");
            ProtocolVersion min = ProtocolVersion.getClosest(rangeParts[0]);
            ProtocolVersion max = ProtocolVersion.getClosest(rangeParts[1]);
            return Range.open((Comparable)min, (Comparable)max);
        }
        return Range.singleton((Comparable)ProtocolVersion.getClosest(part));
    }
}

