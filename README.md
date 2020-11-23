![badge][badge-mac]

# Kommand

Quick command execution tool that allows configuring **current directory** aware command execution.
For example: have an `install` command invoke one command for one project and a different command
for a project located in a different folder.

Supports notion of command dependencies, whereas a project's command can be configured to depend on
a separate project command; especially useful when developming against large project dependency
trees where a project compilation depends on other projects being compiled/installed first.

```
$ kommand --help
Usage: kommand [OPTIONS] COMMAND

Options:
  -u, --dry-run            Don't execute commands, only display what would be
                           run
  -d, --dependencies-only  Only execute commands for the project's
                           dependencies, not for the project itself
  -h, --help               Show this message and exit
```

## Configuration

Kommand is configured via `$HOME/.kommand.js`. The configuration file has the following structure:

```
{
    "home": PROJECTS_ROOT_DIRECTORY,
    "projects": {
        PROJECT_DIRECTORY: {
            COMMAND_NAME: {
                "dependsOn": [
                    {
                        "project": PROJECT_NAME,
                        "command": COMMAND_NAME
                    },
                    ...
                ],
                "run": SHELL_COMMAND
            },
            ...
        },
        ...
    },
    "global": {
        COMMAND_NAME: ...
    }
}
```

When `kommand` is run, the current directory name is considered the **project** being operated on.
Execution working directory will be the current directory name relative to the configured `home`.
`global` commands are available regardless of what the current directory is.

### Example

```json
{
    "home": "/Users/travis/Projects",
    "projects": {
        "kable": {
            "install": {
                "run": "./gradlew publishToMavenLocal"
            }
        },
        "sensortag": {
            "run": {
                "dependsOn": [
                    {
                        "project": "kable",
                        "command": "install"
                    }
                ],
                "run": "./gradlew runDebugExecutableMacosX64"
            }
        }
    },
    "global": {
        "config": {
            "run": "subl ~/.kommand.json"
        }
    }
}
```

With the current directory as `/Users/travis/Projects/sensortag`, running `kommand run` will result in:

```
🏃 kable ▶ ./gradlew publishToMavenLocal
🏃 sensortag ▶ ./gradlew runDebugExecutableMacosX64
```


[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-ios]: http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat
[badge-js]: http://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat
[badge-linux]: http://img.shields.io/badge/platform-linux-2D3F6C.svg?style=flat
[badge-windows]: http://img.shields.io/badge/platform-windows-4D76CD.svg?style=flat
[badge-mac]: http://img.shields.io/badge/platform-macos-111111.svg?style=flat
[badge-watchos]: http://img.shields.io/badge/platform-watchos-C0C0C0.svg?style=flat
[badge-tvos]: http://img.shields.io/badge/platform-tvos-808080.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/platform-wasm-624FE8.svg?style=flat
