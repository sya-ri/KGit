# KGit
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/sya-ri/KGit)](https://github.com/sya-ri/KGit/releases/latest)
[![maven-central](https://img.shields.io/maven-central/v/com.github.sya-ri/kgit)](https://search.maven.org/artifact/com.github.sya-ri/kgit)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

KGit is Kotlin Wrapper Library of [JGit](https://projects.eclipse.org/projects/technology.jgit).
- Null Safety Methods
- Command Settings Available Using Lambda Function (No Method Chain)
- Support JGit features: `7.6.0.202603022253-r`

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
    implementation 'com.github.sya-ri:kgit:1.1.0'
}
```

### build.gradle.kts

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.sya-ri:kgit:1.1.0")
}
```

## Develop

### Update Bundled JGit

1. Resolve the latest `org.eclipse.jgit:org.eclipse.jgit` version from Maven Central.
2. Clone the upstream JGit repository:

```bash
git clone "https://eclipse.gerrithub.io/eclipse-jgit/jgit" jgit-src
```

3. Check out the tag that matches the Maven Central version exactly. For example:

```bash
git -C jgit-src checkout v7.6.0.202603022253-r
```

4. Sync only `jgit-src/org.eclipse.jgit/src/org/eclipse/jgit/api/*Command.java` into `jgit/org.eclipse.jgit/src/org/eclipse/jgit/api/`.
5. Review the `*Command.java` diffs under `org.eclipse.jgit/src/org/eclipse/jgit/api` and update the Kotlin wrappers in `src/main/kotlin/com/github/syari/kgit`.
   Check not only new command files, but also added, removed, and changed public methods in existing command files.
   Ignore `api/errors` and other non-command files for this workflow.
   If a new command needs a `KGit` entrypoint, add that wrapper exposure too.
6. Run the build and fix any remaining wrapper mismatches.

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
