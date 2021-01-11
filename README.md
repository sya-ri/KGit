# KGit
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/sya-ri/KGit)](https://github.com/sya-ri/KGit/releases/latest) [![Publish package to GitHub Packages](https://github.com/sya-ri/KGit/workflows/Publish%20package%20to%20GitHub%20Packages/badge.svg)](https://github.com/sya-ri/KGit/actions) [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

KGit is Kotlin Wrapper Library of [JGit](https://github.com/eclipse/jgit).
- Null Safety Methods
- Command Settings Available Using Lambda Function (No Method Chain)

## Installation

### build.gradle

```groovy
repositories {
    maven { url 'https://dl.bintray.com/sya-ri/maven/' }
}

dependencies {
    implementation 'com.github.syari:kgit:1.0.1'
}
```

### build.gradle.kts

```kotlin
repositories {
    maven(url = "https://dl.bintray.com/sya-ri/maven/")
}

dependencies {
    implementation("com.github.syari:kgit:1.0.1")
}
```

## Develop

### Git Commit

#### Before Commit
Run the gradle task to check the code format.

```
gradle ktlintCheck
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