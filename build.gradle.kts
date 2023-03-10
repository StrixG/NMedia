// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version libs.versions.android.plugin apply false
    id("com.android.library") version libs.versions.android.plugin apply false
    alias(libs.plugins.kotlin) apply false
    id("androidx.navigation.safeargs") version "2.5.3" apply false
}
