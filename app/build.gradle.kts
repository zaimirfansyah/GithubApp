plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.githubapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.githubapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"YOUR_PERSONAL_APIKEY\"")
        buildConfigField("String", "BASE_URL", "\"YOUR_BASE_URL\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    dependencies {

        implementation("androidx.viewpager2:viewpager2:1.0.0")
        implementation("androidx.datastore:datastore-preferences:1.0.0")
        implementation("androidx.activity:activity-ktx:1.8.2")
        implementation("androidx.fragment:fragment-ktx:1.6.2")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("de.hdodenhof:circleimageview:3.1.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("com.github.bumptech.glide:glide:4.16.0")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
        implementation("androidx.room:room-runtime:2.5.2")
        ksp("androidx.room:room-compiler:2.5.2")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
