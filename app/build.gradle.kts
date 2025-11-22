plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

        kotlin("kapt")


}

android {
    namespace = "com.mopo.sample"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mopo.sample"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


        val work_version = "2.11.0"

        // (Java only)
        implementation("androidx.work:work-runtime:$work_version")

        // Kotlin + coroutines
        implementation("androidx.work:work-runtime-ktx:$work_version")

        // optional - RxJava2 support
        implementation("androidx.work:work-rxjava2:$work_version")

        // optional - GCMNetworkManager support
        implementation("androidx.work:work-gcm:$work_version")

        // optional - Test helpers
        androidTestImplementation("androidx.work:work-testing:$work_version")

        // optional - Multiprocess support
        implementation("androidx.work:work-multiprocess:$work_version")
// Source - https://stackoverflow.com/a
// Posted by che10
// Retrieved 2025-11-22, License - CC BY-SA 4.0
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    kapt("androidx.room:room-compiler:2.8.4")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")


// coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")





}