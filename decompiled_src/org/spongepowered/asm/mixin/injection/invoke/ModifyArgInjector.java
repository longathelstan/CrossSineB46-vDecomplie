/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.invoke;

import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.invoke.InvokeInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgInjector
extends InvokeInjector {
    private final int index;
    private final boolean singleArgMode;

    public ModifyArgInjector(InjectionInfo info, int index2) {
        super(info, "@ModifyArg");
        this.index = index2;
        this.singleArgMode = this.methodArgs.length == 1;
    }

    @Override
    protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
        super.sanityCheck(target, injectionPoints);
        if (this.singleArgMode && !this.methodArgs[0].equals(this.returnType)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type. ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
        }
    }

    @Override
    protected void checkTarget(Target target) {
        if (!this.isStatic && target.isStatic) {
            throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
        }
    }

    @Override
    protected void inject(Target target, InjectionNodes.InjectionNode node) {
        this.checkTargetForNode(target, node);
        super.inject(target, node);
    }

    @Override
    protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
        MethodInsnNode methodNode = (MethodInsnNode)node.getCurrentTarget();
        Type[] args2 = Type.getArgumentTypes(methodNode.desc);
        int argIndex = this.findArgIndex(target, args2);
        InsnList insns = new InsnList();
        int extraLocals = 0;
        extraLocals = this.singleArgMode ? this.injectSingleArgHandler(target, args2, argIndex, insns) : this.injectMultiArgHandler(target, args2, argIndex, insns);
        target.insns.insertBefore((AbstractInsnNode)methodNode, insns);
        target.addToLocals(extraLocals);
        target.addToStack(2 - (extraLocals - 1));
    }

    private int injectSingleArgHandler(Target target, Type[] args2, int argIndex, InsnList insns) {
        int[] argMap = this.storeArgs(target, args2, insns, argIndex);
        this.invokeHandlerWithArgs(args2, insns, argMap, argIndex, argIndex + 1);
        this.pushArgs(args2, insns, argMap, argIndex + 1, args2.length);
        return argMap[argMap.length - 1] - target.getMaxLocals() + args2[args2.length - 1].getSize();
    }

    private int injectMultiArgHandler(Target target, Type[] args2, int argIndex, InsnList insns) {
        if (!Arrays.equals(args2, this.methodArgs)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + Bytecode.getDescriptor(args2) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
        }
        int[] argMap = this.storeArgs(target, args2, insns, 0);
        this.pushArgs(args2, insns, argMap, 0, argIndex);
        this.invokeHandlerWithArgs(args2, insns, argMap, 0, args2.length);
        this.pushArgs(args2, insns, argMap, argIndex + 1, args2.length);
        return argMap[argMap.length - 1] - target.getMaxLocals() + args2[args2.length - 1].getSize();
    }

    protected int findArgIndex(Target target, Type[] args2) {
        if (this.index > -1) {
            if (this.index >= args2.length || !args2[this.index].equals(this.returnType)) {
                throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + Bytecode.getDescriptor(args2) + ", expected " + this.returnType + " on " + this);
            }
            return this.index;
        }
        int argIndex = -1;
        for (int arg = 0; arg < args2.length; ++arg) {
            if (!args2[arg].equals(this.returnType)) continue;
            if (argIndex != -1) {
                throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + argIndex + ", " + arg + "] matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this + ". Please specify index of desired arg.");
            }
            argIndex = arg;
        }
        if (argIndex == -1) {
            throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this);
        }
        return argIndex;
    }
}

