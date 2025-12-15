import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val apiKeyPropertiesFile = rootProject.file("apikey.properties")
val apiKeyProperties = Properties().apply {
    if (apiKeyPropertiesFile.exists()) {
        load(apiKeyPropertiesFile.inputStream())
    }
}

val mapsApiKey: String = apiKeyProperties.getProperty("MAPS_API_KEY") ?: ""

android {
    namespace = "com.example.project_g4_personalrestaurantguide"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.project_g4_personalrestaurantguide"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room components
    implementation("androidx.room:room-runtime:2.8.2")
    annotationProcessor("androidx.room:room-compiler:2.8.2")

    // Lifecycle components
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.4")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:2.9.4")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime:2.9.4")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.9.4")

    // Annotation processor
    implementation("androidx.lifecycle:lifecycle-common-java8:2.9.4")

    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location-license:12.0.1")

}