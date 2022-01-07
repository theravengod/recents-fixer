val kotlinVersion: String by extra
plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "kitty.cheshire.recents.fixer"
        minSdk = 30
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/atomicfu.kotlin_module"
        }
    }
}

dependencies {
    val composeVersion: String by rootProject.extra
    val koinVersion: String by rootProject.extra

    // AndroidX
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Google
    implementation("com.google.android.material:material:1.6.0-alpha01")
    implementation("androidx.compose.material3:material3:1.0.0-alpha02")
    // Koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
    // Test stuff
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}