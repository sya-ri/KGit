import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    `maven-publish`
}

group = "com.github.syari.kgit"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://repo.eclipse.org/content/groups/releases/")
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.eclipse.jgit:org.eclipse.jgit:5.10.0.202012080955-r")
}

configure<KtlintExtension> {
    version.set("0.40.0")
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava.srcDirs)
}

publishing {
    repositories {
        maven {
            name = "Bintray"
            url = uri("https://api.bintray.com/maven/sya-ri/maven/KGit/;publish=1;override=1")
            credentials {
                username = System.getenv("BINTRAY_USER")
                password = System.getenv("BINTRAY_KEY")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sya-ri/KGit")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("maven") {
            groupId = "com.github.syari"
            artifactId = "kgit"
            from(components["java"])
            artifact(sourceJar.get())
        }
    }
}
