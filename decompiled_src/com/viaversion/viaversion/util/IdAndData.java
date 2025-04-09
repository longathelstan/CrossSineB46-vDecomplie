/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import java.util.Objects;

public class IdAndData {
    private int id;
    private byte data;

    public IdAndData(int id) {
        this.id = id;
        this.data = (byte)-1;
    }

    public IdAndData(int id, int data) {
        int n = data;
        int n2 = id;
        Preconditions.checkArgument((data >= 0 && data <= 15 ? 1 : 0) != 0, (Object)("Data has to be between 0 and 15: (id: " + n2 + " data: " + n + ")"));
        this.id = id;
        this.data = (byte)data;
    }

    public static int getId(int rawData) {
        return rawData >> 4;
    }

    public static int getData(int rawData) {
        return rawData & 0xF;
    }

    public static int toRawData(int id) {
        return id << 4;
    }

    public static int removeData(int data) {
        return data & 0xFFFFFFF0;
    }

    public static IdAndData fromRawData(int rawData) {
        return new IdAndData(rawData >> 4, rawData & 0xF);
    }

    public static int toRawData(int id, int data) {
        return id << 4 | data & 0xF;
    }

    public int toRawData() {
        return IdAndData.toRawData(this.id, this.data);
    }

    public IdAndData withData(int data) {
        return new IdAndData(this.id, data);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getData() {
        return this.data;
    }

    public void setData(int data) {
        this.data = (byte)data;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        IdAndData idAndData = (IdAndData)o;
        return this.id == idAndData.id && this.data == idAndData.data;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.data);
    }

    public String toString() {
        byte by = this.data;
        int n = this.id;
        return "IdAndData{id=" + n + ", data=" + by + "}";
    }
}

