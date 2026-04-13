---
name: update-jgit
description: Update KGit to the latest org.eclipse.jgit release by resolving the current Maven Central version, checking out the matching upstream JGit tag, syncing only org.eclipse.jgit/src/org/eclipse/jgit/api/*Command.java, and updating Kotlin wrappers for public API changes.
---

# Update JGit

Use this skill when KGit needs to refresh its vendored `org.eclipse.jgit/src/org/eclipse/jgit/api/*Command.java` sources and wrapper commands.

## Workflow

1. Resolve the latest `org.eclipse.jgit:org.eclipse.jgit` release from Maven Central.
2. Clone upstream JGit if `jgit-src/` is missing:
   `git clone "https://eclipse.gerrithub.io/eclipse-jgit/jgit" jgit-src`
3. Find the exact upstream tag that matches the Maven Central version.
   Use the release tag, not a stable branch. The tag format is `v<version>`, for example `v7.6.0.202603022253-r`.
4. Check out that tag in `jgit-src/`.
5. Sync only `jgit-src/org.eclipse.jgit/src/org/eclipse/jgit/api/*Command.java` into `jgit/org.eclipse.jgit/src/org/eclipse/jgit/api/`.
6. Compare the `*Command.java` files under `jgit/org.eclipse.jgit/src/org/eclipse/jgit/api` against the previous state and update Kotlin wrappers under `src/main/kotlin/com/github/syari/kgit`.
   Inspect three cases in existing files: added public methods, removed public methods, and changed public method signatures or semantics.
   Do not limit the review to newly added command files.
7. Update the documented supported JGit version in `README.md` and `gradle/libs.versions.toml`.
8. Run the build and fix any remaining API mismatches.

## What To Check

- New `*Command.java` files under `org.eclipse.jgit.api`
- Added public methods on existing `org.eclipse.jgit.api.*Command` classes
- Removed public methods on existing `org.eclipse.jgit.api.*Command` classes
- Changed public method signatures or behavior on existing `org.eclipse.jgit.api.*Command` classes
- New enum or type imports required by wrapper signatures
- README support-version text

## Notes

- The practical source of truth for checkout is the upstream tag, not a branch.
- The vendored update target is `org.eclipse.jgit/src/org/eclipse/jgit/api/*Command.java`, not the whole upstream source tree.
- `api/errors` and other non-command files can be ignored for this workflow.
- For wrapper work, focus on public API changes in the command classes first.
- Even if `Git.java` is not part of the vendored sync target, a newly introduced command may still require a corresponding `KGit` convenience method.
