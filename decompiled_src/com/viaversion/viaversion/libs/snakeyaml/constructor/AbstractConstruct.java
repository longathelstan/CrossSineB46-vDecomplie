/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.constructor;

import com.viaversion.viaversion.libs.snakeyaml.constructor.Construct;
import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;

public abstract class AbstractConstruct
implements Construct {
    @Override
    public void construct2ndStep(Node node, Object data) {
        if (node.isTwoStepsConstruction()) {
            throw new IllegalStateException("Not Implemented in " + this.getClass().getName());
        }
        throw new YAMLException("Unexpected recursive structure for Node: " + node);
    }
}

