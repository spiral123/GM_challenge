buildscript {
    repositories {
        google()
        mavenCentral()
    }

    val kotlinVersion: String by project

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-beta06")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

repositories {
    google()
    mavenCentral()
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}