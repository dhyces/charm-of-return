import modsdotgroovy.Dependency

ModsDotGroovy.make {
    def modid = this.buildProperties["mod_id"]

    modLoader = "javafml"
    loaderVersion = "[1,)"

    license = "MIT"
    issueTrackerUrl = "https://github.com/dhyces/charm-of-return/issues"

    mod {
        modId = modid
        displayName = this.buildProperties["mod_name"]
        version = this.version
        group = this.group
        authors = [this.buildProperties["authors"] as String]

        displayUrl = "https://www.curseforge.com/minecraft/mc-mods/charm-of-return"
        sourcesUrl = "https://github.com/dhyces/charm-of-return"
        logoFile = "icon.png"
        description = "Magic mirror, but it's a charm. Return to spawn point!"

        onFabric {
            entrypoints {
                main = "dev.dhyces.charmofreturn.FabricCharmOfReturn"
                client = "dev.dhyces.charmofreturn.FabricCharmOfReturnClient"
                entrypoint("fabric-datagen", "dev.dhyces.charmofreturn.FabricCharmOfReturnDatagen")
                entrypoint("jei_mod_plugin", "dev.dhyces.charmofreturn.integration.JeiPlugin")
                entrypoint("modmenu", "dev.dhyces.charmofreturn.integration.YaclModmenuCompat")
            }
        }

        dependencies {
            mod("yet_another_config_lib_v3") {
                versionRange = this.libs.versions.yacl.range
                mandatory = false
                side = DependencySide.CLIENT
            }
            mod("jei") {
                versionRange = this.libs.versions.jei.range
                mandatory = false
                side = DependencySide.CLIENT
            }
            minecraft = this.minecraftVersionRange
            onForge {
                neoforge = ">=${this.libs.versions.neoforge}"
            }

            onFabric {
                fabricloader = ">=${this.fabricLoaderVersion}"
                mod('fabric-api') {
                    versionRange = this.libs.versions.fabric.api.range
                }
                mod("modmenu") {
                    versionRange = this.libs.versions.modmenu.range
                    mandatory = false
                }
            }
        }

        onForge {
            dependencies = dependencies.collect { dep ->
                new Dependency() {
                    @Override
                    Map asForgeMap() {
                        def map = dep.asForgeMap()
                        def mandatory = map.mandatory
                        map.remove('mandatory')
                        map.put('type', mandatory ? 'required' : 'optional')
                        return map
                    }
                }
            }
        }
    }

    onFabric {
        environment = "*"
        mixin = [
                modid + ".mixins.json"
        ]
    }
}