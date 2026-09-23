# Contributing

## Update bundled JGit

1. Resolve the latest `org.eclipse.jgit:org.eclipse.jgit` version from Maven Central and check out its exact matching tag in the [upstream repository](https://eclipse.gerrithub.io/eclipse-jgit/jgit).
2. Copy only `org.eclipse.jgit/src/org/eclipse/jgit/api/*Command.java` into the matching directory under `jgit/`.
3. Update wrappers in `src/main/kotlin/com/github/syari/kgit` for added, removed, or changed public command methods. Add `KGit` entrypoints for new commands as needed; non-command files and `api/errors` are outside this sync.
4. Run the build and resolve wrapper mismatches.

## Git commits

Run `./gradlew lintKotlin` before committing. Use `<type>: <subject>` with these types:

| Type | Change |
| --- | --- |
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation |
| `style` | Formatting without behavior changes |
| `refactor` | Restructuring without a feature or fix |
| `perf` | Performance improvement |
| `test` | Added or corrected tests |
| `chore` | Build or supporting tools |
