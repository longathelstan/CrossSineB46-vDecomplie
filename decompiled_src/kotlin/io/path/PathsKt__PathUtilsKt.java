/*
 * Decompiled with CFR 0.152.
 */
package kotlin.io.path;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.io.CloseableKt;
import kotlin.io.path.ExperimentalPathApi;
import kotlin.io.path.PathRelativizer;
import kotlin.io.path.PathsKt;
import kotlin.io.path.PathsKt__PathReadWriteKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=5, xi=49, d1={"\u0000\u00b2\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0001H\u0087\b\u001a*\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00012\u0012\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001a\"\u00020\u0001H\u0087\b\u00a2\u0006\u0002\u0010\u001b\u001a?\u0010\u001c\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007\u00a2\u0006\u0002\u0010!\u001a6\u0010\u001c\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010\"\u001aK\u0010#\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007\u00a2\u0006\u0002\u0010%\u001aB\u0010#\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010&\u001a\u001c\u0010'\u001a\u00020(2\u0006\u0010\u0017\u001a\u00020\u00022\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0001\u001a\r\u0010+\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010,\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a.\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\u0087\b\u00a2\u0006\u0002\u00101\u001a\u001f\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\u0087\b\u001a.\u00104\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u00105\u001a.\u00106\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u00105\u001a.\u00107\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u00105\u001a\u0015\u00108\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u0002H\u0087\b\u001a6\u00109\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0087\b\u00a2\u0006\u0002\u0010:\u001a\r\u0010;\u001a\u00020<*\u00020\u0002H\u0087\b\u001a\r\u0010=\u001a\u000203*\u00020\u0002H\u0087\b\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\u0087\n\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0001H\u0087\n\u001a&\u0010@\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010B\u001a2\u0010C\u001a\u0002HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010F\u001a4\u0010G\u001a\u0004\u0018\u0001HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010F\u001a\r\u0010H\u001a\u00020I*\u00020\u0002H\u0087\b\u001a\r\u0010J\u001a\u00020K*\u00020\u0002H\u0087\b\u001a.\u0010L\u001a\u00020<*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020<0OH\u0087\b\u00f8\u0001\u0000\u001a0\u0010P\u001a\u0004\u0018\u00010Q*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010S\u001a&\u0010T\u001a\u00020U*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010V\u001a(\u0010W\u001a\u0004\u0018\u00010X*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010Y\u001a,\u0010Z\u001a\b\u0012\u0004\u0012\u00020\\0[*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010]\u001a&\u0010^\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010B\u001a\r\u0010_\u001a\u000203*\u00020\u0002H\u0087\b\u001a\r\u0010`\u001a\u000203*\u00020\u0002H\u0087\b\u001a\r\u0010a\u001a\u000203*\u00020\u0002H\u0087\b\u001a&\u0010b\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010B\u001a\u0015\u0010c\u001a\u000203*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\u0087\b\u001a\r\u0010d\u001a\u000203*\u00020\u0002H\u0087\b\u001a\r\u0010e\u001a\u000203*\u00020\u0002H\u0087\b\u001a\u001c\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00020g*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u0001H\u0007\u001a.\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\u0087\b\u00a2\u0006\u0002\u00101\u001a\u001f\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\u0087\b\u001a&\u0010i\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010B\u001a2\u0010j\u001a\u0002Hk\"\n\b\u0000\u0010k\u0018\u0001*\u00020l*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010m\u001a<\u0010j\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010Q0n*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010o\u001a\r\u0010p\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0014\u0010q\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0016\u0010r\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0014\u0010s\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a8\u0010t\u001a\u00020\u0002*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\b\u0010u\u001a\u0004\u0018\u00010Q2\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\u0087\b\u00a2\u0006\u0002\u0010v\u001a\u0015\u0010w\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020UH\u0087\b\u001a\u0015\u0010x\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020XH\u0087\b\u001a\u001b\u0010y\u001a\u00020\u0002*\u00020\u00022\f\u0010u\u001a\b\u0012\u0004\u0012\u00020\\0[H\u0087\b\u001a\r\u0010z\u001a\u00020\u0002*\u00020{H\u0087\b\u001a@\u0010|\u001a\u0002H}\"\u0004\b\u0000\u0010}*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0018\u0010~\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u007f\u0012\u0004\u0012\u0002H}0OH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0003\u0010\u0080\u0001\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\u0007\u001a\u00020\u0001*\u00020\u00028\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"\u001e\u0010\n\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\"\u001e\u0010\r\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006\"\u001e\u0010\u0010\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0006\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00028\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u0081\u0001"}, d2={"extension", "", "Ljava/nio/file/Path;", "getExtension$annotations", "(Ljava/nio/file/Path;)V", "getExtension", "(Ljava/nio/file/Path;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath$annotations", "getInvariantSeparatorsPath", "invariantSeparatorsPathString", "getInvariantSeparatorsPathString$annotations", "getInvariantSeparatorsPathString", "name", "getName$annotations", "getName", "nameWithoutExtension", "getNameWithoutExtension$annotations", "getNameWithoutExtension", "pathString", "getPathString$annotations", "getPathString", "Path", "path", "base", "subpaths", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", "createTempDirectory", "directory", "prefix", "attributes", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createTempFile", "suffix", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "fileAttributeViewNotAvailable", "", "attributeViewClass", "Ljava/lang/Class;", "absolute", "absolutePathString", "copyTo", "target", "options", "Ljava/nio/file/CopyOption;", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", "overwrite", "", "createDirectories", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createDirectory", "createFile", "createLinkPointingTo", "createSymbolicLinkPointingTo", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteExisting", "", "deleteIfExists", "div", "other", "exists", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "fileAttributesView", "V", "Ljava/nio/file/attribute/FileAttributeView;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileAttributeView;", "fileAttributesViewOrNull", "fileSize", "", "fileStore", "Ljava/nio/file/FileStore;", "forEachDirectoryEntry", "glob", "action", "Lkotlin/Function1;", "getAttribute", "", "attribute", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "getLastModifiedTime", "Ljava/nio/file/attribute/FileTime;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileTime;", "getOwner", "Ljava/nio/file/attribute/UserPrincipal;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/UserPrincipal;", "getPosixFilePermissions", "", "Ljava/nio/file/attribute/PosixFilePermission;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/util/Set;", "isDirectory", "isExecutable", "isHidden", "isReadable", "isRegularFile", "isSameFileAs", "isSymbolicLink", "isWritable", "listDirectoryEntries", "", "moveTo", "notExists", "readAttributes", "A", "Ljava/nio/file/attribute/BasicFileAttributes;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", "", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/util/Map;", "readSymbolicLink", "relativeTo", "relativeToOrNull", "relativeToOrSelf", "setAttribute", "value", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/Path;", "setLastModifiedTime", "setOwner", "setPosixFilePermissions", "toPath", "Ljava/net/URI;", "useDirectoryEntries", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/nio/file/Path;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, xs="kotlin/io/path/PathsKt")
class PathsKt__PathUtilsKt
extends PathsKt__PathReadWriteKt {
    @NotNull
    public static final String getName(@NotNull Path $this$name) {
        Intrinsics.checkNotNullParameter($this$name, "<this>");
        Path path = $this$name.getFileName();
        String string = path == null ? null : ((Object)path).toString();
        return string == null ? "" : string;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static /* synthetic */ void getName$annotations(Path path) {
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull Path $this$nameWithoutExtension) {
        String string;
        Intrinsics.checkNotNullParameter($this$nameWithoutExtension, "<this>");
        Path path = $this$nameWithoutExtension.getFileName();
        if (path == null) {
            string = "";
        } else {
            String string2 = ((Object)path).toString();
            string = StringsKt.substringBeforeLast$default(string2, ".", null, 2, null);
        }
        return string;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static /* synthetic */ void getNameWithoutExtension$annotations(Path path) {
    }

    @NotNull
    public static final String getExtension(@NotNull Path $this$extension) {
        String string;
        Intrinsics.checkNotNullParameter($this$extension, "<this>");
        Path path = $this$extension.getFileName();
        if (path == null) {
            string = "";
        } else {
            String string2 = ((Object)path).toString();
            string = StringsKt.substringAfterLast(string2, '.', "");
        }
        return string;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static /* synthetic */ void getExtension$annotations(Path path) {
    }

    private static final String getPathString(Path $this$pathString) {
        Intrinsics.checkNotNullParameter($this$pathString, "<this>");
        return ((Object)$this$pathString).toString();
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    public static /* synthetic */ void getPathString$annotations(Path path) {
    }

    @NotNull
    public static final String getInvariantSeparatorsPathString(@NotNull Path $this$invariantSeparatorsPathString) {
        String string;
        Intrinsics.checkNotNullParameter($this$invariantSeparatorsPathString, "<this>");
        String separator = $this$invariantSeparatorsPathString.getFileSystem().getSeparator();
        if (!Intrinsics.areEqual(separator, "/")) {
            String string2 = ((Object)$this$invariantSeparatorsPathString).toString();
            Intrinsics.checkNotNullExpressionValue(separator, "separator");
            string = StringsKt.replace$default(string2, separator, "/", false, 4, null);
        } else {
            string = ((Object)$this$invariantSeparatorsPathString).toString();
        }
        return string;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    public static /* synthetic */ void getInvariantSeparatorsPathString$annotations(Path path) {
    }

    private static final String getInvariantSeparatorsPath(Path $this$invariantSeparatorsPath) {
        Intrinsics.checkNotNullParameter($this$invariantSeparatorsPath, "<this>");
        return PathsKt.getInvariantSeparatorsPathString($this$invariantSeparatorsPath);
    }

    @Deprecated(message="Use invariantSeparatorsPathString property instead.", replaceWith=@ReplaceWith(expression="invariantSeparatorsPathString", imports={}), level=DeprecationLevel.ERROR)
    @SinceKotlin(version="1.4")
    @ExperimentalPathApi
    @InlineOnly
    public static /* synthetic */ void getInvariantSeparatorsPath$annotations(Path path) {
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path absolute(Path $this$absolute) {
        Intrinsics.checkNotNullParameter($this$absolute, "<this>");
        Path path = $this$absolute.toAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(path, "toAbsolutePath()");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final String absolutePathString(Path $this$absolutePathString) {
        Intrinsics.checkNotNullParameter($this$absolutePathString, "<this>");
        return ((Object)$this$absolutePathString.toAbsolutePath()).toString();
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path relativeTo(@NotNull Path $this$relativeTo, @NotNull Path base) {
        Path path;
        Intrinsics.checkNotNullParameter($this$relativeTo, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        try {
            path = PathRelativizer.INSTANCE.tryRelativeTo($this$relativeTo, base);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + "\nthis path: " + $this$relativeTo + "\nbase path: " + base, e);
        }
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path relativeToOrSelf(@NotNull Path $this$relativeToOrSelf, @NotNull Path base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrSelf, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        Path path = PathsKt.relativeToOrNull($this$relativeToOrSelf, base);
        return path == null ? $this$relativeToOrSelf : path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @Nullable
    public static final Path relativeToOrNull(@NotNull Path $this$relativeToOrNull, @NotNull Path base) {
        Path path;
        Intrinsics.checkNotNullParameter($this$relativeToOrNull, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        try {
            path = PathRelativizer.INSTANCE.tryRelativeTo($this$relativeToOrNull, base);
        }
        catch (IllegalArgumentException e) {
            path = null;
        }
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path copyTo(Path $this$copyTo, Path target, boolean overwrite) throws IOException {
        CopyOption[] copyOptionArray;
        Object object;
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        if (overwrite) {
            object = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray = object;
        } else {
            boolean $i$f$emptyArray = false;
            copyOptionArray = new CopyOption[]{};
        }
        CopyOption[] options = copyOptionArray;
        object = Files.copy($this$copyTo, target, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(object, "copy(this, target, *options)");
        return object;
    }

    static /* synthetic */ Path copyTo$default(Path $this$copyTo_u24default, Path target, boolean overwrite, int n, Object copyOptionArray) throws IOException {
        CopyOption[] copyOptionArray2;
        if ((n & 2) != 0) {
            overwrite = false;
        }
        Intrinsics.checkNotNullParameter($this$copyTo_u24default, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        if (overwrite) {
            copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray2 = copyOptionArray;
        } else {
            boolean $i$f$emptyArray = false;
            copyOptionArray2 = new CopyOption[]{};
        }
        CopyOption[] options = copyOptionArray2;
        Path path = Files.copy($this$copyTo_u24default, target, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(path, "copy(this, target, *options)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path copyTo(Path $this$copyTo, Path target, CopyOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(options, "options");
        Path path = Files.copy($this$copyTo, target, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(path, "copy(this, target, *options)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean exists(Path $this$exists, LinkOption ... options) {
        Intrinsics.checkNotNullParameter($this$exists, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.exists($this$exists, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean notExists(Path $this$notExists, LinkOption ... options) {
        Intrinsics.checkNotNullParameter($this$notExists, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.notExists($this$notExists, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isRegularFile(Path $this$isRegularFile, LinkOption ... options) {
        Intrinsics.checkNotNullParameter($this$isRegularFile, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.isRegularFile($this$isRegularFile, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isDirectory(Path $this$isDirectory, LinkOption ... options) {
        Intrinsics.checkNotNullParameter($this$isDirectory, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.isDirectory($this$isDirectory, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isSymbolicLink(Path $this$isSymbolicLink) {
        Intrinsics.checkNotNullParameter($this$isSymbolicLink, "<this>");
        return Files.isSymbolicLink($this$isSymbolicLink);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isExecutable(Path $this$isExecutable) {
        Intrinsics.checkNotNullParameter($this$isExecutable, "<this>");
        return Files.isExecutable($this$isExecutable);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isHidden(Path $this$isHidden) throws IOException {
        Intrinsics.checkNotNullParameter($this$isHidden, "<this>");
        return Files.isHidden($this$isHidden);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isReadable(Path $this$isReadable) {
        Intrinsics.checkNotNullParameter($this$isReadable, "<this>");
        return Files.isReadable($this$isReadable);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isWritable(Path $this$isWritable) {
        Intrinsics.checkNotNullParameter($this$isWritable, "<this>");
        return Files.isWritable($this$isWritable);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean isSameFileAs(Path $this$isSameFileAs, Path other) throws IOException {
        Intrinsics.checkNotNullParameter($this$isSameFileAs, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Files.isSameFile($this$isSameFileAs, other);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final List<Path> listDirectoryEntries(@NotNull Path $this$listDirectoryEntries, @NotNull String glob) throws IOException {
        List<Path> list;
        Intrinsics.checkNotNullParameter($this$listDirectoryEntries, "<this>");
        Intrinsics.checkNotNullParameter(glob, "glob");
        Closeable closeable = Files.newDirectoryStream($this$listDirectoryEntries, glob);
        Throwable throwable = null;
        try {
            DirectoryStream it = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            list = CollectionsKt.toList(it);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
        return list;
    }

    public static /* synthetic */ List listDirectoryEntries$default(Path path, String string, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            string = "*";
        }
        return PathsKt.listDirectoryEntries(path, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final <T> T useDirectoryEntries(Path $this$useDirectoryEntries, String glob, Function1<? super Sequence<? extends Path>, ? extends T> block) throws IOException {
        T t;
        Intrinsics.checkNotNullParameter($this$useDirectoryEntries, "<this>");
        Intrinsics.checkNotNullParameter(glob, "glob");
        Intrinsics.checkNotNullParameter(block, "block");
        Closeable closeable = Files.newDirectoryStream($this$useDirectoryEntries, glob);
        Throwable throwable = null;
        try {
            DirectoryStream it = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            t = block.invoke(CollectionsKt.asSequence(it));
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
        return t;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static /* synthetic */ Object useDirectoryEntries$default(Path $this$useDirectoryEntries_u24default, String glob, Function1 block, int n, Object object) throws IOException {
        Object r;
        if ((n & 1) != 0) {
            glob = "*";
        }
        Intrinsics.checkNotNullParameter($this$useDirectoryEntries_u24default, "<this>");
        Intrinsics.checkNotNullParameter(glob, "glob");
        Intrinsics.checkNotNullParameter(block, "block");
        Closeable closeable = Files.newDirectoryStream($this$useDirectoryEntries_u24default, glob);
        object = null;
        try {
            DirectoryStream it = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            r = block.invoke(CollectionsKt.asSequence(it));
        }
        catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        }
        finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, (Throwable)object);
            InlineMarker.finallyEnd(1);
        }
        return r;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void forEachDirectoryEntry(Path $this$forEachDirectoryEntry, String glob, Function1<? super Path, Unit> action) throws IOException {
        Intrinsics.checkNotNullParameter($this$forEachDirectoryEntry, "<this>");
        Intrinsics.checkNotNullParameter(glob, "glob");
        Intrinsics.checkNotNullParameter(action, "action");
        Closeable closeable = Files.newDirectoryStream($this$forEachDirectoryEntry, glob);
        Throwable throwable = null;
        try {
            DirectoryStream it = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            Iterable $this$forEach$iv = it;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                action.invoke((Path)element$iv);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, throwable);
            InlineMarker.finallyEnd(1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static /* synthetic */ void forEachDirectoryEntry$default(Path $this$forEachDirectoryEntry_u24default, String glob, Function1 action, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            glob = "*";
        }
        Intrinsics.checkNotNullParameter($this$forEachDirectoryEntry_u24default, "<this>");
        Intrinsics.checkNotNullParameter(glob, "glob");
        Intrinsics.checkNotNullParameter(action, "action");
        Closeable closeable = Files.newDirectoryStream($this$forEachDirectoryEntry_u24default, glob);
        object = null;
        try {
            DirectoryStream it = (DirectoryStream)closeable;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            Iterable $this$forEach$iv = it;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                action.invoke(element$iv);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable) {
            object = throwable;
            throw throwable;
        }
        finally {
            InlineMarker.finallyStart(1);
            CloseableKt.closeFinally(closeable, (Throwable)object);
            InlineMarker.finallyEnd(1);
        }
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final long fileSize(Path $this$fileSize) throws IOException {
        Intrinsics.checkNotNullParameter($this$fileSize, "<this>");
        return Files.size($this$fileSize);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final void deleteExisting(Path $this$deleteExisting) throws IOException {
        Intrinsics.checkNotNullParameter($this$deleteExisting, "<this>");
        Files.delete($this$deleteExisting);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final boolean deleteIfExists(Path $this$deleteIfExists) throws IOException {
        Intrinsics.checkNotNullParameter($this$deleteIfExists, "<this>");
        return Files.deleteIfExists($this$deleteIfExists);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createDirectory(Path $this$createDirectory, FileAttribute<?> ... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createDirectory, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createDirectory($this$createDirectory, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createDirectory(this, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createDirectories(Path $this$createDirectories, FileAttribute<?> ... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createDirectories, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createDirectories($this$createDirectories, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createDirectories(this, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path moveTo(Path $this$moveTo, Path target, CopyOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$moveTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(options, "options");
        Path path = Files.move($this$moveTo, target, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(path, "move(this, target, *options)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path moveTo(Path $this$moveTo, Path target, boolean overwrite) throws IOException {
        CopyOption[] copyOptionArray;
        Object object;
        Intrinsics.checkNotNullParameter($this$moveTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        if (overwrite) {
            object = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray = object;
        } else {
            boolean $i$f$emptyArray = false;
            copyOptionArray = new CopyOption[]{};
        }
        CopyOption[] options = copyOptionArray;
        object = Files.move($this$moveTo, target, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(object, "move(this, target, *options)");
        return object;
    }

    static /* synthetic */ Path moveTo$default(Path $this$moveTo_u24default, Path target, boolean overwrite, int n, Object copyOptionArray) throws IOException {
        CopyOption[] copyOptionArray2;
        if ((n & 2) != 0) {
            overwrite = false;
        }
        Intrinsics.checkNotNullParameter($this$moveTo_u24default, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        if (overwrite) {
            copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            copyOptionArray2 = copyOptionArray;
        } else {
            boolean $i$f$emptyArray = false;
            copyOptionArray2 = new CopyOption[]{};
        }
        CopyOption[] options = copyOptionArray2;
        Path path = Files.move($this$moveTo_u24default, target, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(path, "move(this, target, *options)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final FileStore fileStore(Path $this$fileStore) throws IOException {
        Intrinsics.checkNotNullParameter($this$fileStore, "<this>");
        FileStore fileStore = Files.getFileStore($this$fileStore);
        Intrinsics.checkNotNullExpressionValue(fileStore, "getFileStore(this)");
        return fileStore;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Object getAttribute(Path $this$getAttribute, String attribute, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getAttribute, "<this>");
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.getAttribute($this$getAttribute, attribute, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setAttribute(Path $this$setAttribute, String attribute, Object value, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$setAttribute, "<this>");
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        Intrinsics.checkNotNullParameter(options, "options");
        Path path = Files.setAttribute($this$setAttribute, attribute, value, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(path, "setAttribute(this, attribute, value, *options)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final /* synthetic */ <V extends FileAttributeView> V fileAttributesViewOrNull(Path $this$fileAttributesViewOrNull, LinkOption ... options) {
        Intrinsics.checkNotNullParameter($this$fileAttributesViewOrNull, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.reifiedOperationMarker(4, "V");
        return (V)Files.getFileAttributeView($this$fileAttributesViewOrNull, FileAttributeView.class, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final /* synthetic */ <V extends FileAttributeView> V fileAttributesView(Path $this$fileAttributesView, LinkOption ... options) {
        Intrinsics.checkNotNullParameter($this$fileAttributesView, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.reifiedOperationMarker(4, "V");
        FileAttributeView fileAttributeView = Files.getFileAttributeView($this$fileAttributesView, FileAttributeView.class, Arrays.copyOf(options, options.length));
        if (fileAttributeView == null) {
            Intrinsics.reifiedOperationMarker(4, "V");
            PathsKt.fileAttributeViewNotAvailable($this$fileAttributesView, FileAttributeView.class);
            throw new KotlinNothingValueException();
        }
        return (V)fileAttributeView;
    }

    @PublishedApi
    @NotNull
    public static final Void fileAttributeViewNotAvailable(@NotNull Path path, @NotNull Class<?> attributeViewClass) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(attributeViewClass, "attributeViewClass");
        throw new UnsupportedOperationException("The desired attribute view type " + attributeViewClass + " is not available for the file " + path + '.');
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final /* synthetic */ <A extends BasicFileAttributes> A readAttributes(Path $this$readAttributes, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$readAttributes, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.reifiedOperationMarker(4, "A");
        BasicFileAttributes basicFileAttributes = Files.readAttributes($this$readAttributes, BasicFileAttributes.class, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(basicFileAttributes, "readAttributes(this, A::class.java, *options)");
        return (A)basicFileAttributes;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Map<String, Object> readAttributes(Path $this$readAttributes, String attributes, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$readAttributes, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(options, "options");
        Map<String, Object> map = Files.readAttributes($this$readAttributes, attributes, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(map, "readAttributes(this, attributes, *options)");
        return map;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final FileTime getLastModifiedTime(Path $this$getLastModifiedTime, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getLastModifiedTime, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        FileTime fileTime = Files.getLastModifiedTime($this$getLastModifiedTime, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(fileTime, "getLastModifiedTime(this, *options)");
        return fileTime;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setLastModifiedTime(Path $this$setLastModifiedTime, FileTime value) throws IOException {
        Intrinsics.checkNotNullParameter($this$setLastModifiedTime, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Path path = Files.setLastModifiedTime($this$setLastModifiedTime, value);
        Intrinsics.checkNotNullExpressionValue(path, "setLastModifiedTime(this, value)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final UserPrincipal getOwner(Path $this$getOwner, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getOwner, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.getOwner($this$getOwner, Arrays.copyOf(options, options.length));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setOwner(Path $this$setOwner, UserPrincipal value) throws IOException {
        Intrinsics.checkNotNullParameter($this$setOwner, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Path path = Files.setOwner($this$setOwner, value);
        Intrinsics.checkNotNullExpressionValue(path, "setOwner(this, value)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Set<PosixFilePermission> getPosixFilePermissions(Path $this$getPosixFilePermissions, LinkOption ... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getPosixFilePermissions, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Set<PosixFilePermission> set = Files.getPosixFilePermissions($this$getPosixFilePermissions, Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(set, "getPosixFilePermissions(this, *options)");
        return set;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path setPosixFilePermissions(Path $this$setPosixFilePermissions, Set<? extends PosixFilePermission> value) throws IOException {
        Intrinsics.checkNotNullParameter($this$setPosixFilePermissions, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Path path = Files.setPosixFilePermissions($this$setPosixFilePermissions, value);
        Intrinsics.checkNotNullExpressionValue(path, "setPosixFilePermissions(this, value)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createLinkPointingTo(Path $this$createLinkPointingTo, Path target) throws IOException {
        Intrinsics.checkNotNullParameter($this$createLinkPointingTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Path path = Files.createLink($this$createLinkPointingTo, target);
        Intrinsics.checkNotNullExpressionValue(path, "createLink(this, target)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createSymbolicLinkPointingTo(Path $this$createSymbolicLinkPointingTo, Path target, FileAttribute<?> ... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createSymbolicLinkPointingTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createSymbolicLink($this$createSymbolicLinkPointingTo, target, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createSymbolicLink(this, target, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path readSymbolicLink(Path $this$readSymbolicLink) throws IOException {
        Intrinsics.checkNotNullParameter($this$readSymbolicLink, "<this>");
        Path path = Files.readSymbolicLink($this$readSymbolicLink);
        Intrinsics.checkNotNullExpressionValue(path, "readSymbolicLink(this)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createFile(Path $this$createFile, FileAttribute<?> ... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createFile, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createFile($this$createFile, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createFile(this, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createTempFile(String prefix, String suffix, FileAttribute<?> ... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createTempFile(prefix, suffix, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempFile(prefix, suffix, *attributes)");
        return path;
    }

    static /* synthetic */ Path createTempFile$default(String prefix, String suffix, FileAttribute[] attributes, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            prefix = null;
        }
        if ((n & 2) != 0) {
            suffix = null;
        }
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createTempFile(prefix, suffix, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempFile(prefix, suffix, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path createTempFile(@Nullable Path directory, @Nullable String prefix, @Nullable String suffix, FileAttribute<?> ... attributes) throws IOException {
        Path path;
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (directory != null) {
            Path path2 = Files.createTempFile(directory, prefix, suffix, Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(path2, "createTempFile(directory\u2026fix, suffix, *attributes)");
            path = path2;
        } else {
            Path path3 = Files.createTempFile(prefix, suffix, Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(path3, "createTempFile(prefix, suffix, *attributes)");
            path = path3;
        }
        return path;
    }

    public static /* synthetic */ Path createTempFile$default(Path path, String string, String string2, FileAttribute[] fileAttributeArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            string = null;
        }
        if ((n & 4) != 0) {
            string2 = null;
        }
        return PathsKt.createTempFile(path, string, string2, fileAttributeArray);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path createTempDirectory(String prefix, FileAttribute<?> ... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createTempDirectory(prefix, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempDirectory(prefix, *attributes)");
        return path;
    }

    static /* synthetic */ Path createTempDirectory$default(String prefix, FileAttribute[] attributes, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            prefix = null;
        }
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path path = Files.createTempDirectory(prefix, Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(path, "createTempDirectory(prefix, *attributes)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @NotNull
    public static final Path createTempDirectory(@Nullable Path directory, @Nullable String prefix, FileAttribute<?> ... attributes) throws IOException {
        Path path;
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (directory != null) {
            Path path2 = Files.createTempDirectory(directory, prefix, Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(path2, "createTempDirectory(dire\u2026ory, prefix, *attributes)");
            path = path2;
        } else {
            Path path3 = Files.createTempDirectory(prefix, Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(path3, "createTempDirectory(prefix, *attributes)");
            path = path3;
        }
        return path;
    }

    public static /* synthetic */ Path createTempDirectory$default(Path path, String string, FileAttribute[] fileAttributeArray, int n, Object object) throws IOException {
        if ((n & 2) != 0) {
            string = null;
        }
        return PathsKt.createTempDirectory(path, string, fileAttributeArray);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path div(Path $this$div, Path other) {
        Intrinsics.checkNotNullParameter($this$div, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Path path = $this$div.resolve(other);
        Intrinsics.checkNotNullExpressionValue(path, "this.resolve(other)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path div(Path $this$div, String other) {
        Intrinsics.checkNotNullParameter($this$div, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Path path = $this$div.resolve(other);
        Intrinsics.checkNotNullExpressionValue(path, "this.resolve(other)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path Path(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Path path2 = Paths.get(path, new String[0]);
        Intrinsics.checkNotNullExpressionValue(path2, "get(path)");
        return path2;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path Path(String base, String ... subpaths) {
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(subpaths, "subpaths");
        Path path = Paths.get(base, Arrays.copyOf(subpaths, subpaths.length));
        Intrinsics.checkNotNullExpressionValue(path, "get(base, *subpaths)");
        return path;
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalPathApi.class})
    @InlineOnly
    private static final Path toPath(URI $this$toPath) {
        Intrinsics.checkNotNullParameter($this$toPath, "<this>");
        Path path = Paths.get($this$toPath);
        Intrinsics.checkNotNullExpressionValue(path, "get(this)");
        return path;
    }
}

