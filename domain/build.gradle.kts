@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.blogspot.soyamr.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        testInstrumentationRunner = libs.versions.testRunner.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // android test libs
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    // hilt annotations
    implementation(libs.hilt.javax.inject)
    // coroutines
    implementation(libs.coroutines.core)
}