/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.TypeReference;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.util.Printer;
import org.spongepowered.asm.lib.util.Textifiable;
import org.spongepowered.asm.lib.util.TraceClassVisitor;
import org.spongepowered.asm.lib.util.TraceSignatureVisitor;

public class Textifier
extends Printer {
    public static final int INTERNAL_NAME = 0;
    public static final int FIELD_DESCRIPTOR = 1;
    public static final int FIELD_SIGNATURE = 2;
    public static final int METHOD_DESCRIPTOR = 3;
    public static final int METHOD_SIGNATURE = 4;
    public static final int CLASS_SIGNATURE = 5;
    public static final int TYPE_DECLARATION = 6;
    public static final int CLASS_DECLARATION = 7;
    public static final int PARAMETERS_DECLARATION = 8;
    public static final int HANDLE_DESCRIPTOR = 9;
    protected String tab = "  ";
    protected String tab2 = "    ";
    protected String tab3 = "      ";
    protected String ltab = "   ";
    protected Map<Label, String> labelNames;
    private int access;
    private int valueNumber = 0;

    public Textifier() {
        this(327680);
        if (this.getClass() != Textifier.class) {
            throw new IllegalStateException();
        }
    }

    protected Textifier(int api) {
        super(api);
    }

    public static void main(String[] args2) throws Exception {
        int i = 0;
        int flags = 2;
        boolean ok = true;
        if (args2.length < 1 || args2.length > 2) {
            ok = false;
        }
        if (ok && "-debug".equals(args2[0])) {
            i = 1;
            flags = 0;
            if (args2.length != 2) {
                ok = false;
            }
        }
        if (!ok) {
            System.err.println("Prints a disassembled view of the given class.");
            System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        ClassReader cr = args2[i].endsWith(".class") || args2[i].indexOf(92) > -1 || args2[i].indexOf(47) > -1 ? new ClassReader(new FileInputStream(args2[i])) : new ClassReader(args2[i]);
        cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), flags);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.access = access;
        int major = version & 0xFFFF;
        int minor = version >>> 16;
        this.buf.setLength(0);
        this.buf.append("// class version ").append(major).append('.').append(minor).append(" (").append(version).append(")\n");
        if ((access & 0x20000) != 0) {
            this.buf.append("// DEPRECATED\n");
        }
        this.buf.append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        this.appendDescriptor(5, signature);
        if (signature != null) {
            TraceSignatureVisitor sv = new TraceSignatureVisitor(access);
            SignatureReader r = new SignatureReader(signature);
            r.accept(sv);
            this.buf.append("// declaration: ").append(name).append(sv.getDeclaration()).append('\n');
        }
        this.appendAccess(access & 0xFFFFFFDF);
        if ((access & 0x2000) != 0) {
            this.buf.append("@interface ");
        } else if ((access & 0x200) != 0) {
            this.buf.append("interface ");
        } else if ((access & 0x4000) == 0) {
            this.buf.append("class ");
        }
        this.appendDescriptor(0, name);
        if (superName != null && !"java/lang/Object".equals(superName)) {
            this.buf.append(" extends ");
            this.appendDescriptor(0, superName);
            this.buf.append(' ');
        }
        if (interfaces != null && interfaces.length > 0) {
            this.buf.append(" implements ");
            for (int i = 0; i < interfaces.length; ++i) {
                this.appendDescriptor(0, interfaces[i]);
                this.buf.append(' ');
            }
        }
        this.buf.append(" {\n\n");
        this.text.add(this.buf.toString());
    }

    public void visitSource(String file, String debug) {
        this.buf.setLength(0);
        if (file != null) {
            this.buf.append(this.tab).append("// compiled from: ").append(file).append('\n');
        }
        if (debug != null) {
            this.buf.append(this.tab).append("// debug info: ").append(debug).append('\n');
        }
        if (this.buf.length() > 0) {
            this.text.add(this.buf.toString());
        }
    }

    public void visitOuterClass(String owner, String name, String desc) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("OUTERCLASS ");
        this.appendDescriptor(0, owner);
        this.buf.append(' ');
        if (name != null) {
            this.buf.append(name).append(' ');
        }
        this.appendDescriptor(3, desc);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public Textifier visitClassAnnotation(String desc, boolean visible) {
        this.text.add("\n");
        return this.visitAnnotation(desc, visible);
    }

    public Printer visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.text.add("\n");
        return this.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    public void visitClassAttribute(Attribute attr) {
        this.text.add("\n");
        this.visitAttribute(attr);
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("// access flags 0x");
        this.buf.append(Integer.toHexString(access & 0xFFFFFFDF).toUpperCase()).append('\n');
        this.buf.append(this.tab);
        this.appendAccess(access);
        this.buf.append("INNERCLASS ");
        this.appendDescriptor(0, name);
        this.buf.append(' ');
        this.appendDescriptor(0, outerName);
        this.buf.append(' ');
        this.appendDescriptor(0, innerName);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public Textifier visitField(int access, String name, String desc, String signature, Object value) {
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((access & 0x20000) != 0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        if (signature != null) {
            this.buf.append(this.tab);
            this.appendDescriptor(2, signature);
            TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.acceptType(sv);
            this.buf.append(this.tab).append("// declaration: ").append(sv.getDeclaration()).append('\n');
        }
        this.buf.append(this.tab);
        this.appendAccess(access);
        this.appendDescriptor(1, desc);
        this.buf.append(' ').append(name);
        if (value != null) {
            this.buf.append(" = ");
            if (value instanceof String) {
                this.buf.append('\"').append(value).append('\"');
            } else {
                this.buf.append(value);
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        return t;
    }

    public Textifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((access & 0x20000) != 0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(access).toUpperCase()).append('\n');
        if (signature != null) {
            this.buf.append(this.tab);
            this.appendDescriptor(4, signature);
            TraceSignatureVisitor v = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.accept(v);
            String genericDecl = v.getDeclaration();
            String genericReturn = v.getReturnType();
            String genericExceptions = v.getExceptions();
            this.buf.append(this.tab).append("// declaration: ").append(genericReturn).append(' ').append(name).append(genericDecl);
            if (genericExceptions != null) {
                this.buf.append(" throws ").append(genericExceptions);
            }
            this.buf.append('\n');
        }
        this.buf.append(this.tab);
        this.appendAccess(access & 0xFFFFFFBF);
        if ((access & 0x100) != 0) {
            this.buf.append("native ");
        }
        if ((access & 0x80) != 0) {
            this.buf.append("varargs ");
        }
        if ((access & 0x40) != 0) {
            this.buf.append("bridge ");
        }
        if ((this.access & 0x200) != 0 && (access & 0x400) == 0 && (access & 8) == 0) {
            this.buf.append("default ");
        }
        this.buf.append(name);
        this.appendDescriptor(3, desc);
        if (exceptions != null && exceptions.length > 0) {
            this.buf.append(" throws ");
            for (int i = 0; i < exceptions.length; ++i) {
                this.appendDescriptor(0, exceptions[i]);
                this.buf.append(' ');
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        return t;
    }

    public void visitClassEnd() {
        this.text.add("}\n");
    }

    public void visit(String name, Object value) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        if (value instanceof String) {
            this.visitString((String)value);
        } else if (value instanceof Type) {
            this.visitType((Type)value);
        } else if (value instanceof Byte) {
            this.visitByte((Byte)value);
        } else if (value instanceof Boolean) {
            this.visitBoolean((Boolean)value);
        } else if (value instanceof Short) {
            this.visitShort((Short)value);
        } else if (value instanceof Character) {
            this.visitChar(((Character)value).charValue());
        } else if (value instanceof Integer) {
            this.visitInt((Integer)value);
        } else if (value instanceof Float) {
            this.visitFloat(((Float)value).floatValue());
        } else if (value instanceof Long) {
            this.visitLong((Long)value);
        } else if (value instanceof Double) {
            this.visitDouble((Double)value);
        } else if (value.getClass().isArray()) {
            this.buf.append('{');
            if (value instanceof byte[]) {
                byte[] v = (byte[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitByte(v[i]);
                }
            } else if (value instanceof boolean[]) {
                boolean[] v = (boolean[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitBoolean(v[i]);
                }
            } else if (value instanceof short[]) {
                short[] v = (short[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitShort(v[i]);
                }
            } else if (value instanceof char[]) {
                char[] v = (char[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitChar(v[i]);
                }
            } else if (value instanceof int[]) {
                int[] v = (int[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitInt(v[i]);
                }
            } else if (value instanceof long[]) {
                long[] v = (long[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitLong(v[i]);
                }
            } else if (value instanceof float[]) {
                float[] v = (float[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitFloat(v[i]);
                }
            } else if (value instanceof double[]) {
                double[] v = (double[])value;
                for (int i = 0; i < v.length; ++i) {
                    this.appendComa(i);
                    this.visitDouble(v[i]);
                }
            }
            this.buf.append('}');
        }
        this.text.add(this.buf.toString());
    }

    private void visitInt(int value) {
        this.buf.append(value);
    }

    private void visitLong(long value) {
        this.buf.append(value).append('L');
    }

    private void visitFloat(float value) {
        this.buf.append(value).append('F');
    }

    private void visitDouble(double value) {
        this.buf.append(value).append('D');
    }

    private void visitChar(char value) {
        this.buf.append("(char)").append((int)value);
    }

    private void visitShort(short value) {
        this.buf.append("(short)").append(value);
    }

    private void visitByte(byte value) {
        this.buf.append("(byte)").append(value);
    }

    private void visitBoolean(boolean value) {
        this.buf.append(value);
    }

    private void visitString(String value) {
        Textifier.appendString(this.buf, value);
    }

    private void visitType(Type value) {
        this.buf.append(value.getClassName()).append(".class");
    }

    public void visitEnum(String name, String desc, String value) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        this.appendDescriptor(1, desc);
        this.buf.append('.').append(value);
        this.text.add(this.buf.toString());
    }

    public Textifier visitAnnotation(String name, String desc) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        this.buf.append('@');
        this.appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.text.add(")");
        return t;
    }

    public Textifier visitArray(String name) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (name != null) {
            this.buf.append(name).append('=');
        }
        this.buf.append('{');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.text.add("}");
        return t;
    }

    public void visitAnnotationEnd() {
    }

    public Textifier visitFieldAnnotation(String desc, boolean visible) {
        return this.visitAnnotation(desc, visible);
    }

    public Printer visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return this.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    public void visitFieldAttribute(Attribute attr) {
        this.visitAttribute(attr);
    }

    public void visitFieldEnd() {
    }

    public void visitParameter(String name, int access) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("// parameter ");
        this.appendAccess(access);
        this.buf.append(' ').append(name == null ? "<no name>" : name).append('\n');
        this.text.add(this.buf.toString());
    }

    public Textifier visitAnnotationDefault() {
        this.text.add(this.tab2 + "default=");
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.text.add("\n");
        return t;
    }

    public Textifier visitMethodAnnotation(String desc, boolean visible) {
        return this.visitAnnotation(desc, visible);
    }

    public Printer visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return this.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    public Textifier visitParameterAnnotation(int parameter, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append('@');
        this.appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.text.add(visible ? ") // parameter " : ") // invisible, parameter ");
        this.text.add(parameter);
        this.text.add("\n");
        return t;
    }

    public void visitMethodAttribute(Attribute attr) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        this.appendDescriptor(-1, attr.type);
        if (attr instanceof Textifiable) {
            ((Textifiable)((Object)attr)).textify(this.buf, this.labelNames);
        } else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }

    public void visitCode() {
    }

    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.buf.append("FRAME ");
        switch (type) {
            case -1: 
            case 0: {
                this.buf.append("FULL [");
                this.appendFrameTypes(nLocal, local);
                this.buf.append("] [");
                this.appendFrameTypes(nStack, stack);
                this.buf.append(']');
                break;
            }
            case 1: {
                this.buf.append("APPEND [");
                this.appendFrameTypes(nLocal, local);
                this.buf.append(']');
                break;
            }
            case 2: {
                this.buf.append("CHOP ").append(nLocal);
                break;
            }
            case 3: {
                this.buf.append("SAME");
                break;
            }
            case 4: {
                this.buf.append("SAME1 ");
                this.appendFrameTypes(1, stack);
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitInsn(int opcode) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitIntInsn(int opcode, int operand) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ').append(opcode == 188 ? TYPES[operand] : Integer.toString(operand)).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitVarInsn(int opcode, int var) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ').append(var).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitTypeInsn(int opcode, String type) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        this.appendDescriptor(0, type);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        this.appendDescriptor(0, owner);
        this.buf.append('.').append(name).append(" : ");
        this.appendDescriptor(1, desc);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, desc);
            return;
        }
        this.doVisitMethodInsn(opcode, owner, name, desc, opcode == 185);
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            return;
        }
        this.doVisitMethodInsn(opcode, owner, name, desc, itf);
    }

    private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        this.appendDescriptor(0, owner);
        this.buf.append('.').append(name).append(' ');
        this.appendDescriptor(3, desc);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object ... bsmArgs) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
        this.buf.append(name);
        this.appendDescriptor(3, desc);
        this.buf.append(" [");
        this.buf.append('\n');
        this.buf.append(this.tab3);
        this.appendHandle(bsm);
        this.buf.append('\n');
        this.buf.append(this.tab3).append("// arguments:");
        if (bsmArgs.length == 0) {
            this.buf.append(" none");
        } else {
            this.buf.append('\n');
            for (int i = 0; i < bsmArgs.length; ++i) {
                this.buf.append(this.tab3);
                Object cst = bsmArgs[i];
                if (cst instanceof String) {
                    Printer.appendString(this.buf, (String)cst);
                } else if (cst instanceof Type) {
                    Type type = (Type)cst;
                    if (type.getSort() == 11) {
                        this.appendDescriptor(3, type.getDescriptor());
                    } else {
                        this.buf.append(type.getDescriptor()).append(".class");
                    }
                } else if (cst instanceof Handle) {
                    this.appendHandle((Handle)cst);
                } else {
                    this.buf.append(cst);
                }
                this.buf.append(", \n");
            }
            this.buf.setLength(this.buf.length() - 3);
        }
        this.buf.append('\n');
        this.buf.append(this.tab2).append("]\n");
        this.text.add(this.buf.toString());
    }

    public void visitJumpInsn(int opcode, Label label) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[opcode]).append(' ');
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitLabel(Label label) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitLdcInsn(Object cst) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LDC ");
        if (cst instanceof String) {
            Printer.appendString(this.buf, (String)cst);
        } else if (cst instanceof Type) {
            this.buf.append(((Type)cst).getDescriptor()).append(".class");
        } else {
            this.buf.append(cst);
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitIincInsn(int var, int increment) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("IINC ").append(var).append(' ').append(increment).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitTableSwitchInsn(int min, int max, Label dflt, Label ... labels) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TABLESWITCH\n");
        for (int i = 0; i < labels.length; ++i) {
            this.buf.append(this.tab3).append(min + i).append(": ");
            this.appendLabel(labels[i]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        this.appendLabel(dflt);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitLookupSwitchInsn(Label dflt, int[] keys2, Label[] labels) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
        for (int i = 0; i < labels.length; ++i) {
            this.buf.append(this.tab3).append(keys2[i]).append(": ");
            this.appendLabel(labels[i]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        this.appendLabel(dflt);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitMultiANewArrayInsn(String desc, int dims) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MULTIANEWARRAY ");
        this.appendDescriptor(1, desc);
        this.buf.append(' ').append(dims).append('\n');
        this.text.add(this.buf.toString());
    }

    public Printer visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return this.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
        this.appendLabel(start);
        this.buf.append(' ');
        this.appendLabel(end);
        this.buf.append(' ');
        this.appendLabel(handler);
        this.buf.append(' ');
        this.appendDescriptor(0, type);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public Printer visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
        this.appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(typeRef);
        this.buf.append(", ").append(typePath);
        this.buf.append(visible ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return t;
    }

    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE ").append(name).append(' ');
        this.appendDescriptor(1, desc);
        this.buf.append(' ');
        this.appendLabel(start);
        this.buf.append(' ');
        this.appendLabel(end);
        this.buf.append(' ').append(index2).append('\n');
        if (signature != null) {
            this.buf.append(this.tab2);
            this.appendDescriptor(2, signature);
            TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
            SignatureReader r = new SignatureReader(signature);
            r.acceptType(sv);
            this.buf.append(this.tab2).append("// declaration: ").append(sv.getDeclaration()).append('\n');
        }
        this.text.add(this.buf.toString());
    }

    public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index2, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE @");
        this.appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(typeRef);
        this.buf.append(", ").append(typePath);
        for (int i = 0; i < start.length; ++i) {
            this.buf.append(" [ ");
            this.appendLabel(start[i]);
            this.buf.append(" - ");
            this.appendLabel(end[i]);
            this.buf.append(" - ").append(index2[i]).append(" ]");
        }
        this.buf.append(visible ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return t;
    }

    public void visitLineNumber(int line, Label start) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LINENUMBER ").append(line).append(' ');
        this.appendLabel(start);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXSTACK = ").append(maxStack).append('\n');
        this.text.add(this.buf.toString());
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXLOCALS = ").append(maxLocals).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitMethodEnd() {
    }

    public Textifier visitAnnotation(String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        this.appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.text.add(visible ? ")\n" : ") // invisible\n");
        return t;
    }

    public Textifier visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        this.appendDescriptor(1, desc);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier t = this.createTextifier();
        this.text.add(t.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(typeRef);
        this.buf.append(", ").append(typePath);
        this.buf.append(visible ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return t;
    }

    public void visitAttribute(Attribute attr) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        this.appendDescriptor(-1, attr.type);
        if (attr instanceof Textifiable) {
            ((Textifiable)((Object)attr)).textify(this.buf, null);
        } else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }

    protected Textifier createTextifier() {
        return new Textifier();
    }

    protected void appendDescriptor(int type, String desc) {
        if (type == 5 || type == 2 || type == 4) {
            if (desc != null) {
                this.buf.append("// signature ").append(desc).append('\n');
            }
        } else {
            this.buf.append(desc);
        }
    }

    protected void appendLabel(Label l) {
        String name;
        if (this.labelNames == null) {
            this.labelNames = new HashMap<Label, String>();
        }
        if ((name = this.labelNames.get(l)) == null) {
            name = "L" + this.labelNames.size();
            this.labelNames.put(l, name);
        }
        this.buf.append(name);
    }

    protected void appendHandle(Handle h) {
        int tag = h.getTag();
        this.buf.append("// handle kind 0x").append(Integer.toHexString(tag)).append(" : ");
        boolean isMethodHandle = false;
        switch (tag) {
            case 1: {
                this.buf.append("GETFIELD");
                break;
            }
            case 2: {
                this.buf.append("GETSTATIC");
                break;
            }
            case 3: {
                this.buf.append("PUTFIELD");
                break;
            }
            case 4: {
                this.buf.append("PUTSTATIC");
                break;
            }
            case 9: {
                this.buf.append("INVOKEINTERFACE");
                isMethodHandle = true;
                break;
            }
            case 7: {
                this.buf.append("INVOKESPECIAL");
                isMethodHandle = true;
                break;
            }
            case 6: {
                this.buf.append("INVOKESTATIC");
                isMethodHandle = true;
                break;
            }
            case 5: {
                this.buf.append("INVOKEVIRTUAL");
                isMethodHandle = true;
                break;
            }
            case 8: {
                this.buf.append("NEWINVOKESPECIAL");
                isMethodHandle = true;
            }
        }
        this.buf.append('\n');
        this.buf.append(this.tab3);
        this.appendDescriptor(0, h.getOwner());
        this.buf.append('.');
        this.buf.append(h.getName());
        if (!isMethodHandle) {
            this.buf.append('(');
        }
        this.appendDescriptor(9, h.getDesc());
        if (!isMethodHandle) {
            this.buf.append(')');
        }
    }

    private void appendAccess(int access) {
        if ((access & 1) != 0) {
            this.buf.append("public ");
        }
        if ((access & 2) != 0) {
            this.buf.append("private ");
        }
        if ((access & 4) != 0) {
            this.buf.append("protected ");
        }
        if ((access & 0x10) != 0) {
            this.buf.append("final ");
        }
        if ((access & 8) != 0) {
            this.buf.append("static ");
        }
        if ((access & 0x20) != 0) {
            this.buf.append("synchronized ");
        }
        if ((access & 0x40) != 0) {
            this.buf.append("volatile ");
        }
        if ((access & 0x80) != 0) {
            this.buf.append("transient ");
        }
        if ((access & 0x400) != 0) {
            this.buf.append("abstract ");
        }
        if ((access & 0x800) != 0) {
            this.buf.append("strictfp ");
        }
        if ((access & 0x1000) != 0) {
            this.buf.append("synthetic ");
        }
        if ((access & 0x8000) != 0) {
            this.buf.append("mandated ");
        }
        if ((access & 0x4000) != 0) {
            this.buf.append("enum ");
        }
    }

    private void appendComa(int i) {
        if (i != 0) {
            this.buf.append(", ");
        }
    }

    private void appendTypeReference(int typeRef) {
        TypeReference ref = new TypeReference(typeRef);
        switch (ref.getSort()) {
            case 0: {
                this.buf.append("CLASS_TYPE_PARAMETER ").append(ref.getTypeParameterIndex());
                break;
            }
            case 1: {
                this.buf.append("METHOD_TYPE_PARAMETER ").append(ref.getTypeParameterIndex());
                break;
            }
            case 16: {
                this.buf.append("CLASS_EXTENDS ").append(ref.getSuperTypeIndex());
                break;
            }
            case 17: {
                this.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(ref.getTypeParameterIndex()).append(", ").append(ref.getTypeParameterBoundIndex());
                break;
            }
            case 18: {
                this.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(ref.getTypeParameterIndex()).append(", ").append(ref.getTypeParameterBoundIndex());
                break;
            }
            case 19: {
                this.buf.append("FIELD");
                break;
            }
            case 20: {
                this.buf.append("METHOD_RETURN");
                break;
            }
            case 21: {
                this.buf.append("METHOD_RECEIVER");
                break;
            }
            case 22: {
                this.buf.append("METHOD_FORMAL_PARAMETER ").append(ref.getFormalParameterIndex());
                break;
            }
            case 23: {
                this.buf.append("THROWS ").append(ref.getExceptionIndex());
                break;
            }
            case 64: {
                this.buf.append("LOCAL_VARIABLE");
                break;
            }
            case 65: {
                this.buf.append("RESOURCE_VARIABLE");
                break;
            }
            case 66: {
                this.buf.append("EXCEPTION_PARAMETER ").append(ref.getTryCatchBlockIndex());
                break;
            }
            case 67: {
                this.buf.append("INSTANCEOF");
                break;
            }
            case 68: {
                this.buf.append("NEW");
                break;
            }
            case 69: {
                this.buf.append("CONSTRUCTOR_REFERENCE");
                break;
            }
            case 70: {
                this.buf.append("METHOD_REFERENCE");
                break;
            }
            case 71: {
                this.buf.append("CAST ").append(ref.getTypeArgumentIndex());
                break;
            }
            case 72: {
                this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                break;
            }
            case 73: {
                this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                break;
            }
            case 74: {
                this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
                break;
            }
            case 75: {
                this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(ref.getTypeArgumentIndex());
            }
        }
    }

    private void appendFrameTypes(int n, Object[] o) {
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                this.buf.append(' ');
            }
            if (o[i] instanceof String) {
                String desc = (String)o[i];
                if (desc.startsWith("[")) {
                    this.appendDescriptor(1, desc);
                    continue;
                }
                this.appendDescriptor(0, desc);
                continue;
            }
            if (o[i] instanceof Integer) {
                switch ((Integer)o[i]) {
                    case 0: {
                        this.appendDescriptor(1, "T");
                        break;
                    }
                    case 1: {
                        this.appendDescriptor(1, "I");
                        break;
                    }
                    case 2: {
                        this.appendDescriptor(1, "F");
                        break;
                    }
                    case 3: {
                        this.appendDescriptor(1, "D");
                        break;
                    }
                    case 4: {
                        this.appendDescriptor(1, "J");
                        break;
                    }
                    case 5: {
                        this.appendDescriptor(1, "N");
                        break;
                    }
                    case 6: {
                        this.appendDescriptor(1, "U");
                    }
                }
                continue;
            }
            this.appendLabel((Label)o[i]);
        }
    }
}

