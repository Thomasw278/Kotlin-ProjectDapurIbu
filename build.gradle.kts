// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Alternatif classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha' karena versi terbaru
    alias(libs.plugins.hilt.android) apply false

}