/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.extensions.compactnotation;

import com.viaversion.viaversion.libs.snakeyaml.LoaderOptions;
import com.viaversion.viaversion.libs.snakeyaml.constructor.Construct;
import com.viaversion.viaversion.libs.snakeyaml.constructor.Constructor;
import com.viaversion.viaversion.libs.snakeyaml.error.YAMLException;
import com.viaversion.viaversion.libs.snakeyaml.extensions.compactnotation.CompactData;
import com.viaversion.viaversion.libs.snakeyaml.introspector.Property;
import com.viaversion.viaversion.libs.snakeyaml.nodes.MappingNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.Node;
import com.viaversion.viaversion.libs.snakeyaml.nodes.NodeTuple;
import com.viaversion.viaversion.libs.snakeyaml.nodes.ScalarNode;
import com.viaversion.viaversion.libs.snakeyaml.nodes.SequenceNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompactConstructor
extends Constructor {
    private static final Pattern GUESS_COMPACT = Pattern.compile("\\p{Alpha}.*\\s*\\((?:,?\\s*(?:(?:\\w*)|(?:\\p{Alpha}\\w*\\s*=.+))\\s*)+\\)");
    private static final Pattern FIRST_PATTERN = Pattern.compile("(\\p{Alpha}.*)(\\s*)\\((.*?)\\)");
    private static final Pattern PROPERTY_NAME_PATTERN = Pattern.compile("\\s*(\\p{Alpha}\\w*)\\s*=(.+)");
    private Construct compactConstruct;

    public CompactConstructor(LoaderOptions loadingConfig) {
        super(loadingConfig);
    }

    public CompactConstructor() {
        super(new LoaderOptions());
    }

    protected Object constructCompactFormat(ScalarNode node, CompactData data) {
        try {
            Object obj = this.createInstance(node, data);
            HashMap<String, Object> properties = new HashMap<String, Object>(data.getProperties());
            this.setProperties(obj, properties);
            return obj;
        }
        catch (Exception e) {
            throw new YAMLException(e);
        }
    }

    protected Object createInstance(ScalarNode node, CompactData data) throws Exception {
        Class<?> clazz = this.getClassForName(data.getPrefix());
        Class[] args2 = new Class[data.getArguments().size()];
        for (int i = 0; i < args2.length; ++i) {
            args2[i] = String.class;
        }
        java.lang.reflect.Constructor<?> c = clazz.getDeclaredConstructor(args2);
        c.setAccessible(true);
        return c.newInstance(data.getArguments().toArray());
    }

    protected void setProperties(Object bean, Map<String, Object> data) throws Exception {
        if (data == null) {
            throw new NullPointerException("Data for Compact Object Notation cannot be null.");
        }
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Property property = this.getPropertyUtils().getProperty(bean.getClass(), key);
            try {
                property.set(bean, entry.getValue());
            }
            catch (IllegalArgumentException e) {
                throw new YAMLException("Cannot set property='" + key + "' with value='" + data.get(key) + "' (" + data.get(key).getClass() + ") in " + bean);
            }
        }
    }

    public CompactData getCompactData(String scalar) {
        if (!scalar.endsWith(")")) {
            return null;
        }
        if (scalar.indexOf(40) < 0) {
            return null;
        }
        Matcher m2 = FIRST_PATTERN.matcher(scalar);
        if (m2.matches()) {
            String tag = m2.group(1).trim();
            String content = m2.group(3);
            CompactData data = new CompactData(tag);
            if (content.length() == 0) {
                return data;
            }
            String[] names = content.split("\\s*,\\s*");
            for (int i = 0; i < names.length; ++i) {
                String section = names[i];
                if (section.indexOf(61) < 0) {
                    data.getArguments().add(section);
                    continue;
                }
                Matcher sm = PROPERTY_NAME_PATTERN.matcher(section);
                if (sm.matches()) {
                    String name = sm.group(1);
                    String value = sm.group(2).trim();
                    data.getProperties().put(name, value);
                    continue;
                }
                return null;
            }
            return data;
        }
        return null;
    }

    private Construct getCompactConstruct() {
        if (this.compactConstruct == null) {
            this.compactConstruct = this.createCompactConstruct();
        }
        return this.compactConstruct;
    }

    protected Construct createCompactConstruct() {
        return new ConstructCompactObject();
    }

    @Override
    protected Construct getConstructor(Node node) {
        ScalarNode scalar;
        ScalarNode scalar2;
        NodeTuple tuple;
        Node key;
        MappingNode mnode;
        List<NodeTuple> list;
        if (node instanceof MappingNode ? (list = (mnode = (MappingNode)node).getValue()).size() == 1 && (key = (tuple = list.get(0)).getKeyNode()) instanceof ScalarNode && GUESS_COMPACT.matcher((scalar2 = (ScalarNode)key).getValue()).matches() : node instanceof ScalarNode && GUESS_COMPACT.matcher((scalar = (ScalarNode)node).getValue()).matches()) {
            return this.getCompactConstruct();
        }
        return super.getConstructor(node);
    }

    protected void applySequence(Object bean, List<?> value) {
        try {
            Property property = this.getPropertyUtils().getProperty(bean.getClass(), this.getSequencePropertyName(bean.getClass()));
            property.set(bean, value);
        }
        catch (Exception e) {
            throw new YAMLException(e);
        }
    }

    protected String getSequencePropertyName(Class<?> bean) {
        Set<Property> properties = this.getPropertyUtils().getProperties(bean);
        Iterator<Property> iterator2 = properties.iterator();
        while (iterator2.hasNext()) {
            Property property = iterator2.next();
            if (List.class.isAssignableFrom(property.getType())) continue;
            iterator2.remove();
        }
        if (properties.size() == 0) {
            throw new YAMLException("No list property found in " + bean);
        }
        if (properties.size() > 1) {
            throw new YAMLException("Many list properties found in " + bean + "; Please override getSequencePropertyName() to specify which property to use.");
        }
        return properties.iterator().next().getName();
    }

    public class ConstructCompactObject
    extends Constructor.ConstructMapping {
        public ConstructCompactObject() {
            super(CompactConstructor.this);
        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            MappingNode mnode = (MappingNode)node;
            NodeTuple nodeTuple = mnode.getValue().iterator().next();
            Node valueNode = nodeTuple.getValueNode();
            if (valueNode instanceof MappingNode) {
                valueNode.setType(object.getClass());
                this.constructJavaBean2ndStep((MappingNode)valueNode, object);
            } else {
                CompactConstructor.this.applySequence(object, CompactConstructor.this.constructSequence((SequenceNode)valueNode));
            }
        }

        @Override
        public Object construct(Node node) {
            ScalarNode tmpNode;
            if (node instanceof MappingNode) {
                MappingNode mnode = (MappingNode)node;
                NodeTuple nodeTuple = mnode.getValue().iterator().next();
                node.setTwoStepsConstruction(true);
                tmpNode = (ScalarNode)nodeTuple.getKeyNode();
            } else {
                tmpNode = (ScalarNode)node;
            }
            CompactData data = CompactConstructor.this.getCompactData(tmpNode.getValue());
            if (data == null) {
                return CompactConstructor.this.constructScalar(tmpNode);
            }
            return CompactConstructor.this.constructCompactFormat(tmpNode, data);
        }
    }
}

