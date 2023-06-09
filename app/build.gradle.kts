@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android.gradle.plugin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.safe.args)
}

android {
    namespace = libs.versions.applicationId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()

        val major = libs.versions.major.get().toInt()
        val minor = libs.versions.minor.get().toInt()
        val buildNumber = libs.versions.buildNumber.get().toInt()
        versionName = "$major.$minor($buildNumber)"

        testInstrumentationRunner = libs.versions.testRunner.get()

        buildConfigField("String", "BASE_URL", "\"https://test.kode-t.ru/\"")
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kapt {
    correctErrorTypes = true

    javacOptions {
        // Increase the max count of errors from annotation processors.
        // Default is 100.
        option("-Xmaxerrs", 500)
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))

    // test libs
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)

    // android libs
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // serialization
    implementation(libs.kotlin.serilization.json)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // room
    implementation(libs.room.runtime)

    // retrofit
    implementation(libs.retrofit)

    // coroutines
    implementation(libs.coroutines.android)

    // better link movement method
    implementation(libs.better.link.movement.method)

    //phone number
    implementation(libs.phone.number)

    // model watcher
    implementation(libs.mvi.core.diff)

    // swipe to refresh
    implementation(libs.swipe.to.refresh)

    // coil
    implementation(libs.coil)
}