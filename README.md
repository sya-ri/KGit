# KGit
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/sya-ri/KGit)](https://github.com/sya-ri/KGit/releases/latest)
[![maven-central](https://img.shields.io/maven-central/v/com.github.sya-ri/kgit)](https://search.maven.org/artifact/com.github.sya-ri/kgit)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

KGit is Kotlin Wrapper Library of [JGit](https://projects.eclipse.org/projects/technology.jgit).
- Null Safety Methods
- Command Settings Available Using Lambda Function (No Method Chain)
- Support JGit features: `7.7.1.202607240634-r`

## Examples

### Clone Repository

#### JGit
```kotlin
val git: Git = Git.cloneRepository()
                  .setURI("https://github.com/sya-ri/KGit")
                  .setTimeout(60)
                  .setProgressMonitor(TextProgressMonitor())
                  .call()
```

#### KGit
```kotlin
val git: KGit = KGit.cloneRepository {
    setURI("https://github.com/sya-ri/KGit")
    setTimeout(60)
    setProgressMonitor(TextProgressMonitor())
}
```

## Installation

> [!TIP]
>
> - KGit bundles `org.eclipse.jgit:org.eclipse.jgit`.
> - Install the jgit extension library if necessary.

### build.gradle

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.github.sya-ri:kgit:1.2.2'
}
```

### build.gradle.kts

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.sya-ri:kgit:1.2.2")
}
```

## Documentation and development

- [Releases](https://github.com/sya-ri/KGit/releases): published versions and release notes.
- [Kotlin wrappers](src/main/kotlin/com/github/syari/kgit): available commands and their source.
- [Contributing](CONTRIBUTING.md): updating bundled JGit, validation, and commit conventions.
