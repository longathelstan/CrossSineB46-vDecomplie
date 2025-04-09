/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.ParticleGenerator;
import org.jetbrains.annotations.NotNull;

@SideOnly(value=Side.CLIENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/ParticleUtils;", "", "()V", "particleGenerator", "Lnet/vitox/ParticleGenerator;", "drawParticles", "", "mouseX", "", "mouseY", "CrossSine"})
public final class ParticleUtils {
    @NotNull
    public static final ParticleUtils INSTANCE = new ParticleUtils();
    @NotNull
    private static final ParticleGenerator particleGenerator = new ParticleGenerator(100);

    private ParticleUtils() {
    }

    public final void drawParticles(int mouseX, int mouseY) {
        particleGenerator.draw(mouseX, mouseY);
    }
}

