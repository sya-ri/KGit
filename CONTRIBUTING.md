# Contributing

### Update Bundled JGit

1. Resolve the latest `org.eclipse.jgit:org.eclipse.jgit` version from Maven Central.
2. Clone the upstream JGit repository:

```bash
git clone "https://eclipse.gerrithub.io/eclipse-jgit/jgit" jgit-src
```

3. Check out the tag that matches the Maven Central version exactly. For example:

```bash
git -C jgit-src checkout v7.7.1.202607240634-r
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
./gradlew lintKotlin
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
