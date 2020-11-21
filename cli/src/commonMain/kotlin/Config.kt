package com.juul.kommand

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

expect fun read(path: String): String?

val config: Config by lazy {
    val configPath = "$homePath/.kommand.json"
    val text = read(configPath) ?: error("Config '$configPath' not found")
    Json.Default.decodeFromString(text)
}

private typealias Name = String

@Serializable(with = ConfigSerializer::class)
data class Config(
    @SerialName("home") val homeDirectory: String,
    val projects: Map<Name, Project>,
)

data class Project(
    val name: String,
    val commands: Map<Name, Command>,
)

@Serializable
data class Command(
    @SerialName("dependsOn") val dependencies: List<Dependency>? = null,
    val command: String
)

@Serializable
data class Dependency(
    val project: String,
    val command: String,
)

object ConfigSerializer : KSerializer<Config> {

    override val descriptor: SerialDescriptor = ConfigSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Config) = throw UnsupportedOperationException()

    override fun deserialize(decoder: Decoder): Config {
        val surrogate = decoder.decodeSerializableValue(ConfigSurrogate.serializer())
        return Config(
            homeDirectory = surrogate.homeDirectory,
            projects = surrogate.projects.map { (name, commands) ->
                name to Project(name, commands)
            }.toMap()
        )
    }
}

@Serializable
private data class ConfigSurrogate(
    @SerialName("home") val homeDirectory: String,
    val projects: Map<Name, Map<Name, Command>>,
)
