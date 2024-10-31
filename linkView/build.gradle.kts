plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`

}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.madar.linkview"
            artifactId = "library"
            version = "1.0.0"
            artifact("$buildDir/outputs/aar/linkView-debug.aar")  // Adjust this path as needed
        }
    }
    repositories {
        maven {
            name ="MadarLikeResolver"
            url = uri("https://maven.pkg.github.com/alisamihakemy/LinkView")  // Replace with your repository URL
            credentials {

                username = "alisamihakemy"
                password = "ALI&yehia010"
            }
        }
    }
}

android {
    namespace = "com.madar.linkview"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.glide)
    implementation(libs.jsoup)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


}