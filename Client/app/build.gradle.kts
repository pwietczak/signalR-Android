import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file(".gradle/gradle.properties")
if (localPropertiesFile.exists()) {
	localProperties.load(FileInputStream(localPropertiesFile))
}

android {
	namespace = "eu.pw.notificationpusher"
	compileSdk {
		version = release(36)
	}

	defaultConfig {
		applicationId = "eu.pw.notificationpusher"
		minSdk = 25
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		debug {
			buildConfigField(
					"String",
					"SERVER_ADDERSS",
					"\"${localProperties.getProperty("SERVER_ADDERSS", "")}\"",
							)
		}

		release {
			isMinifyEnabled = false
			buildConfigField(
					"String",
					"SERVER_ADDERSS",
					"\"${localProperties.getProperty("SERVER_ADDERSS", "")}\"",
							)
		}
	}

	kotlin {
		compilerOptions {
			jvmTarget = JvmTarget.JVM_11
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	buildFeatures {
		buildConfig = true
		compose = true
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.androidx.compose.material3)
	implementation(libs.kotlinx.datetime)

	implementation(libs.micrsoft.signalr)
	implementation(libs.timber)
	implementation(libs.permissions)
	implementation(libs.eventbus)

	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	debugImplementation(libs.androidx.compose.ui.tooling)
	debugImplementation(libs.androidx.compose.ui.test.manifest)
}