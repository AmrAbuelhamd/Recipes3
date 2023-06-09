@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.blogspot.soyamr.data"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        testInstrumentationRunner = libs.versions.testRunner.get()
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
    sourceSets {
        getByName("androidTest").assets.srcDirs(files("$projectDir/schemas"))
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(path = ":domain"))

    // test libs
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)

    // serialization
    implementation(libs.kotlin.serilization.json)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization.converter)
    implementation(libs.okHttp.logging.interceptor)

    // datastore
    implementation(libs.datastore.preferences)

    // hilt annotations
    implementation(libs.hilt.javax.inject)
}