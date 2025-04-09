/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_9to1_8.storage;

import com.viaversion.viarewind.protocol.v1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_8;
import java.util.HashMap;
import java.util.Map;

public class WindowTracker
extends StoredObject {
    private final HashMap<Short, String> types = new HashMap();
    private final HashMap<Short, Item[]> brewingItems = new HashMap();
    private final Map<Short, Short> enchantmentProperties = new HashMap<Short, Short>();

    public WindowTracker(UserConnection user) {
        super(user);
    }

    public String get(short windowId) {
        return this.types.get(windowId);
    }

    public void put(short windowId, String type) {
        this.types.put(windowId, type);
    }

    public void remove(short windowId) {
        this.types.remove(windowId);
        this.brewingItems.remove(windowId);
    }

    /*
     * Exception decompiling
     */
    public Item[] getBrewingItems(short windowId) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.UnsupportedOperationException
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:142)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:455)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:409)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:167)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:105)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredReturn.rewriteExpressions(StructuredReturn.java:99)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1137)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:912)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public short getEnchantmentValue(short key) {
        if (!this.enchantmentProperties.containsKey(key)) {
            return 0;
        }
        return this.enchantmentProperties.remove(key);
    }

    public void putEnchantmentProperty(short key, short value) {
        this.enchantmentProperties.put(key, value);
    }

    public void clearEnchantmentProperties() {
        this.enchantmentProperties.clear();
    }

    public static void updateBrewingStand(UserConnection user, Item blazePowder, short windowId) {
        if (blazePowder != null && blazePowder.identifier() != 377) {
            return;
        }
        int amount = blazePowder == null ? 0 : blazePowder.amount();
        PacketWrapper openWindow = PacketWrapper.create(ClientboundPackets1_8.OPEN_SCREEN, user);
        openWindow.write(Types.UNSIGNED_BYTE, windowId);
        openWindow.write(Types.STRING, "minecraft:brewing_stand");
        TextFormatting textFormatting = TextFormatting.DARK_GRAY;
        TextFormatting textFormatting2 = TextFormatting.DARK_RED;
        int n = amount;
        ATextComponent title2 = new StringComponent().append((ATextComponent)new TranslationComponent("container.brewing", new Object[0])).append((ATextComponent)new StringComponent(": " + textFormatting)).append((ATextComponent)new StringComponent(n + " " + textFormatting2)).append((ATextComponent)new TranslationComponent("item.blazePowder.name", TextFormatting.DARK_RED));
        openWindow.write(Types.COMPONENT, TextComponentSerializer.V1_8.serializeJson(title2));
        openWindow.write(Types.UNSIGNED_BYTE, (short)420);
        openWindow.scheduleSend(Protocol1_9To1_8.class);
        Item[] items = user.get(WindowTracker.class).getBrewingItems(windowId);
        for (int i = 0; i < items.length; ++i) {
            PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_8.CONTAINER_SET_SLOT, user);
            setSlot.write(Types.UNSIGNED_BYTE, windowId);
            setSlot.write(Types.SHORT, (short)i);
            setSlot.write(Types.ITEM1_8, items[i]);
            setSlot.scheduleSend(Protocol1_9To1_8.class);
        }
    }
}

