/*
 * Decompiled with CFR 0.152.
 */
package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.ReversedList;
import kotlin.collections.ReversedListReadOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=5, xi=49, d1={"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0004\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\b\u001a\u001d\u0010\t\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b\n\u00a8\u0006\u000b"}, d2={"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "kotlin-stdlib"}, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__ReversedViewsKt
extends CollectionsKt__MutableCollectionsKt {
    private static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reverseElementIndex, int index2) {
        if (!(0 <= index2 ? index2 <= CollectionsKt.getLastIndex($this$reverseElementIndex) : false)) {
            throw new IndexOutOfBoundsException("Element index " + index2 + " must be in range [" + new IntRange(0, CollectionsKt.getLastIndex($this$reverseElementIndex)) + "].");
        }
        return CollectionsKt.getLastIndex($this$reverseElementIndex) - index2;
    }

    private static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reversePositionIndex, int index2) {
        if (!(0 <= index2 ? index2 <= $this$reversePositionIndex.size() : false)) {
            throw new IndexOutOfBoundsException("Position index " + index2 + " must be in range [" + new IntRange(0, $this$reversePositionIndex.size()) + "].");
        }
        return $this$reversePositionIndex.size() - index2;
    }

    @NotNull
    public static final <T> List<T> asReversed(@NotNull List<? extends T> $this$asReversed) {
        Intrinsics.checkNotNullParameter($this$asReversed, "<this>");
        return new ReversedListReadOnly<T>($this$asReversed);
    }

    @JvmName(name="asReversedMutable")
    @NotNull
    public static final <T> List<T> asReversedMutable(@NotNull List<T> $this$asReversed) {
        Intrinsics.checkNotNullParameter($this$asReversed, "<this>");
        return new ReversedList<T>($this$asReversed);
    }

    public static final /* synthetic */ int access$reverseElementIndex(List $receiver, int index2) {
        return CollectionsKt__ReversedViewsKt.reverseElementIndex$CollectionsKt__ReversedViewsKt($receiver, index2);
    }

    public static final /* synthetic */ int access$reversePositionIndex(List $receiver, int index2) {
        return CollectionsKt__ReversedViewsKt.reversePositionIndex$CollectionsKt__ReversedViewsKt($receiver, index2);
    }
}

