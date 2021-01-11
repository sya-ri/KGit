import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

group = "com.github.syari.kgit"
version = "1.0.0"

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
