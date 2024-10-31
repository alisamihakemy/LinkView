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
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/alisamihakemy/LinkView")  // Your Maven repository URL
            credentials {
                username = "alisamihakemy"
                password = "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"
            }
        }
    }

}

rootProject.name = "LinkView"
include(":app")
include(":linkView")
