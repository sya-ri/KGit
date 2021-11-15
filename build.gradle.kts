import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm") version "1.6.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
    `maven-publish`
    signing
}

group = "com.github.syari.kgit"
version = "1.0.5"

repositories {
    mavenCentral()
    maven("https://repo.eclipse.org/content/groups/releases/")
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.eclipse.jgit:org.eclipse.jgit:5.11.0.202103091610-r")
}

configure<KtlintExtension> {
    version.set("0.40.0")
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    repositories {
        maven {
            url = uri(
                if (version.toString().endsWith("SNAPSHOT")) {
                    "https://oss.sonatype.org/content/repositories/snapshots"
                } else {
                    "https://oss.sonatype.org/service/local/staging/deploy/maven2"
                }
            )
            credentials {
                username = System.getenv("SONATYPE_USER")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
    publications {
        register<MavenPublication>("maven") {
            groupId = "com.github.sya-ri"
            artifactId = "kgit"
            from(components["kotlin"])
            artifact(sourceJar.get())
            pom {
                packaging = "pom"
                name.set("KGit")
                description.set("Kotlin Wrapper Library of JGit")
                url.set("https://github.com/sya-ri/KGit")
                licenses {
                    license {
                        name.set("Eclipse Public License 2.0")
                        url.set("https://www.eclipse.org/legal/epl-2.0/")
                    }
                }
                developers {
                    developer {
                        id.set("sya-ri")
                        name.set("sya-ri")
                        email.set("sya79lua@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/sya-ri/KGit.git")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
