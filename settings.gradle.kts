pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "game2048"

include(":androidApp")
include(":shared:core:core-domain")
include(":shared:core:core-data")
include(":shared:core:core-database")
include(":shared:feature:feature-game")
include(":shared:feature:feature-settings")
include(":shared:feature:feature-history")
include(":shared:di")
include(":shared:core:core-presentation")
include(":shared:app")
include(":desktopapp")
