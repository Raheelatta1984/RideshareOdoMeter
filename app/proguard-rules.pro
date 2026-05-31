# RideshareOdoMeter ProGuard Configuration

# Keep all public classes and methods (safe default for app)
-keep public class com.rideshare.odometer.** { *; }

# Keep Room entities
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keep @androidx.room.Database class * { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }
-keepclasseswithmembernames class androidx.compose.** { *; }

# Keep Kotlin metadata
-keepclasseswithmembernames class * {
    kotlin.Metadata *;
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
