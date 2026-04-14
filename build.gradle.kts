import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.versions)
    alias(libs.plugins.dokka)
    alias(libs.plugins.dokka.javadoc)
    alias(libs.plugins.mavenPublish)
}

group = "com.github.syari.kgit"
version = "1.2.0"

repositories {
    mavenCentral()
    maven("https://repo.eclipse.org/content/groups/releases/")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(libs.jgit)
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("com.github.sya-ri", "kgit", version.toString())
    configure(
        KotlinJvm(
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationJavadoc"),
            sourcesJar = true,
        ),
    )
    pom {
        packaging = "pom"
        name.set("KGit")
        description.set("Kotlin Wrapper Library of JGit")
        inceptionYear.set("2020")
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
