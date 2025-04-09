/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.data;

import com.viaversion.viaversion.util.IdAndData;
import java.util.HashMap;
import java.util.Map;

public class BlockHardnessList {
    private static final Map<Integer, Float> HARDNESS_TABLE = new HashMap<Integer, Float>(82, 0.99f);

    public static boolean canBeBrokenInstantly(int blockID) {
        return HARDNESS_TABLE.getOrDefault(blockID, Float.valueOf(0.0f)).floatValue() == 0.0f;
    }

    public static boolean canBeBrokenInstantly(IdAndData block) {
        return BlockHardnessList.canBeBrokenInstantly(block.getId());
    }

    static {
        HARDNESS_TABLE.put(1, Float.valueOf(1.5f));
        HARDNESS_TABLE.put(2, Float.valueOf(0.6f));
        HARDNESS_TABLE.put(3, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(4, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(5, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(6, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(7, Float.valueOf(-1.0f));
        HARDNESS_TABLE.put(8, Float.valueOf(100.0f));
        HARDNESS_TABLE.put(9, Float.valueOf(100.0f));
        HARDNESS_TABLE.put(10, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(11, Float.valueOf(100.0f));
        HARDNESS_TABLE.put(12, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(13, Float.valueOf(0.6f));
        HARDNESS_TABLE.put(14, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(15, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(16, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(17, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(18, Float.valueOf(0.2f));
        HARDNESS_TABLE.put(19, Float.valueOf(0.6f));
        HARDNESS_TABLE.put(20, Float.valueOf(0.3f));
        HARDNESS_TABLE.put(21, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(22, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(23, Float.valueOf(3.5f));
        HARDNESS_TABLE.put(24, Float.valueOf(0.8f));
        HARDNESS_TABLE.put(25, Float.valueOf(0.8f));
        HARDNESS_TABLE.put(35, Float.valueOf(0.8f));
        HARDNESS_TABLE.put(37, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(38, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(39, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(40, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(41, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(42, Float.valueOf(5.0f));
        HARDNESS_TABLE.put(43, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(44, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(45, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(46, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(47, Float.valueOf(1.5f));
        HARDNESS_TABLE.put(48, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(49, Float.valueOf(10.0f));
        HARDNESS_TABLE.put(50, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(51, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(52, Float.valueOf(5.0f));
        HARDNESS_TABLE.put(53, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(54, Float.valueOf(2.5f));
        HARDNESS_TABLE.put(55, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(56, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(57, Float.valueOf(5.0f));
        HARDNESS_TABLE.put(58, Float.valueOf(2.5f));
        HARDNESS_TABLE.put(59, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(60, Float.valueOf(0.6f));
        HARDNESS_TABLE.put(61, Float.valueOf(3.5f));
        HARDNESS_TABLE.put(62, Float.valueOf(3.5f));
        HARDNESS_TABLE.put(63, Float.valueOf(1.0f));
        HARDNESS_TABLE.put(64, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(65, Float.valueOf(0.4f));
        HARDNESS_TABLE.put(66, Float.valueOf(0.7f));
        HARDNESS_TABLE.put(67, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(68, Float.valueOf(1.0f));
        HARDNESS_TABLE.put(69, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(70, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(71, Float.valueOf(5.0f));
        HARDNESS_TABLE.put(72, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(73, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(74, Float.valueOf(3.0f));
        HARDNESS_TABLE.put(75, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(76, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(77, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(78, Float.valueOf(0.1f));
        HARDNESS_TABLE.put(79, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(80, Float.valueOf(0.2f));
        HARDNESS_TABLE.put(81, Float.valueOf(0.4f));
        HARDNESS_TABLE.put(82, Float.valueOf(0.6f));
        HARDNESS_TABLE.put(83, Float.valueOf(0.0f));
        HARDNESS_TABLE.put(84, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(85, Float.valueOf(2.0f));
        HARDNESS_TABLE.put(86, Float.valueOf(1.0f));
        HARDNESS_TABLE.put(87, Float.valueOf(0.4f));
        HARDNESS_TABLE.put(88, Float.valueOf(0.5f));
        HARDNESS_TABLE.put(89, Float.valueOf(0.3f));
        HARDNESS_TABLE.put(90, Float.valueOf(-1.0f));
        HARDNESS_TABLE.put(91, Float.valueOf(1.0f));
        HARDNESS_TABLE.put(92, Float.valueOf(0.5f));
    }
}

