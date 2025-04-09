/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0007J\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0005J\u000e\u0010\u001d\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0018J\u000e\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0018J\u0018\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0005H\u0002J\u001a\u0010\u001f\u001a\u00020\u00052\b\b\u0002\u0010 \u001a\u00020\u00182\b\b\u0002\u0010!\u001a\u00020\u0010R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\b\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2={"Lnet/ccbluex/liquidbounce/utils/misc/RandomUtils;", "", "()V", "ADJECTIVES", "", "", "[Ljava/lang/String;", "ANIMALS", "FILLER_CHARS", "leetMap", "", "random", "Ljava/util/Random;", "leetRandomly", "string", "nextBoolean", "", "nextDouble", "", "startInclusive", "endInclusive", "nextFloat", "", "nextInt", "", "endExclusive", "length", "chars", "", "randomNumber", "randomString", "randomUsername", "maxLength", "raw", "CrossSine"})
public final class RandomUtils {
    @NotNull
    public static final RandomUtils INSTANCE = new RandomUtils();
    @NotNull
    private static final java.util.Random random = new java.util.Random();
    @NotNull
    private static final String FILLER_CHARS = "0123456789_";
    @NotNull
    private static final Map<String, String> leetMap;
    @NotNull
    private static final String[] ADJECTIVES;
    @NotNull
    private static final String[] ANIMALS;

    private RandomUtils() {
    }

    public final boolean nextBoolean() {
        return new java.util.Random().nextBoolean();
    }

    @JvmStatic
    public static final int nextInt(int startInclusive, int endExclusive) {
        return endExclusive - startInclusive <= 0 ? startInclusive : startInclusive + random.nextInt(endExclusive - startInclusive);
    }

    public final double nextDouble(double startInclusive, double endInclusive) {
        return startInclusive == endInclusive || endInclusive - startInclusive <= 0.0 ? startInclusive : startInclusive + (endInclusive - startInclusive) * Math.random();
    }

    public final float nextFloat(float startInclusive, float endInclusive) {
        return startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f ? startInclusive : (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    @Nullable
    public final String random(int length, @NotNull String chars) {
        Intrinsics.checkNotNullParameter(chars, "chars");
        char[] cArray = chars.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "this as java.lang.String).toCharArray()");
        return this.random(length, cArray);
    }

    @Nullable
    public final String random(int length, @NotNull char[] chars) {
        Intrinsics.checkNotNullParameter(chars, "chars");
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < length) {
            int i = n++;
            stringBuilder.append(chars[new java.util.Random().nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

    @NotNull
    public final String randomNumber(int length) {
        return this.randomString(length, "123456789");
    }

    @NotNull
    public final String randomString(int length) {
        return this.randomString(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    private final String randomString(int length, String chars) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < length) {
            int i = n++;
            char[] cArray = chars.toCharArray();
            Intrinsics.checkNotNullExpressionValue(cArray, "this as java.lang.String).toCharArray()");
            stringBuilder.append(chars.charAt(random.nextInt(cArray.length)));
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
        return string;
    }

    @NotNull
    public final String randomUsername(int maxLength, boolean raw) {
        String[] $this$filterTo$iv$iv;
        Object object;
        boolean $i$f$filter;
        String[] $this$filter$iv;
        if (!GuiAltManager.Companion.getStylisedAlts()) {
            return this.randomString(maxLength);
        }
        String adjective = null;
        String animal = null;
        if (this.nextBoolean()) {
            String it;
            $this$filter$iv = ADJECTIVES;
            $i$f$filter = false;
            object = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (String element$iv$iv : $this$filterTo$iv$iv) {
                it = element$iv$iv;
                boolean bl = false;
                if (!(it.length() <= maxLength - 3)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            adjective = (String)CollectionsKt.random((List)destination$iv$iv, Random.Default);
            $this$filter$iv = ANIMALS;
            $i$f$filter = false;
            $this$filterTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            $i$f$filterTo = false;
            for (String element$iv$iv : $this$filterTo$iv$iv) {
                it = element$iv$iv;
                boolean bl = false;
                if (!(it.length() <= maxLength - adjective.length())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            animal = (String)CollectionsKt.random((List)destination$iv$iv, Random.Default);
        } else {
            String it;
            $this$filter$iv = ANIMALS;
            $i$f$filter = false;
            $this$filterTo$iv$iv = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (String element$iv$iv : $this$filterTo$iv$iv) {
                it = element$iv$iv;
                boolean bl = false;
                if (!(it.length() <= maxLength - 3)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            animal = (String)CollectionsKt.random((List)destination$iv$iv, Random.Default);
            $this$filter$iv = ADJECTIVES;
            $i$f$filter = false;
            $this$filterTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            $i$f$filterTo = false;
            for (String element$iv$iv : $this$filterTo$iv$iv) {
                it = element$iv$iv;
                boolean bl = false;
                if (!(it.length() <= maxLength - animal.length())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            adjective = (String)CollectionsKt.random((List)destination$iv$iv, Random.Default);
        }
        if (!raw) {
            return adjective + (adjective.length() + animal.length() < maxLength ? "_" : "") + animal;
        }
        String baseName = this.leetRandomly(adjective) + (adjective.length() + animal.length() < maxLength ? this.random(1, FILLER_CHARS) : "") + this.leetRandomly(animal);
        int fillerCount = maxLength - baseName.length();
        object = new StringBuilder(this.random(fillerCount, FILLER_CHARS)).insert(RandomUtils.nextInt(0, fillerCount), baseName).toString();
        Intrinsics.checkNotNullExpressionValue(object, "StringBuilder(random(fil\u2026nt), baseName).toString()");
        return object;
    }

    public static /* synthetic */ String randomUsername$default(RandomUtils randomUtils, int n, boolean bl, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = GuiAltManager.Companion.getAltsLength();
        }
        if ((n2 & 2) != 0) {
            bl = GuiAltManager.Companion.getUnformattedAlts();
        }
        return randomUtils.randomUsername(n, bl);
    }

    /*
     * WARNING - void declaration
     */
    private final String leetRandomly(String string) {
        void $this$mapIndexedTo$iv$iv;
        CharSequence $this$mapIndexed$iv = string;
        boolean $i$f$mapIndexed = false;
        CharSequence charSequence = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList($this$mapIndexed$iv.length());
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        void var8_8 = $this$mapIndexedTo$iv$iv;
        int n = 0;
        while (n < var8_8.length()) {
            Object object;
            void char_;
            void i;
            char item$iv$iv = var8_8.charAt(n);
            ++n;
            int n2 = index$iv$iv;
            index$iv$iv = n2 + 1;
            char c = item$iv$iv;
            int n3 = n2;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (i != false && i != StringsKt.getLastIndex(string) && INSTANCE.nextBoolean()) {
                String string2 = String.valueOf((char)char_).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                object = leetMap.get(string2);
                if (object == null) {
                    object = Character.valueOf((char)char_);
                }
            } else {
                object = Character.valueOf((char)char_);
            }
            collection.add(object);
        }
        return CollectionsKt.joinToString$default((List)destination$iv$iv, "", null, null, 0, null, null, 62, null);
    }

    static {
        Object[] objectArray = new Pair[]{TuplesKt.to("a", "4"), TuplesKt.to("b", "8"), TuplesKt.to("e", "3"), TuplesKt.to("g", "6"), TuplesKt.to("i", "1"), TuplesKt.to("o", "0"), TuplesKt.to("s", "5"), TuplesKt.to("t", "7"), TuplesKt.to("z", "2")};
        leetMap = MapsKt.mapOf(objectArray);
        objectArray = new String[]{"Abandoned", "Able", "Absolute", "Academic", "Acceptable", "Acclaimed", "Accomplished", "Accurate", "Aching", "Acidic", "Acrobatic", "Adorable", "Adventurous", "Babyish", "Back", "Bad", "Baggy", "Bare", "Barren", "Basic", "Beautiful", "Belated", "Beloved", "Calculating", "Calm", "Candid", "Canine", "Capital", "Carefree", "Careful", "Careless", "Caring", "Cautious", "Cavernous", "Celebrated", "Charming", "Damaged", "Damp", "Dangerous", "Dapper", "Daring", "Dark", "Darling", "Dazzling", "Dead", "Deadly", "Deafening", "Dear", "Dearest", "Each", "Eager", "Early", "Earnest", "Easy", "Easygoing", "Ecstatic", "Edible", "Educated", "Fabulous", "Failing", "Faint", "Fair", "Faithful", "Fake", "Familiar", "Famous", "Fancy", "Fantastic", "Far", "Faraway", "Farflung", "Faroff", "Gargantuan", "Gaseous", "General", "Generous", "Gentle", "Genuine", "Giant", "Giddy", "Gigantic", "Hairy", "Half", "Handmade", "Handsome", "Handy", "Happy", "Happygolucky", "Hard", "Icky", "Icy", "Ideal", "Idealistic", "Identical", "Idiotic", "Idle", "Idolized", "Ignorant", "Ill", "Illegal", "Jaded", "Jagged", "Jampacked", "Kaleidoscopic", "Keen", "Lame", "Lanky", "Large", "Last", "Lasting", "Late", "Lavish", "Lawful", "Mad", "Madeup", "Magnificent", "Majestic", "Major", "Male", "Mammoth", "Married", "Marvelous", "Naive", "Narrow", "Nasty", "Natural", "Naughty", "Obedient", "Obese", "Oblong", "Oblong", "Obvious", "Occasional", "Oily", "Palatable", "Pale", "Paltry", "Parallel", "Parched", "Partial", "Passionate", "Past", "Pastel", "Peaceful", "Peppery", "Perfect", "Perfumed", "Quaint", "Qualified", "Radiant", "Ragged", "Rapid", "Rare", "Rash", "Raw", "Recent", "Reckless", "Rectangular", "Sad", "Safe", "Salty", "Same", "Sandy", "Sane", "Sarcastic", "Sardonic", "Satisfied", "Scaly", "Scarce", "Scared", "Scary", "Scented", "Scholarly", "Scientific", "Scornful", "Scratchy", "Scrawny", "Second", "Secondary", "Secondhand", "Secret", "Selfassured", "Selfish", "Selfreliant", "Sentimental", "Talkative", "Tall", "Tame", "Tan", "Tangible", "Tart", "Tasty", "Tattered", "Taut", "Tedious", "Teeming", "Ugly", "Ultimate", "Unacceptable", "Unaware", "Uncomfortable", "Uncommon", "Unconscious", "Understated", "Unequaled", "Vacant", "Vague", "Vain", "Valid", "Wan", "Warlike", "Warm", "Warmhearted", "Warped", "Wary", "Wasteful", "Watchful", "Waterlogged", "Watery", "Wavy", "Yawning", "Yearly", "Zany", "False", "Active", "Actual", "Adept", "Admirable", "Admired", "Adolescent", "Adorable", "Adored", "Advanced", "Affectionate", "Afraid", "Aged", "Aggravating", "Beneficial", "Best", "Better", "Bewitched", "Big", "Bighearted", "Biodegradable", "Bitesized", "Bitter", "Black", "Cheap", "Cheerful", "Cheery", "Chief", "Chilly", "Chubby", "Circular", "Classic", "Clean", "Clear", "Clearcut", "Clever", "Close", "Closed", "Decent", "Decimal", "Decisive", "Deep", "Defenseless", "Defensive", "Defiant", "Deficient", "Definite", "Definitive", "Delayed", "Delectable", "Delicious", "Elaborate", "Elastic", "Elated", "Elderly", "Electric", "Elegant", "Elementary", "Elliptical", "Embarrassed", "Fast", "Fat", "Fatal", "Fatherly", "Favorable", "Favorite", "Fearful", "Fearless", "Feisty", "Feline", "Female", "Feminine", "Few", "Fickle", "Gifted", "Giving", "Glamorous", "Glaring", "Glass", "Gleaming", "Gleeful", "Glistening", "Glittering", "Hardtofind", "Harmful", "Harmless", "Harmonious", "Harsh", "Hasty", "Hateful", "Haunting", "Illfated", "Illinformed", "Illiterate", "Illustrious", "Imaginary", "Imaginative", "Immaculate", "Immaterial", "Immediate", "Immense", "Impassioned", "Jaunty", "Jealous", "Jittery", "Key", "Kind", "Lazy", "Leading", "Leafy", "Lean", "Left", "Legal", "Legitimate", "Light", "Masculine", "Massive", "Mature", "Meager", "Mealy", "Mean", "Measly", "Meaty", "Medical", "Mediocre", "Nautical", "Near", "Neat", "Necessary", "Needy", "Odd", "Oddball", "Offbeat", "Offensive", "Official", "Old", "Periodic", "Perky", "Personal", "Pertinent", "Pesky", "Pessimistic", "Petty", "Phony", "Physical", "Piercing", "Pink", "Pitiful", "Plain", "Quarrelsome", "Quarterly", "Ready", "Real", "Realistic", "Reasonable", "Red", "Reflecting", "Regal", "Regular", "Separate", "Serene", "Serious", "Serpentine", "Several", "Severe", "Shabby", "Shadowy", "Shady", "Shallow", "Shameful", "Shameless", "Sharp", "Shimmering", "Shiny", "Shocked", "Shocking", "Shoddy", "Short", "Shortterm", "Showy", "Shrill", "Shy", "Sick", "Silent", "Silky", "Tempting", "Tender", "Tense", "Tepid", "Terrible", "Terrific", "Testy", "Thankful", "That", "These", "Uneven", "Unfinished", "Unfit", "Unfolded", "Unfortunate", "Unhappy", "Unhealthy", "Uniform", "Unimportant", "Unique", "Valuable", "Vapid", "Variable", "Vast", "Velvety", "Weak", "Wealthy", "Weary", "Webbed", "Wee", "Weekly", "Weepy", "Weighty", "Weird", "Welcome", "Yellow", "Zealous", "Aggressive", "Agile", "Agitated", "Agonizing", "Agreeable", "Ajar", "Alarmed", "Alarming", "Alert", "Alienated", "Alive", "All", "Altruistic", "Blackandwhite", "Bland", "Blank", "Blaring", "Bleak", "Blind", "Blissful", "Blond", "Blue", "Blushing", "Cloudy", "Clueless", "Clumsy", "Cluttered", "Coarse", "Cold", "Colorful", "Colorless", "Colossal", "Comfortable", "Common", "Compassionate", "Competent", "Complete", "Delightful", "Delirious", "Demanding", "Dense", "Dental", "Dependable", "Dependent", "Descriptive", "Deserted", "Detailed", "Determined", "Devoted", "Different", "Embellished", "Eminent", "Emotional", "Empty", "Enchanted", "Enchanting", "Energetic", "Enlightened", "Enormous", "Filthy", "Fine", "Finished", "Firm", "First", "Firsthand", "Fitting", "Fixed", "Flaky", "Flamboyant", "Flashy", "Flat", "Flawed", "Flawless", "Flickering", "Gloomy", "Glorious", "Glossy", "Glum", "Golden", "Good", "Goodnatured", "Gorgeous", "Graceful", "Healthy", "Heartfelt", "Hearty", "Heavenly", "Heavy", "Hefty", "Helpful", "Helpless", "Impartial", "Impeccable", "Imperfect", "Imperturbable", "Impish", "Impolite", "Important", "Impossible", "Impractical", "Impressive", "Improbable", "Joint", "Jolly", "Jovial", "Kindhearted", "Kindly", "Lighthearted", "Likable", "Likely", "Limited", "Limp", "Limping", "Linear", "Lined", "Liquid", "Medium", "Meek", "Mellow", "Melodic", "Memorable", "Menacing", "Merry", "Messy", "Metallic", "Mild", "Negative", "Neglected", "Negligible", "Neighboring", "Nervous", "New", "Oldfashioned", "Only", "Open", "Optimal", "Optimistic", "Opulent", "Plaintive", "Plastic", "Playful", "Pleasant", "Pleased", "Pleasing", "Plump", "Plush", "Pointed", "Pointless", "Poised", "Polished", "Polite", "Political", "Queasy", "Querulous", "Reliable", "Relieved", "Remarkable", "Remorseful", "Remote", "Repentant", "Required", "Respectful", "Responsible", "Silly", "Silver", "Similar", "Simple", "Simplistic", "Sinful", "Single", "Sizzling", "Skeletal", "Skinny", "Sleepy", "Slight", "Slim", "Slimy", "Slippery", "Slow", "Slushy", "Small", "Smart", "Smoggy", "Smooth", "Smug", "Snappy", "Snarling", "Sneaky", "Sniveling", "Snoopy", "Thick", "Thin", "Third", "Thirsty", "This", "Thorny", "Thorough", "Those", "Thoughtful", "Threadbare", "United", "Unkempt", "Unknown", "Unlawful", "Unlined", "Unlucky", "Unnatural", "Unpleasant", "Unrealistic", "Venerated", "Vengeful", "Verifiable", "Vibrant", "Vicious", "Wellgroomed", "Wellinformed", "Welllit", "Wellmade", "Welloff", "Welltodo", "Wellworn", "Wet", "Which", "Whimsical", "Whirlwind", "Whispered", "Yellowish", "Zesty", "Amazing", "Ambitious", "Ample", "Amused", "Amusing", "Anchored", "Ancient", "Angelic", "Angry", "Anguished", "Animated", "Annual", "Another", "Antique", "Bogus", "Boiling", "Bold", "Bony", "Boring", "Bossy", "Both", "Bouncy", "Bountiful", "Bowed", "Complex", "Complicated", "Composed", "Concerned", "Concrete", "Confused", "Conscious", "Considerate", "Constant", "Content", "Conventional", "Cooked", "Cool", "Cooperative", "Difficult", "Digital", "Diligent", "Dim", "Dimpled", "Dimwitted", "Direct", "Disastrous", "Discrete", "Disfigured", "Disgusting", "Disloyal", "Dismal", "Enraged", "Entire", "Envious", "Equal", "Equatorial", "Essential", "Esteemed", "Ethical", "Euphoric", "Flimsy", "Flippant", "Flowery", "Fluffy", "Fluid", "Flustered", "Focused", "Fond", "Foolhardy", "Foolish", "Forceful", "Forked", "Formal", "Forsaken", "Gracious", "Grand", "Grandiose", "Granular", "Grateful", "Grave", "Gray", "Great", "Greedy", "Green", "Hidden", "Hideous", "High", "Highlevel", "Hilarious", "Hoarse", "Hollow", "Homely", "Impure", "Inborn", "Incomparable", "Incompatible", "Incomplete", "Incredible", "Indelible", "Indolent", "Inexperienced", "Infamous", "Infantile", "Joyful", "Joyous", "Jubilant", "Klutzy", "Knobby", "Little", "Live", "Lively", "Livid", "Loathsome", "Lone", "Lonely", "Long", "Milky", "Mindless", "Miniature", "Minor", "Minty", "Miserable", "Miserly", "Misguided", "Misty", "Mixed", "Next", "Nice", "Nifty", "Nimble", "Nippy", "Orange", "Orderly", "Ordinary", "Organic", "Ornate", "Ornery", "Poor", "Popular", "Portly", "Posh", "Positive", "Possible", "Potable", "Powerful", "Powerless", "Practical", "Precious", "Present", "Prestigious", "Questionable", "Quick", "Repulsive", "Revolving", "Rewarding", "Rich", "Right", "Rigid", "Ringed", "Ripe", "Sociable", "Soft", "Soggy", "Solid", "Somber", "Some", "Sophisticated", "Sore", "Sorrowful", "Soulful", "Soupy", "Sour", "Spanish", "Sparkling", "Sparse", "Specific", "Spectacular", "Speedy", "Spherical", "Spicy", "Spiffy", "Spirited", "Spiteful", "Splendid", "Spotless", "Spotted", "Spry", "Thrifty", "Thunderous", "Tidy", "Tight", "Timely", "Tinted", "Tiny", "Tired", "Torn", "Total", "Unripe", "Unruly", "Unselfish", "Unsightly", "Unsteady", "Unsung", "Untidy", "Untimely", "Untried", "Victorious", "Vigilant", "Vigorous", "Villainous", "Violet", "White", "Whole", "Whopping", "Wicked", "Wide", "Wideeyed", "Wiggly", "Wild", "Willing", "Wilted", "Winding", "Windy", "Young", "Zigzag", "Anxious", "Any", "Apprehensive", "Appropriate", "Apt", "Arctic", "Arid", "Aromatic", "Artistic", "Ashamed", "Assured", "Astonishing", "Athletic", "Brave", "Breakable", "Brief", "Bright", "Brilliant", "Brisk", "Broken", "Bronze", "Brown", "Bruised", "Coordinated", "Corny", "Corrupt", "Costly", "Courageous", "Courteous", "Crafty", "Crazy", "Creamy", "Creative", "Creepy", "Criminal", "Crisp", "Dirty", "Disguised", "Dishonest", "Dismal", "Distant", "Distant", "Distinct", "Distorted", "Dizzy", "Dopey", "Downright", "Dreary", "Even", "Evergreen", "Everlasting", "Every", "Evil", "Exalted", "Excellent", "Excitable", "Exemplary", "Exhausted", "Forthright", "Fortunate", "Fragrant", "Frail", "Frank", "Frayed", "Free", "French", "Frequent", "Fresh", "Friendly", "Frightened", "Frightening", "Frigid", "Gregarious", "Grim", "Grimy", "Gripping", "Grizzled", "Gross", "Grotesque", "Grouchy", "Grounded", "Honest", "Honorable", "Honored", "Hopeful", "Horrible", "Hospitable", "Hot", "Huge", "Infatuated", "Inferior", "Infinite", "Informal", "Innocent", "Insecure", "Insidious", "Insignificant", "Insistent", "Instructive", "Insubstantial", "Judicious", "Juicy", "Jumbo", "Knotty", "Knowing", "Knowledgeable", "Longterm", "Loose", "Lopsided", "Lost", "Loud", "Lovable", "Lovely", "Loving", "Modern", "Modest", "Moist", "Monstrous", "Monthly", "Monumental", "Moral", "Mortified", "Motherly", "Motionless", "Nocturnal", "Noisy", "Nonstop", "Normal", "Notable", "Noted", "Original", "Other", "Our", "Outgoing", "Outlandish", "Outlying", "Precious", "Pretty", "Previous", "Pricey", "Prickly", "Primary", "Prime", "Pristine", "Private", "Prize", "Probable", "Productive", "Profitable", "Quickwitted", "Quiet", "Roasted", "Robust", "Rosy", "Rotating", "Rotten", "Rough", "Round", "Rowdy", "Square", "Squeaky", "Squiggly", "Stable", "Staid", "Stained", "Stale", "Standard", "Starchy", "Stark", "Starry", "Steel", "Steep", "Sticky", "Stiff", "Stimulating", "Stingy", "Stormy", "Straight", "Strange", "Strict", "Strident", "Striking", "Striped", "Strong", "Studious", "Stunning", "Tough", "Tragic", "Trained", "Traumatic", "Treasured", "Tremendous", "Tremendous", "Triangular", "Tricky", "Trifling", "Trim", "Untrue", "Unused", "Unusual", "Unwelcome", "Unwieldy", "Unwilling", "Unwitting", "Unwritten", "Upbeat", "Violent", "Virtual", "Virtuous", "Visible", "Winged", "Wiry", "Wise", "Witty", "Wobbly", "Woeful", "Wonderful", "Wooden", "Woozy", "Wordy", "Worldly", "Worn", "Youthful", "Attached", "Attentive", "Attractive", "Austere", "Authentic", "Authorized", "Automatic", "Avaricious", "Average", "Aware", "Awesome", "Awful", "Awkward", "Bubbly", "Bulky", "Bumpy", "Buoyant", "Burdensome", "Burly", "Bustling", "Busy", "Buttery", "Buzzing", "Critical", "Crooked", "Crowded", "Cruel", "Crushing", "Cuddly", "Cultivated", "Cultured", "Cumbersome", "Curly", "Curvy", "Cute", "Cylindrical", "Doting", "Double", "Downright", "Drab", "Drafty", "Dramatic", "Dreary", "Droopy", "Dry", "Dual", "Dull", "Dutiful", "Excited", "Exciting", "Exotic", "Expensive", "Experienced", "Expert", "Extralarge", "Extraneous", "Extrasmall", "Extroverted", "Frilly", "Frivolous", "Frizzy", "Front", "Frosty", "Frozen", "Frugal", "Fruitful", "Full", "Fumbling", "Functional", "Funny", "Fussy", "Fuzzy", "Growing", "Growling", "Grown", "Grubby", "Gruesome", "Grumpy", "Guilty", "Gullible", "Gummy", "Humble", "Humiliating", "Humming", "Humongous", "Hungry", "Hurtful", "Husky", "Intelligent", "Intent", "Intentional", "Interesting", "Internal", "International", "Intrepid", "Ironclad", "Irresponsible", "Irritating", "Itchy", "Jumpy", "Junior", "Juvenile", "Known", "Kooky", "Kosher", "Low", "Loyal", "Lucky", "Lumbering", "Luminous", "Lumpy", "Lustrous", "Luxurious", "Mountainous", "Muddy", "Muffled", "Multicolored", "Mundane", "Murky", "Mushy", "Musty", "Muted", "Mysterious", "Noteworthy", "Novel", "Noxious", "Numb", "Nutritious", "Nutty", "Onerlooked", "Outrageous", "Outstanding", "Oval", "Overcooked", "Overdue", "Overjoyed", "Profuse", "Proper", "Proud", "Prudent", "Punctual", "Pungent", "Puny", "Pure", "Purple", "Pushy", "Putrid", "Puzzled", "Puzzling", "Quirky", "Quixotic", "Quizzical", "Royal", "Rubbery", "Ruddy", "Rude", "Rundown", "Runny", "Rural", "Rusty", "Stupendous", "Stupid", "Sturdy", "Stylish", "Subdued", "Submissive", "Substantial", "Subtle", "Suburban", "Sudden", "Sugary", "Sunny", "Super", "Superb", "Superficial", "Superior", "Supportive", "Surefooted", "Surprised", "Suspicious", "Svelte", "Sweaty", "Sweet", "Sweltering", "Swift", "Sympathetic", "Trivial", "Troubled", "Trusting", "Trustworthy", "Trusty", "Truthful", "Tubby", "Turbulent", "Twin", "Upright", "Upset", "Urban", "Usable", "Used", "Useful", "Useless", "Utilized", "Utter", "Vital", "Vivacious", "Vivid", "Voluminous", "Worried", "Worrisome", "Worse", "Worst", "Worthless", "Worthwhile", "Worthy", "Wrathful", "Wretched", "Writhing", "Wrong", "Wry", "Yummy", "True", "Aliceblue", "Antiquewhite", "Aqua", "Aquamarine", "Azure", "Beige", "Bisque", "Black", "Blue", "Blueviolet", "Brown", "Burlywood", "Cadetblue", "Chartreuse", "Chocolate", "Coral", "Cornsilk", "Crimson", "Cyan", "Darkblue", "Darkcyan", "Darkgoldenrod", "Darkgray", "Darkgreen", "Darkgrey", "Darkkhaki", "Darkmagenta", "Darkorange", "Darkorchid", "Darkred", "Darksalmon", "Darkseagreen", "Darkslateblue", "Darkslategray", "Darkslategrey", "Darkturquoise", "Darkviolet", "Deeppink", "Deepskyblue", "Dimgray", "Dimgrey", "Dodgerblue", "Firebrick", "Floralwhite", "Forestgreen", "Fractal", "Fuchsia", "Gainsboro", "Ghostwhite", "Gold", "Goldenrod", "Gray", "Green", "Greenyellow", "Honeydew", "Hotpink", "Indianred", "Indigo", "Ivory", "Khaki", "Lavender", "Lavenderblush", "Lawngreen", "Lemonchiffon", "Lightblue", "Lightcoral", "Lightcyan", "Lightgray", "Lightgreen", "Lightgrey", "Lightpink", "Lightsalmon", "Lightseagreen", "Lightskyblue", "Lightyellow", "Lime", "Limegreen", "Linen", "Magenta", "Maroon", "Mediumblue", "Mediumorchid", "Mediumpurple", "Midnightblue", "Mintcream", "Mistyrose", "Moccasin", "Navajowhite", "Navy", "Navyblue", "Oldlace", "Olive", "Olivedrab", "Opaque", "Orange", "Orangered", "Orchid", "Palegoldenrod", "Palegreen", "Paleturquoise", "Palevioletred", "Papayawhip", "Peachpuff", "Peru", "Pink", "Plum", "Powderblue", "Purple", "Red", "Rosybrown", "Royalblue", "Saddlebrown", "Salmon", "Sandybrown", "Seagreen", "Seashell", "Sienna", "Silver", "Skyblue", "Slateblue", "Slategray", "Slategrey", "Snow", "Springgreen", "Steelblue", "Tan", "Teal", "Thistle", "Tomato", "Transparent", "Turquoise", "Violet", "Violetred", "Wheat", "White", "Whitesmoke", "Yellow", "Yellowgreen"};
        ADJECTIVES = objectArray;
        objectArray = new String[]{"Aardvark", "Aardwolf", "Abalone", "Abyssiniancat", "Acaciarat", "Achillestang", "Acornbarnacle", "Acornweevil", "Acouchi", "Addax", "Adder", "Adeliepenguin", "Adouri", "Aegeancat", "Affenpinscher", "Afghanhound", "Africancivet", "Africanjacana", "Agama", "Agouti", "Aidi", "Airedale", "Akitainu", "Alaskajingle", "Alaskanhusky", "Albacoretuna", "Albatross", "Albertosaurus", "Albino", "Alleycat", "Alligator", "Alligatorgar", "Allosaurus", "Alpaca", "Alpinegoat", "Amazondolphin", "Amazonparrot", "Amazontreeboa", "Amberpenshell", "Ambushbug", "Americancrow", "Americancurl", "Americanrobin", "Americantoad", "Ammonite", "Amoeba", "Amphibian", "Amphiuma", "Amurminnow", "Amurratsnake", "Amurstarfish", "Anaconda", "Anchovy", "Andeancat", "Andeancondor", "Anemone", "Anemonecrab", "Anemoneshrimp", "Angelfish", "Anglerfish", "Angora", "Angwantibo", "Anhinga", "Ankole", "Ankolewatusi", "Annelid", "Annelida", "Anole", "Ant", "Antbear", "Anteater", "Antelope", "Antlion", "Anura", "Aoudad", "Apatosaur", "Ape", "Aphid", "Appaloosa", "Aquaticleech", "Arabianhorse", "Arabianoryx", "Aracari", "Arachnid", "Arawana", "Archaeocete", "Archaeopteryx", "Archerfish", "Arcticduck", "Arcticfox", "Arctichare", "Arcticseal", "Arcticwolf", "Argali", "Argusfish", "Arieltoucan", "Arkshell", "Armadillo", "Armedcrab", "Armyant", "Armyworm", "Arowana", "Arrowcrab", "Arrowworm", "Arthropods", "Aruanas", "Asianelephant", "Asianlion", "Asp", "Ass", "Assassinbug", "Astarte", "Atlasmoth", "Auk", "Auklet", "Aurochs", "Avians", "Avocet", "Axisdeer", "Axolotl", "Ayeaye", "Aztecant", "Azurevase", "Babirusa", "Baboon", "Backswimmer", "Bactrian", "Badger", "Bagworm", "Baiji", "Baldeagle", "Baleenwhale", "Balloonfish", "Ballpython", "Bandicoot", "Bangeltiger", "Bantamrooster", "Banteng", "Barasinga", "Barasingha", "Barb", "Barbet", "Barebirdbat", "Barnacle", "Barnowl", "Barnswallow", "Barracuda", "Basenji", "Basil", "Basilisk", "Bass", "Bassethound", "Bat", "Bats", "Beagle", "Bear", "Beardedcollie", "Beardeddragon", "Beauceron", "Beaver", "Bedbug", "Bee", "Beetle", "Bellfrog", "Bellsnake", "Belugawhale", "Bengaltiger", "Bergerpicard", "Betafish", "Bettong", "Bichonfrise", "Bighorn", "Bighornsheep", "Bigmouthbass", "Bilby", "Billygoat", "Binturong", "Bird", "Bison", "Bittern", "Blackbear", "Blackbird", "Blackbuck", "Blackcrappie", "Blackfish", "Blackfly", "Blacklab", "Blacklemur", "Blackmamba", "Blackpanther", "Blackrhino", "Blesbok", "Blobfish", "Blowfish", "Bluebird", "Bluebottle", "Bluefintuna", "Bluefish", "Bluegill", "Bluejay", "Blueshark", "Bluet", "Bluewhale", "Boa", "Boar", "Bobcat", "Bobolink", "Bobwhite", "Boilweevil", "Bongo", "Bonobo", "Booby", "Bordercollie", "Borderterrier", "Borer", "Borzoi", "Boto", "Boubou", "Boutu", "Bovine", "Brahmanbull", "Brahmancow", "Brant", "Bream", "Brocketdeer", "Bronco", "Brontosaurus", "Brownbear", "Bubblefish", "Buck", "Budgie", "Bufeo", "Buffalo", "Bufflehead", "Bug", "Bull", "Bullfrog", "Bullmastiff", "Bumblebee", "Bunny", "Bunting", "Burro", "Bushbaby", "Bushsqueaker", "Bustard", "Butterfly", "Buzzard", "Caecilian", "Caiman", "Caimanlizard", "Calf", "Camel", "Canadagoose", "Canary", "Canine", "Canvasback", "Capeghostfrog", "Capybara", "Caracal", "Cardinal", "Caribou", "Carp", "Carpenterant", "Cassowary", "Cat", "Catbird", "Caterpillar", "Catfish", "Cats", "Cattle", "Caudata", "Cavy", "Centipede", "Cero", "Chafer", "Chameleon", "Chamois", "Chanticleer", "Cheetah", "Chevrotain", "Chick", "Chickadee", "Chicken", "Chihuahua", "Chimneyswift", "Chimpanzee", "Chinchilla", "Chipmunk", "Chital", "Chrysalis", "Chrysomelid", "Chuckwalla", "Chupacabra", "Cicada", "Cirriped", "Civet", "Clam", "Cleanerwrasse", "Clingfish", "Clumber", "Coati", "Cob", "Cobra", "Cock", "Cockatiel", "Cockatoo", "Cockerspaniel", "Cockroach", "Cod", "Coelacanth", "Collardlizard", "Collie", "Colt", "Comet", "Commongonolek", "Conch", "Condor", "Coney", "Conure", "Cony", "Coot", "Cooter", "Copepod", "Copperhead", "Coqui", "Coral", "Cormorant", "Cornsnake", "Cottonmouth", "Cottontail", "Cougar", "Cow", "Cowbird", "Cowrie", "Coyote", "Coypu", "Crab", "Crane", "Cranefly", "Crayfish", "Creature", "Cricket", "Crocodile", "Crossbill", "Crow", "Crustacean", "Cub", "Cuckoo", "Cur", "Curassow", "Curlew", "Cuscus", "Cusimanse", "Cuttlefish", "Cutworm", "Cygnet", "Dachshund", "Daddylonglegs", "Dairycow", "Dalmatian", "Damselfly", "Dartfrog", "Darwinsfox", "Dassie", "Dassierat", "Davidstiger", "Deer", "Deermouse", "Degu", "Degus", "Deinonychus", "Desertpupfish", "Devilfish", "Dikdik", "Dikkops", "Dingo", "Dinosaur", "Diplodocus", "Dipper", "Discus", "Doctorfish", "Dodo", "Dodobird", "Doe", "Dog", "Dogfish", "Dolphin", "Donkey", "Dorado", "Dore", "Dorking", "Dormouse", "Dotterel", "Dove", "Dowitcher", "Drafthorse", "Dragon", "Dragonfly", "Drake", "Drever", "Dromaeosaur", "Dromedary", "Drongo", "Duck", "Duckbillcat", "Duckling", "Dugong", "Duiker", "Dungbeetle", "Dungenesscrab", "Dunlin", "Dunnart", "Dwarfmongoose", "Dwarfrabbit", "Eagle", "Earthworm", "Earwig", "Easternnewt", "Echidna", "Eel", "Eelelephant", "Eeve", "Eft", "Egg", "Egret", "Eider", "Eidolonhelvum", "Ekaltadeta", "Eland", "Electriceel", "Elephant", "Elephantseal", "Elk", "Elkhound", "Elver", "Emperorshrimp", "Emu", "Englishsetter", "Equestrian", "Equine", "Erin", "Ermine", "Erne", "Eskimodog", "Esok", "Ethiopianwolf", "Ewe", "Eyas", "Eyra", "Fairybluebird", "Fairyfly", "Falcon", "Fallowdeer", "Fantail", "Fanworms", "Fawn", "Feline", "Fennecfox", "Ferret", "Fiddlercrab", "Fieldmouse", "Fieldspaniel", "Finch", "Finnishspitz", "Finwhale", "Fireant", "Firecrest", "Firefly", "Fish", "Fishingcat", "Flamingo", "Flatfish", "Flea", "Flee", "Flicker", "Flies", "Flounder", "Fluke", "Fly", "Flycatcher", "Flyingfish", "Flyingfox", "Flyinglemur", "Foal", "Fossa", "Fowl", "Fox", "Foxhound", "Foxterrier", "Frenchbulldog", "Freshwatereel", "Frigatebird", "Frilledlizard", "Frog", "Frogmouth", "Fruitbat", "Fruitfly", "Fugu", "Fulmar", "Furseal", "Gadwall", "Galago", "Galah", "Galapagosdove", "Galapagoshawk", "Gallinule", "Gallowaycow", "Gander", "Gangesdolphin", "Gannet", "Gar", "Gardensnake", "Garpike", "Gartersnake", "Gaur", "Gavial", "Gazelle", "Gecko", "Geese", "Gelada", "Gelding", "Gemsbok", "Gemsbuck", "Genet", "Gentoopenguin", "Gerbil", "Gerenuk", "Germanspaniel", "Germanspitz", "Gharial", "Ghostshrimp", "Gibbon", "Gilamonster", "Giraffe", "Glassfrog", "Globefish", "Glowworm", "Gnat", "Gnatcatcher", "Gnu", "Goa", "Goat", "Godwit", "Goitered", "Goldeneye", "Goldfinch", "Goldfish", "Gonolek", "Goose", "Goosefish", "Gopher", "Goral", "Gordonsetter", "Gorilla", "Goshawk", "Gosling", "Gourami", "Grackle", "Grasshopper", "Grassspider", "Grayfox", "Grayling", "Grayreefshark", "Graysquirrel", "Graywolf", "Greatargus", "Greatdane", "Grebe", "Greyhounddog", "Grison", "Grizzlybear", "Grosbeak", "Groundbeetle", "Groundhog", "Grouper", "Grouse", "Grub", "Grunion", "Guanaco", "Guernseycow", "Guillemot", "Guineafowl", "Guineapig", "Gull", "Guppy", "Gypsymoth", "Gyrfalcon", "Hackee", "Haddock", "Hadrosaurus", "Hagfish", "Hairstreak", "Hake", "Halcyon", "Halibut", "Halicore", "Hamadryad", "Hamadryas", "Hammerkop", "Hamster", "Hanumanmonkey", "Hapuka", "Hapuku", "Harborseal", "Hare", "Harlequinbug", "Harpseal", "Harpyeagle", "Harrier", "Harrierhawk", "Hart", "Hartebeest", "Harvestmen", "Harvestmouse", "Hatchetfish", "Hawk", "Hedgehog", "Heifer", "Hellbender", "Hen", "Herald", "Hermitcrab", "Heron", "Herring", "Hind", "Hippopotamus", "Hoatzin", "Hog", "Hogget", "Hoiho", "Hoki", "Homalocephale", "Honeybadger", "Honeybee", "Honeycreeper", "Honeyeater", "Hookersealion", "Hoopoe", "Hornbill", "Hornedtoad", "Hornedviper", "Hornet", "Hornshark", "Horse", "Horsefly", "Horsemouse", "Horseshoebat", "Horseshoecrab", "Hound", "Housefly", "Hoverfly", "Howlermonkey", "Huemul", "Huia", "Human", "Hummingbird", "Humpbackwhale", "Husky", "Hydra", "Hyena", "Hylaeosaurus", "Hypacrosaurus", "Hypsilophodon", "Hyracotherium", "Hyrax", "Ibadanmalimbe", "Iberianbarbel", "Iberianlynx", "Iberianmole", "Iberiannase", "Ibex", "Ibis", "Ibisbill", "Ibizanhound", "Icefish", "Icelandgull", "Ichidna", "Ichneumonfly", "Ichthyosaurs", "Ichthyostega", "Iggypops", "Iguana", "Iguanodon", "Illadopsis", "Ilsamochadegu", "Imago", "Impala", "Imperialeagle", "Incatern", "Inchworm", "Indianabat", "Indiancow", "Indianhare", "Indianjackal", "Indianskimmer", "Indigobunting", "Indri", "Inganue", "Insect", "Invisiblerail", "Iriomotecat", "Irishsetter", "Irishterrier", "Islandcanary", "Isopod", "Ivorygull", "Izuthrush", "Jabiru", "Jackal", "Jackrabbit", "Jaeger", "Jaguar", "Jaguarundi", "Janenschia", "Javalina", "Jay", "Jellyfish", "Jenny", "Jerboa", "Joey", "Johndory", "Jumpingbean", "Junco", "Junebug", "Kagu", "Kakapo", "Kakarikis", "Kangaroo", "Karakul", "Katydid", "Kawala", "Kentrosaurus", "Kestrel", "Kid", "Killdeer", "Killerwhale", "Killifish", "Kingbird", "Kingfisher", "Kinglet", "Kingsnake", "Kinkajou", "Kiskadee", "Kissingbug", "Kite", "Kitfox", "Kitten", "Kittiwake", "Kitty", "Kiwi", "Koala", "Koalabear", "Kob", "Kodiakbear", "Koi", "Komododragon", "Koodoo", "Kookaburra", "Kouprey", "Krill", "Kronosaurus", "Kudu", "Kusimanse", "Lacewing", "Ladybird", "Ladybug", "Lamb", "Lamprey", "Langur", "Lark", "Larva", "Lcont", "Leafbird", "Leafcutterant", "Leafhopper", "Leafwing", "Leech", "Lemming", "Lemur", "Leonberger", "Leopard", "Leopardseal", "Leveret", "Lhasaapso", "Lice", "Liger", "Lightningbug", "Limpet", "Limpkin", "Ling", "Lion", "Lionfish", "Lizard", "Llama", "Lobo", "Lobster", "Locust", "Longhorn", "Longspur", "Loon", "Lorikeet", "Loris", "Louse", "Lovebird", "Lowchen", "Lunamoth", "Lungfish", "Lynx", "Macaque", "Macaw", "Macropod", "Maggot", "Magpie", "Maiasaura", "Majungatholus", "Malamute", "Mallard", "Maltesedog", "Mamba", "Mammal", "Mammoth", "Manatee", "Mandrill", "Mangabey", "Manta", "Mantaray", "Mantid", "Mantis", "Mantisray", "Manxcat", "Mara", "Marabou", "Mare", "Marlin", "Marmoset", "Marmot", "Marten", "Martin", "Massasauga", "Mastiff", "Mastodon", "Mayfly", "Meadowhawk", "Meadowlark", "Mealworm", "Meerkat", "Megalosaurus", "Megaraptor", "Merganser", "Merlin", "Metamorphosis", "Mice", "Microvenator", "Midge", "Milksnake", "Milkweedbug", "Millipede", "Minibeast", "Mink", "Minnow", "Mite", "Moa", "Mockingbird", "Mole", "Mollies", "Mollusk", "Molly", "Monarch", "Mongoose", "Mongrel", "Monkey", "Monkfish", "Monoclonius", "Moorhen", "Moose", "Moray", "Morayeel", "Morpho", "Mosasaur", "Mosquito", "Moth", "Motmot", "Mouflon", "Mountaincat", "Mountainlion", "Mouse", "Mouse/mice", "Mousebird", "Mudpuppy", "Mule", "Mullet", "Muntjac", "Murrelet", "Muskox", "Muskrat", "Mussaurus", "Mussel", "Mustang", "Mutt", "Myna", "Mynah", "Myotis", "Nabarlek", "Nag", "Naga", "Nagapies", "Nakedmolerat", "Nandine", "Nandoo", "Nandu", "Narwhal", "Narwhale", "Nauplius", "Nautilus", "Needlefish", "Needletail", "Nematode", "Nene", "Neonblueguppy", "Neonredguppy", "Neontetra", "Nerka", "Nettlefish", "Newt", "Newtnutria", "Nightcrawler", "Nighthawk", "Nightheron", "Nightingale", "Nightjar", "Nilgai", "Noctilio", "Noctule", "Noddy", "Noolbenger", "Northernpike", "Norwaylobster", "Norwayrat", "Nubiangoat", "Nudibranch", "Numbat", "Nurseshark", "Nutcracker", "Nuthatch", "Nutria", "Nyala", "Nymph", "Ocelot", "Octopus", "Okapi", "Olingo", "Olm", "Opossum", "Orangutan", "Orca", "Oriole", "Oropendola", "Oropendula", "Oryx", "Osprey", "Ostracod", "Ostrich", "Otter", "Ovenbird", "Owl", "Owlbutterfly", "Ox", "Oxen", "Oxpecker", "Oyster", "Paca", "Pachyderm", "Paddlefish", "Panda", "Pangolin", "Panther", "Paperwasp", "Papillon", "Parakeet", "Parrot", "Partridge", "Peacock", "Peafowl", "Peccary", "Pekingese", "Pelican", "Penguin", "Perch", "Pewee", "Phalarope", "Pharaohhound", "Pheasant", "Phoebe", "Phoenix", "Pig", "Pigeon", "Piglet", "Pika", "Pike", "Pikeperch", "Pilchard", "Pinemarten", "Pinniped", "Pintail", "Pipistrelle", "Pipit", "Piranha", "Pitbull", "Pittabird", "Plainsqueaker", "Plankton", "Planthopper", "Platypus", "Plover", "Polarbear", "Polecat", "Polliwog", "Polyp", "Polyturator", "Pomeranian", "Pondskater", "Pony", "Pooch", "Poodle", "Porcupine", "Porpoise", "Possum", "Prairiedog", "Prawn", "Prayingmantid", "Prayingmantis", "Primate", "Pronghorn", "Ptarmigan", "Pterodactyls", "Pterosaurs", "Puffer", "Pufferfish", "Puffin", "Pug", "Pullet", "Puma", "Pupa", "Pupfish", "Puppy", "Purplemarten", "Pussycat", "Pygmy", "Python", "Quadrisectus", "Quagga", "Quahog", "Quail", "Queenant", "Queenbee", "Queenconch", "Queensnake", "Quelea", "Quetzal", "Quillback", "Quokka", "Quoll", "Rabbit", "Rabidsquirrel", "Raccoon", "Racer", "Racerunner", "Ragfish", "Rail", "Rainbowfish", "Rainbowtrout", "Ram", "Raptors", "Rasbora", "Rat", "Ratfish", "Rattail", "Rattlesnake", "Raven", "Ray", "Redhead", "Redpanda", "Redpoll", "Redstart", "Redtailedhawk", "Reindeer", "Reptile", "Reynard", "Rhea", "Rhesusmonkey", "Rhino", "Rhinoceros", "Ringworm", "Roach", "Roadrunner", "Roan", "Robberfly", "Robin", "Rockrat", "Rodent", "Roebuck", "Roller", "Rook", "Rooster", "Rottweiler", "Sable", "Sableantelope", "Sablefish", "Saiga", "Sakimonkey", "Salamander", "Salmon", "Sambar", "Samoyeddog", "Sandbarshark", "Sanddollar", "Sanderling", "Sandpiper", "Sapsucker", "Sardine", "Sawfish", "Scallop", "Scarab", "Scarletibis", "Scaup", "Schapendoes", "Schipperke", "Schnauzer", "Scorpion", "Scoter", "Screamer", "Seabird", "Seagull", "Seahog", "Seahorse", "Seal", "Sealion", "Seamonkey", "Seaslug", "Seaurchin", "Senegalpython", "Seriema", "Serpent", "Serval", "Shark", "Shearwater", "Sheep", "Sheldrake", "Shelduck", "Shibainu", "Shihtzu", "Shorebird", "Shoveler", "Shrew", "Shrike", "Shrimp", "Siamang", "Siamesecat", "Siberiantiger", "Sidewinder", "Sifaka", "Silkworm", "Silverfish", "Silverfox", "Siskin", "Skimmer", "Skink", "Skipper", "Skua", "Skunk", "Skylark", "Sloth", "Slothbear", "Slug", "Smelts", "Smew", "Snail", "Snake", "Snipe", "Snowdog", "Snowgeese", "Snowleopard", "Snowmonkey", "Snowyowl", "Sockeyesalmon", "Solenodon", "Solitaire", "Songbird", "Sora", "Sow", "Spadefoot", "Sparrow", "Sphinx", "Spider", "Spidermonkey", "Spiketail", "Spittlebug", "Sponge", "Spoonbill", "Spreadwing", "Springbok", "Springpeeper", "Springtail", "Squab", "Squamata", "Squeaker", "Squid", "Squirrel", "Stag", "Stagbeetle", "Stallion", "Starfish", "Starling", "Steed", "Steer", "Stegosaurus", "Stickinsect", "Stickleback", "Stilt", "Stingray", "Stinkbug", "Stinkpot", "Stoat", "Stonefly", "Stork", "Stud", "Sturgeon", "Sugarglider", "Sunbear", "Sunbittern", "Sunfish", "Swallow", "Swallowtail", "Swan", "Swellfish", "Swift", "Swordfish", "Tadpole", "Tahr", "Takin", "Tamarin", "Tanager", "Tapaculo", "Tapeworm", "Tapir", "Tarantula", "Tarpan", "Tarsier", "Taruca", "Tattler", "Tayra", "Teal", "Tegus", "Teledu", "Tench", "Tenrec", "Termite", "Tern", "Terrapin", "Terrier", "Thoroughbred", "Thrasher", "Thrip", "Thrush", "Thunderbird", "Thylacine", "Tick", "Tiger", "Tigerbeetle", "Tigermoth", "Tigershark", "Tilefish", "Tinamou", "Titi", "Titmouse", "Toad", "Toadfish", "Tomtit", "Topi", "Tortoise", "Toucan", "Towhee", "Tragopan", "Treecreeper", "Trex", "Triceratops", "Trogon", "Trout", "Trumpeterbird", "Trumpeterswan", "Tsetsefly", "Tuatara", "Tuna", "Turaco", "Turkey", "Turnstone", "Turtle", "Turtledove", "Uakari", "Ugandakob", "Umbrellabird", "Umbrette", "Unau", "Ungulate", "Unicorn", "Upupa", "Urchin", "Urial", "Urson", "Urubu", "Urus", "Urutu", "Urva", "Vampirebat", "Vaquita", "Veery", "Velociraptor", "Velvetcrab", "Velvetworm", "Venomoussnake", "Verdin", "Vervet", "Vicuna", "Viper", "Viperfish", "Vipersquid", "Vireo", "Vixen", "Vole", "Volvox", "Vulpesvelox", "Vulpesvulpes", "Vulture", "Walkingstick", "Wallaby", "Wallaroo", "Walleye", "Walrus", "Warbler", "Warthog", "Wasp", "Waterboatman", "Waterbuck", "Waterbuffalo", "Waterbug", "Waterdogs", "Waterdragons", "Watermoccasin", "Waterstrider", "Waterthrush", "Wattlebird", "Watussi", "Waxwing", "Weasel", "Weaverbird", "Weevil", "Whale", "Whapuku", "Whelp", "Whimbrel", "Whippet", "Whippoorwill", "Whiteeye", "Whitepelican", "Whiterhino", "Whooper", "Whoopingcrane", "Widgeon", "Widowspider", "Wildcat", "Wildebeast", "Wildebeest", "Willet", "Wireworm", "Wisent", "Wolf", "Wolfspider", "Wolverine", "Wombat", "Woodborer", "Woodchuck", "Woodcock", "Woodpecker", "Woodstorks", "Worm", "Wrasse", "Wreckfish", "Wren", "Wrenchbird", "Wryneck", "Wuerhosaurus", "Wyvern", "Xanclomys", "Xanthareel", "Xantus", "Xeme", "Xenarthra", "Xenoposeidon", "Xenops", "Xenopterygii", "Xenopus", "Xenurine", "Xerus", "Xiaosaurus", "Xiphias", "Xiphosuran", "Xoni", "Xrayfish", "Xraytetra", "Xuanhuasaurus", "Yaffle", "Yak", "Yapok", "Yardant", "Yearling", "Yellowhammer", "Yellowjacket", "Yellowlegs", "Yellowthroat", "Yeti", "Ynambu", "Yosemitetoad", "Yucker", "Zander", "Zebra", "Zebradove", "Zebrafinch", "Zebrafish", "Zebu", "Zenaida", "Zeren", "Zethuswasp", "Zooplankton", "Zopilote", "Zorilla"};
        ANIMALS = objectArray;
    }
}

