/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.api.splitter;

import io.netty.buffer.ByteBuf;
import net.raphimc.vialegacy.protocol.release.r1_2_4_5tor1_3_1_2.data.NbtItemList1_2_4;

public class PreNettyTypes {
    public static void readString(ByteBuf buffer) {
        int s = buffer.readShort();
        for (int i = 0; i < s; ++i) {
            buffer.readShort();
        }
    }

    public static void readUTF(ByteBuf buffer) {
        int l = buffer.readUnsignedShort();
        for (int i = 0; i < l; ++i) {
            buffer.readByte();
        }
    }

    public static void readString64(ByteBuf buffer) {
        for (int i = 0; i < 64; ++i) {
            buffer.readByte();
        }
    }

    public static void readItemStack1_3_1(ByteBuf buffer) {
        short s = buffer.readShort();
        if (s >= 0) {
            buffer.readByte();
            buffer.readShort();
            PreNettyTypes.readTag(buffer);
        }
    }

    public static void readItemStack1_0(ByteBuf buffer) {
        short s = buffer.readShort();
        if (s >= 0) {
            buffer.readByte();
            buffer.readShort();
            if (NbtItemList1_2_4.hasNbt(s)) {
                PreNettyTypes.readTag(buffer);
            }
        }
    }

    public static void readItemStackb1_2(ByteBuf buffer) {
        short s = buffer.readShort();
        if (s >= 0) {
            buffer.readByte();
            buffer.readShort();
        }
    }

    public static void readItemStackb1_1(ByteBuf buffer) {
        short s = buffer.readShort();
        if (s >= 0) {
            buffer.readByte();
            buffer.readByte();
        }
    }

    public static void readByteArray(ByteBuf buffer) {
        int s = buffer.readShort();
        for (int i = 0; i < s; ++i) {
            buffer.readByte();
        }
    }

    public static void readByteArray1024(ByteBuf buffer) {
        for (int i = 0; i < 1024; ++i) {
            buffer.readByte();
        }
    }

    public static void readTag(ByteBuf buffer) {
        int s = buffer.readShort();
        for (int i = 0; i < s; ++i) {
            buffer.readByte();
        }
    }

    public static void readEntityDataList1_4_4(ByteBuf buffer) {
        byte b = buffer.readByte();
        while (b != 127) {
            int i = (b & 0xE0) >> 5;
            switch (i) {
                case 0: {
                    buffer.readByte();
                    break;
                }
                case 1: {
                    buffer.readShort();
                    break;
                }
                case 2: {
                    buffer.readInt();
                    break;
                }
                case 3: {
                    buffer.readFloat();
                    break;
                }
                case 4: {
                    PreNettyTypes.readString(buffer);
                    break;
                }
                case 5: {
                    PreNettyTypes.readItemStack1_3_1(buffer);
                    break;
                }
                case 6: {
                    buffer.readInt();
                    buffer.readInt();
                    buffer.readInt();
                }
            }
            b = buffer.readByte();
        }
    }

    public static void readEntityDataList1_4_2(ByteBuf buffer) {
        byte b = buffer.readByte();
        while (b != 127) {
            int i = (b & 0xE0) >> 5;
            switch (i) {
                case 0: {
                    buffer.readByte();
                    break;
                }
                case 1: {
                    buffer.readShort();
                    break;
                }
                case 2: {
                    buffer.readInt();
                    break;
                }
                case 3: {
                    buffer.readFloat();
                    break;
                }
                case 4: {
                    PreNettyTypes.readString(buffer);
                    break;
                }
                case 5: {
                    short x = buffer.readShort();
                    if (x <= -1) break;
                    buffer.readByte();
                    buffer.readShort();
                    break;
                }
                case 6: {
                    buffer.readInt();
                    buffer.readInt();
                    buffer.readInt();
                }
            }
            b = buffer.readByte();
        }
    }

    public static void readEntityDataListb1_5(ByteBuf buffer) {
        byte b = buffer.readByte();
        while (b != 127) {
            int i = (b & 0xE0) >> 5;
            switch (i) {
                case 0: {
                    buffer.readByte();
                    break;
                }
                case 1: {
                    buffer.readShort();
                    break;
                }
                case 2: {
                    buffer.readInt();
                    break;
                }
                case 3: {
                    buffer.readFloat();
                    break;
                }
                case 4: {
                    PreNettyTypes.readString(buffer);
                    break;
                }
                case 5: {
                    buffer.readShort();
                    buffer.readByte();
                    buffer.readShort();
                    break;
                }
                case 6: {
                    buffer.readInt();
                    buffer.readInt();
                    buffer.readInt();
                }
            }
            b = buffer.readByte();
        }
    }

    public static void readEntityDataListb1_3(ByteBuf buffer) {
        byte b = buffer.readByte();
        while (b != 127) {
            int i = (b & 0xE0) >> 5;
            switch (i) {
                case 0: {
                    buffer.readByte();
                    break;
                }
                case 1: {
                    buffer.readShort();
                    break;
                }
                case 2: {
                    buffer.readInt();
                    break;
                }
                case 3: {
                    buffer.readFloat();
                    break;
                }
                case 4: {
                    PreNettyTypes.readUTF(buffer);
                    break;
                }
                case 5: {
                    buffer.readShort();
                    buffer.readByte();
                    buffer.readShort();
                    break;
                }
                case 6: {
                    buffer.readInt();
                    buffer.readInt();
                    buffer.readInt();
                }
            }
            b = buffer.readByte();
        }
    }

    public static void readEntityDataListb1_2(ByteBuf buffer) {
        byte b = buffer.readByte();
        while (b != 127) {
            int i = (b & 0xE0) >> 5;
            switch (i) {
                case 0: {
                    buffer.readByte();
                    break;
                }
                case 1: {
                    buffer.readShort();
                    break;
                }
                case 2: {
                    buffer.readInt();
                    break;
                }
                case 3: {
                    buffer.readFloat();
                    break;
                }
                case 4: {
                    PreNettyTypes.readUTF(buffer);
                    break;
                }
                case 5: {
                    buffer.readShort();
                    buffer.readByte();
                    buffer.readShort();
                }
            }
            b = buffer.readByte();
        }
    }
}

