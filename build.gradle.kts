plugins {
    id("dev.isxander.modstitch.base") version "0.8.4"
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)
        ?.let(consumer)
}

val minecraft = property("deps.minecraft") as String
val yaclVersion = property("deps.yacl") as String

val version = "1.0.0"

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
var constraint: String = name.split("-")[1]
stonecutter {
    consts(
        "fabric" to (constraint == "fabric"),
        "neoforge" to (constraint == "neoforge"),
        "forge" to (constraint == "forge"),
        "vanilla" to (constraint == "vanilla")
    )
}

modstitch {
    minecraftVersion = minecraft

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    javaVersion = if (stonecutter.eval(minecraft, ">=1.20.5")) 21 else 17

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") { mappingsVersion = it }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = "pinkfont"
        modName = "PinkFont"
        modVersion = "$version+$minecraft-$constraint"
        modGroup = "me.andreasmelone"
        modAuthor = "AndreasMelone"
        modDescription = "Allows you to change the color of text in Minecraft!"
        modLicense = "MIT"

        fun <K : Any, V : Any> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }

        replacementProperties.populate {
            // You can put any other replacement properties/metadata here that
            // modstitch doesn't initially support. Some examples below.
            put("mod_issue_tracker", "https://github.com/RaydanOMGr/PinkFont/issues")
            put("mod_repo", "https://github.com/RaydanOMGr/PinkFont")
            put("minecraft_version", minecraft)
            put("yacl_version", yaclVersion)
            put("java_version", "" + javaVersion.get())
            if(isLoom) {
                put("fapi_version", property("deps.fapi") as String)
            }
            if(isModDevGradleLegacy) {
                put("forge_version", (property("deps.forge") as String).split("-", limit = 2)[1])
            } else {
                put("forge_version", "0.0")
            }
        }
    }

    // Fabric Loom (Fabric)
    loom {
        // It's not recommended to store the Fabric Loader version in properties.
        // Make sure its up to date.
        fabricLoaderVersion = "0.17.3"

        // Configure loom like normal in this block.
        configureLoom {
            runConfigs.all {
                ideConfigGenerated(true)
            }
        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        prop("deps.forge") { forgeVersion = it }
        prop("deps.neoform") { neoFormVersion = it }
        prop("deps.neoforge") { neoForgeVersion = it }
        prop("deps.mcp") { mcpVersion = it }

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()

        // This block configures the `neoforge` extension that MDG exposes by default,
        // you can configure MDG like normal from here
//        configureNeoforge {
//            runs.all {
//                disableIdeRun()
//            }
//        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true

        configs.register("pinkfont")

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("examplemod-fabric")
        // if (isModDevGradleRegular) configs.register("examplemod-neoforge")
        // if (isModDevGradleLegacy) configs.register("examplemod-forge")
    }
}

// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    modstitch.loom {
        val fapiVersion = property("deps.fapi")
        val modMenuVersion = property("deps.modmenu")

        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${fapiVersion}")
        modstitchModImplementation("com.terraformersmc:modmenu:${modMenuVersion}")
    }

    // Anything else in the dependencies block will be used for all platforms.
    modstitchModImplementation("dev.isxander:yet-another-config-lib:${yaclVersion}-${constraint}")
}