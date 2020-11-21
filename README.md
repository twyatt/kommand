![badge][badge-mac]

# Kommand

Quick command execution tool.

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
                "command": SHELL_COMMAND
            },
            ...
        },
        ...
    }
}
```

When `kommand` is run, the current directory name is considered the **project** being operated on.
Execution working directory will be the current directory name relative to the configured `home`.

### Example

```json
{
    "home": "/Users/travis/Projects",
    "projects": {
        "kable": {
            "install": {
                "command": "./gradlew publishToMavenLocal"
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
                "command": "./gradlew runDebugExecutableMacosX64"
            }
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
