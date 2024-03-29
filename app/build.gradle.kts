plugins {
    id("com.android.application")
}

android {
    namespace = "tn.esprit.sensors"
    compileSdk = 34

    defaultConfig {
        applicationId = "tn.esprit.sensors"
        minSdk = 26
        targetSdk = 34
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
}

dependencies {
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("com.lorentzos.swipecards:library:1.0.9")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.facebook.stetho:stetho:1.5.1")
    implementation("com.google.android.gms:play-services-maps:17.0.1")
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("org.osmdroid:osmdroid-android:6.1.18")
    implementation("androidx.fragment:fragment:1.5.5")
    implementation("androidx.annotation:annotation:1.3.0")
    implementation("androidx.sqlite:sqlite:2.3.0")
    implementation("androidx.core:core:1.9.0-alpha01")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}