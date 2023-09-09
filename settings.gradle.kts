pluginManagement {
    repositories {
//        maven(url = "https://maven.aliyun.com/repository/google")
//        maven(url = "https://maven.aliyun.com/repository/public/")

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
//        maven(url = "https://maven.aliyun.com/repository/public/")
        google()
        mavenCentral()
        // ❗若你的 Plugin 版本过低，作为 Xposed 模块使用务必添加，其它情况可选
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        // ❗作为 Xposed 模块使用务必添加，其它情况可选
        maven(url = "https://api.xposed.info/")
        // MavenCentral 有 2 小时缓存，若无法集成最新版本请添加此地址
        maven(url = "https://s01.oss.sonatype.org/content/repositories/releases")
    }
}

rootProject.name = "fuck_shake"
include(":app")
