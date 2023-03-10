plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.obrekht.nmedia"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.obrekht.nmedia"
        minSdk = 23
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:${libs.versions.jetpack.core}")
    implementation("androidx.appcompat:appcompat:${libs.versions.appcompat}")
    implementation("com.google.android.material:material:${libs.versions.material}")
    implementation("androidx.activity:activity-ktx:${libs.versions.activity}")
    implementation("androidx.fragment:fragment-ktx:${libs.versions.fragment}")
    implementation("androidx.constraintlayout:constraintlayout:${libs.versions.constraintlayout}")
    implementation("androidx.recyclerview:recyclerview:${libs.versions.recyclerview}")
    implementation("androidx.navigation:navigation-fragment-ktx:${libs.versions.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${libs.versions.navigation}")
    implementation("com.google.code.gson:gson:${libs.versions.gson}")

    testImplementation("junit:junit:${libs.versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${libs.versions.junit.ext}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${libs.versions.espresso}")
}
