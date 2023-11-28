plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
<<<<<<< HEAD
    id("com.google.gms.google-services")
=======
<<<<<<< HEAD
<<<<<<< HEAD
=======
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
>>>>>>> jisan
=======
>>>>>>> 9ac5f302f074b64ebfc61793b3cde5547f45e238
>>>>>>> 6ff7811e54d4ec5949e800ef8172f44288306b08
}

android {
    namespace = "com.example.mydiaryapplication"
<<<<<<< HEAD
<<<<<<< HEAD
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.mydiaryapplication"
        minSdk = 24
=======
=======
>>>>>>> 9ac5f302f074b64ebfc61793b3cde5547f45e238
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mydiaryapplication"
<<<<<<< HEAD
        minSdk = 33
>>>>>>> jisan
        targetSdk = 33
=======
        minSdk = 21
        targetSdk = 34
>>>>>>> 9ac5f302f074b64ebfc61793b3cde5547f45e238
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
<<<<<<< HEAD
<<<<<<< HEAD
    buildFeatures {
        viewBinding = true
=======
    viewBinding{
        enable = true
>>>>>>> jisan
=======
    viewBinding {
        enable = true
>>>>>>> 9ac5f302f074b64ebfc61793b3cde5547f45e238
    }
}

dependencies {

<<<<<<< HEAD
<<<<<<< HEAD
=======
    implementation ("com.github.bumptech.glide:glide:5.0.0-rc01")
    kapt ("com.github.bumptech.glide:compiler:5.0.0-rc01")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics")
>>>>>>> jisan
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
<<<<<<< HEAD
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
=======
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.google.firebase:firebase-common-ktx:20.4.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
>>>>>>> jisan
=======
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
>>>>>>> 9ac5f302f074b64ebfc61793b3cde5547f45e238
