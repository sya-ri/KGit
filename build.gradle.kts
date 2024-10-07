plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.versions)
    `maven-publish`
    signing
}

group = "com.github.syari.kgit"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://repo.eclipse.org/content/groups/releases/")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(libs.jgit)
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
                },
            )
            credentials {
                username = properties["sonatypeUsername"].toString()
                password = properties["sonatypePassword"].toString()
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
                        email.set("contact@s7a.dev")
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
    val key = properties["signingKey"]?.toString()?.replace("\\n", "\n")
    val password = properties["signingPassword"]?.toString()

    useInMemoryPgpKeys(key, password)
    sign(publishing.publications["maven"])
}
