/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.data;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.data.MappingData1_13;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Objects;

public final class RecipeData {
    public static Map<String, Recipe> recipes;

    public static void init() {
        InputStream stream = MappingData1_13.class.getClassLoader().getResourceAsStream("assets/viaversion/data/itemrecipes1_12_2to1_13.json");
        try (InputStreamReader reader = new InputStreamReader(stream);){
            recipes = (Map)GsonUtil.getGson().fromJson((Reader)reader, new TypeToken<Map<String, Recipe>>(){}.getType());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class Recipe {
        final String type;
        final String group;
        final int width;
        final int height;
        final float experience;
        final int cookingTime;
        final DataItem[] ingredient;
        final DataItem[][] ingredients;
        final DataItem result;

        public Recipe(String type, String group, int width, int height, float experience, int cookingTime, DataItem[] ingredient, DataItem[][] ingredients, DataItem result) {
            this.type = type;
            this.group = group;
            this.width = width;
            this.height = height;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.ingredient = ingredient;
            this.ingredients = ingredients;
            this.result = result;
        }

        public String type() {
            return this.type;
        }

        public String group() {
            return this.group;
        }

        public int width() {
            return this.width;
        }

        public int height() {
            return this.height;
        }

        public float experience() {
            return this.experience;
        }

        public int cookingTime() {
            return this.cookingTime;
        }

        public DataItem[] ingredient() {
            return this.ingredient;
        }

        public DataItem[][] ingredients() {
            return this.ingredients;
        }

        public DataItem result() {
            return this.result;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Recipe)) {
                return false;
            }
            Recipe recipe = (Recipe)object;
            return Objects.equals(this.type, recipe.type) && Objects.equals(this.group, recipe.group) && this.width == recipe.width && this.height == recipe.height && Float.compare(this.experience, recipe.experience) == 0 && this.cookingTime == recipe.cookingTime && Objects.equals(this.ingredient, recipe.ingredient) && Objects.equals(this.ingredients, recipe.ingredients) && Objects.equals(this.result, recipe.result);
        }

        public int hashCode() {
            return ((((((((0 * 31 + Objects.hashCode(this.type)) * 31 + Objects.hashCode(this.group)) * 31 + Integer.hashCode(this.width)) * 31 + Integer.hashCode(this.height)) * 31 + Float.hashCode(this.experience)) * 31 + Integer.hashCode(this.cookingTime)) * 31 + Objects.hashCode(this.ingredient)) * 31 + Objects.hashCode(this.ingredients)) * 31 + Objects.hashCode(this.result);
        }

        public String toString() {
            return String.format("%s[type=%s, group=%s, width=%s, height=%s, experience=%s, cookingTime=%s, ingredient=%s, ingredients=%s, result=%s]", this.getClass().getSimpleName(), Objects.toString(this.type), Objects.toString(this.group), Integer.toString(this.width), Integer.toString(this.height), Float.toString(this.experience), Integer.toString(this.cookingTime), Objects.toString(this.ingredient), Objects.toString(this.ingredients), Objects.toString(this.result));
        }
    }
}

