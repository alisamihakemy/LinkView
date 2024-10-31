plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`

}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "madar.soft.linkViewer"
            artifactId = "library"
            version = "2.0.0"
            artifact("$buildDir/outputs/aar/linkView-debug.aar")  // Adjust this path as needed
        }
    }
    repositories {
        maven {
            name ="MadarLikeResolver"
            url = uri("https://maven.pkg.github.com/alisamihakemy/LinkView")  // Replace with your repository URL
            credentials {

                username = "alisamihakemy"
                password = "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"
            }
        }
    }
}

android {
    namespace = "com.madar.linkview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

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
    implementation ("madar.soft.linkViewer:library:1.1.0")


}