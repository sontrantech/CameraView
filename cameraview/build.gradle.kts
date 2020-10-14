import com.otaliastudios.tools.publisher.common.License
import com.otaliastudios.tools.publisher.common.Release

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.otaliastudios.tools.publisher")
    id("jacoco")
}

android {
    setCompileSdkVersion(property("compileSdkVersion") as Int)
    defaultConfig {
        setMinSdkVersion(property("minSdkVersion") as Int)
        setTargetSdkVersion(property("targetSdkVersion") as Int)
        versionCode = 1
        versionName = "2.6.4-noandroidx-rc1"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("filter", "" +
                "com.otaliastudios.cameraview.tools.SdkExcludeFilter," +
                "com.otaliastudios.cameraview.tools.SdkIncludeFilter")
    }
    buildTypes["debug"].isTestCoverageEnabled = true
    buildTypes["release"].isMinifyEnabled = false
}

dependencies {
    testImplementation("junit:junit:4.13")
    testImplementation("org.mockito:mockito-inline:2.28.2")

    androidTestImplementation ("com.android.support.test:runner:1.0.2")
    androidTestImplementation ("com.android.support.test:rules:1.0.2")
//    androidTestImplementation("com.android.support.test.ext:junit:1.0.2")
    androidTestImplementation("org.mockito:mockito-android:3.5.13")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    api("com.android.support:exifinterface:28.0.0")
    api("android.arch.lifecycle:common:1.1.1")
    api("com.google.android.gms:play-services-tasks:16.0.1") //the last version supports non-androidx projects
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("com.sontrantech.opengl:egloo:0.5.3-noandroidx-rc1")
}

// Publishing

publisher {
    project.description = "A well documented, high-level Android interface that makes capturing " +
            "pictures and videos easy, addressing all of the common issues and needs. " +
            "Real-time filters, gestures, watermarks, frame processing, RAW, output of any size."
    project.artifact = "cameraview"
    project.group = "com.sontrantech"
    project.url = "https://github.com/sontrantech/CameraView"
    project.addLicense(License.APACHE_2_0)
    release.setSources(Release.SOURCES_AUTO)
    release.setDocs(Release.DOCS_AUTO)
    bintray {
        auth.user = "BINTRAY_USER"
        auth.key = "BINTRAY_KEY"
        auth.repo = "BINTRAY_REPO"
    }
    directory {
        directory = "build/local"
    }
}

// Code Coverage
val buildDir = project.buildDir.absolutePath
val coverageInputDir = "$buildDir/coverage_input" // changing? change github workflow
val coverageOutputDir = "$buildDir/coverage_output" // changing? change github workflow

// Run unit tests, with coverage enabled in the android { } configuration.
// Output will be an .exec file in build/jacoco.
tasks.register("runUnitTests") { // changing name? change github workflow
    dependsOn("testDebugUnitTest")
    doLast {
        copy {
            from("$buildDir/jacoco/testDebugUnitTest.exec")
            into("$coverageInputDir/unit_tests") // changing? change github workflow
        }
    }
}

// Run android tests with coverage.
tasks.register("runAndroidTests") { // changing name? change github workflow
    dependsOn("connectedDebugAndroidTest")
    doLast {
        copy {
            from("$buildDir/outputs/code_coverage/debugAndroidTest/connected")
            include("*coverage.ec")
            into("$coverageInputDir/android_tests") // changing? change github workflow
        }
    }
}

// Merge the two with a jacoco task.
jacoco { toolVersion = "0.8.5" }
tasks.register("computeCoverage", JacocoReport::class) {
    dependsOn("compileDebugSources") // Compile sources, needed below
    executionData.from(fileTree(coverageInputDir))
    sourceDirectories.from(android.sourceSets["main"].java.sourceFiles)
    additionalSourceDirs.from("$buildDir/generated/source/buildConfig/debug")
    additionalSourceDirs.from("$buildDir/generated/source/r/debug")
    classDirectories.from(fileTree("$buildDir/intermediates/javac/debug") {
        // Not everything here is relevant for CameraView, but let's keep it generic
        exclude(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "android/**",
                "androidx/**",
                "com/google/**",
                "**/*\$ViewInjector*.*",
                "**/Dagger*Component.class",
                "**/Dagger*Component\$Builder.class",
                "**/*Module_*Factory.class",
                // We don"t test OpenGL filters.
                "**/com/otaliastudios/cameraview/filters/**.*"
        )
    })
    reports.html.isEnabled = true
    reports.xml.isEnabled = true
    reports.html.destination = file("$coverageOutputDir/html")
    reports.xml.destination = file("$coverageOutputDir/xml/report.xml")
}