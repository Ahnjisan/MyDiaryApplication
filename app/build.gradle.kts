plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.gms.google-services")}

android {
    namespace = "com.example.mydiaryapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mydiaryapplication"
        minSdk = 33
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mydiaryapplication"
        minSdk = 33
        targetSdk = 33 }



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
    buildFeatures {
        viewBinding = true
    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation ("com.github.bumptech.glide:glide:5.0.0-rc01")
    kapt ("com.github.bumptech.glide:compiler:5.0.0-rc01")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
}}}
