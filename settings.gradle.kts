pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }


}
buildscript{
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}


rootProject.name = "VideoShort_FireBase"
include(":app")
 