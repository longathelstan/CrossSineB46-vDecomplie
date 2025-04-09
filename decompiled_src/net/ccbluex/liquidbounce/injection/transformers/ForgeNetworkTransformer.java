/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.transformers;

import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class ForgeNetworkTransformer
implements IClassTransformer {
    public static boolean returnMethod() {
        return !Minecraft.func_71410_x().func_71387_A();
    }

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
            try {
                ClassNode classNode = ClassUtils.INSTANCE.toClassNode(basicClass);
                classNode.methods.stream().filter(methodNode -> methodNode.name.equals("handleVanilla")).forEach(methodNode -> {
                    LabelNode labelNode = new LabelNode();
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(3), new InsnNode(172), labelNode}));
                });
                return ClassUtils.INSTANCE.toBytes(classNode);
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        if (name.equals("net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler")) {
            try {
                ClassNode classNode = ClassUtils.INSTANCE.toClassNode(basicClass);
                classNode.methods.stream().filter(method -> method.name.equals("channelRead0")).forEach(methodNode -> {
                    LabelNode labelNode = new LabelNode();
                    methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(177), labelNode}));
                });
                return ClassUtils.INSTANCE.toBytes(classNode);
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return basicClass;
    }
}

