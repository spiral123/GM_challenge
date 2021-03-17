plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.example.gmchallenge"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    android {
        android.buildFeatures.viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            versionNameSuffix = "-DBG"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val kotlinVersion: String by project
val coroutinesVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")

    // dagger
    implementation("com.google.dagger:dagger:2.28.3")
    kapt("com.google.dagger:dagger-compiler:2.25.2")
    implementation("com.google.dagger:dagger-android:2.25.2")
    kapt("com.google.dagger:dagger-android-processor:2.25.2")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("app.cash.turbine:turbine:0.4.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}