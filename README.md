# KGit
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/sya-ri/KGit)](https://github.com/sya-ri/KGit/releases/latest)
[![maven-central](https://img.shields.io/maven-central/v/com.github.sya-ri/kgit)](https://search.maven.org/artifact/com.github.sya-ri/kgit)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

KGit is Kotlin Wrapper Library of [JGit](https://eclipse.org/jgit).
- Null Safety Methods
- Command Settings Available Using Lambda Function (No Method Chain)
- Support JGit features: `7.0.0.202409031743-r`

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
    implementation 'com.github.sya-ri:kgit:1.0.6'
}
```

### build.gradle.kts

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.sya-ri:kgit:1.0.6")
}
```

## Develop

### Git Commit

#### Before Commit
Run the gradle task to check the code format.

```
gradle lintKotlin
```

#### Template

```
<type>: <subject>
```

#### Type

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Documentation only changes
- **style**: Changes that do not affect the meaning of the code (white-space, formatting, etc)
- **refactor**: A code change that neither fixes a bug nor adds a feature
- **perf**: A code change that improves performance
- **test**: Adding missing or correcting existing tests
- **chore**: Changes to the build process or auxiliary tools and libraries such as documentation generation
