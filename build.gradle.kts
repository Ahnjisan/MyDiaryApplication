// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
<<<<<<< HEAD
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
=======
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.0" apply false
}
buildscript{
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
>>>>>>> jisan
}