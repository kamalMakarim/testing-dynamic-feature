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

rootProject.name = "Testing Dynamic Module"
include(":app")
include(":feature_user1")
include(":feature_user2")
include(":feature_user3")
include(":feature_user4")
include(":feature_user5")
include(":feature_user6")
include(":feature_user7")
include(":feature_user8")
include(":feature_user9")
include(":feature_user10")
