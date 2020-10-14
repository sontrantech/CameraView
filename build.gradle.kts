
buildscript {

    extra["minSdkVersion"] = 15
    extra["compileSdkVersion"] = 29
    extra["targetSdkVersion"] = 29

    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:2.2.3")
        classpath("com.otaliastudios.tools:publisher:0.3.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0")

    }
}

//Ensure that all dependencies use the same version of the Android Support library
configurations.all {
    resolutionStrategy {
        eachDependency {
            when (requested.group) {
                "com.android.support" -> {
                    if (!requested.name.contains("multidex")) {
                        useVersion("28.0.0")
                    }
                }
            }
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(buildDir)
}