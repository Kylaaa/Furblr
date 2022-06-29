package com.itreallyiskyler.furblr.enum

import java.security.InvalidKeyException

enum class PostTheme(val id:Int) {
    // General Things
    All(0),
    Abstract(1),
    AnimalRelatedNonAnthro(2),
    Anime(3),
    Comics(4),
    Doodle(5),
    Fanart(6),
    Fantasy(7),
    Human(8),
    Portraits(9),
    Scenery(10),
    StillLife(11),
    Tutorials(12),
    Miscellaneous(13),

    // Specialty
    BabyFur(14),
    Bondage(15),
    Digimon(16),
    FatFurs(17),
    FetishOther(18),
    Fursuit(19),
    GoreMacabre(20),
    Hyper(21),
    Hypnosis(22),
    Inflation(23),
    MacroMicro(24),
    Muscle(25),
    MyLittlePonyBrony(26),
    Paw(27),
    Pokemon(28),
    Pregnancy(29),
    Sonic(30),
    Transformation(31),
    TFTG(32),
    Vore(33),
    WaterSports(34),
    GeneralFurryArt(35),

    // Music
    Music_Techno(36),
    Music_Trance(37),
    Music_House(38),
    Music_90s(39),
    Music_80s(40),
    Music_70s(41),
    Music_60s(42),
    Music_Pre60s(43),
    Music_Classical(44),
    Music_GameMusic(45),
    Music_Rock(46),
    Music_Pop(47),
    Music_Rap(48),
    Music_Industrial(49),
    Music_OtherMusic(50);

    companion object {
        private val generalThingsSet : Set<PostTheme> = setOf(
            All, Abstract, AnimalRelatedNonAnthro, Anime, Comics, Doodle,
            Fanart, Fantasy, Human, Portraits, Scenery, StillLife,
            Tutorials, Miscellaneous
        )
        private val specialtySet : Set<PostTheme> = setOf(
            BabyFur, Bondage, Digimon, FatFurs, FetishOther, Fursuit,
            GoreMacabre, Hyper, Hypnosis, Inflation, MacroMicro, Muscle,
            MyLittlePonyBrony, Paw, Pokemon, Pregnancy, Sonic, Transformation,
            TFTG, Vore, WaterSports, GeneralFurryArt
        )
        private val musicSet : Set<PostTheme> = setOf(
            Music_Techno, Music_Trance, Music_House, Music_90s, Music_80s, Music_70s,
            Music_60s, Music_Pre60s, Music_Classical, Music_GameMusic, Music_Rock, Music_Pop,
            Music_Rap, Music_Industrial, Music_OtherMusic
        )

        fun getGeneralThemes() : Set<PostTheme> {
            return generalThingsSet
        }
        fun getSpecialityThemes() : Set<PostTheme> {
            return specialtySet
        }
        fun getMusicalThemes() : Set<PostTheme> {
            return musicSet
        }
        fun fromString(theme : String) : PostTheme {
            // TODO : pull these values from localization
            val mapping : Map<String, PostTheme> = mapOf(
                Pair("All", All),
                Pair("Abstract", Abstract),
                Pair("Animal related (non-anthro)", AnimalRelatedNonAnthro),
                Pair("Anime", Anime),
                Pair("Comics", Comics),
                Pair("Doodle", Doodle),
                Pair("Fanart", Fanart),
                Pair("Fantasy", Fantasy),
                Pair("Human", Human),
                Pair("Portraits", Portraits),
                Pair("Scenery", Scenery),
                Pair("Still Life", StillLife),
                Pair("Tutorials", Tutorials),
                Pair("Miscellaneous", Miscellaneous),
                Pair("Baby fur", BabyFur),
                Pair("Bondage", Bondage),
                Pair("Digimon", Digimon),
                Pair("Fat Furs", FatFurs),
                Pair("Fetish Other", FetishOther),
                Pair("Fursuit", Fursuit),
                Pair("Gore / Macabre Art", GoreMacabre),
                Pair("Hyper", Hyper),
                Pair("Hypnosis", Hypnosis),
                Pair("Inflation", Inflation),
                Pair("Macro / Micro", MacroMicro),
                Pair("Muscle", Muscle),
                Pair("My Little Pony / Brony", MyLittlePonyBrony),
                Pair("Paw", Paw),
                Pair("Pokemon", Pokemon),
                Pair("Pregnancy", Pregnancy),
                Pair("Sonic", Sonic),
                Pair("Transformation", Transformation),
                Pair("TF / TG", TFTG),
                Pair("Vore", Vore),
                Pair("Water Sports", WaterSports),
                Pair("General Furry Art", GeneralFurryArt),
                Pair("Techno", Music_Techno),
                Pair("Trance", Music_Trance),
                Pair("House", Music_House),
                Pair("90s", Music_90s),
                Pair("80s", Music_80s),
                Pair("70s", Music_70s),
                Pair("60s", Music_60s),
                Pair("Pre-60s", Music_Pre60s),
                Pair("Classical", Music_Classical),
                Pair("Game Music", Music_GameMusic),
                Pair("Rock", Music_Rock),
                Pair("Pop", Music_Pop),
                Pair("Rap", Music_Rap),
                Pair("Industrial", Music_Industrial),
                Pair("Other Music", Music_OtherMusic)
            )
            if (!mapping.containsKey(theme)) {
                throw InvalidKeyException(theme + " is not a valid PostTheme")
            }

            return mapping.getValue(theme)
        }
    }
}