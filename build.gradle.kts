// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
buildscript {
    extra.apply {
        set("composeVersion", "1.0.5")
        set("koinVersion", "3.1.4")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}