package com.utils.enums

/**
 * Enum class representing categories of restricted or sensitive words.
 *
 * @property words List of words belonging to this category.
 */
enum class RestrictedWords(val words: List<String>) {
    ADMINISTRATIVE(
        listOf(
            "admin", "administrator", "root", "superuser", "mod", "moderator", "support",
            "helpdesk", "sysadmin", "system", "owner", "god", "master", "boss",
            "manager", "teamlead", "director"
        )
    ),

    OFFENSIVE_ENGLISH(
        listOf(
            "idiot", "stupid", "dumb", "fool", "jerk", "moron", "loser", "trash", "scum",
            "nonsense", "troll", "hater", "clown", "brainless", "blockhead", "dunce"
        )
    ),

    OFFENSIVE_SPANISH(
        listOf(
            "puta", "puto", "pendejo", "idiota", "imbecil", "tarado", "baboso", "tonto",
            "estupido", "cabrón", "gilipollas", "mamon", "mierda", "imbécil", "zorra",
            "carajo", "pelotudo", "huevón", "maldito"
        )
    ),

    HATEFUL(
        listOf(
            "racist", "nazi", "bigot", "terrorist", "killer", "murderer", "pedophile",
            "abuser", "pervert", "incest", "rapist", "misogynist", "homophobe",
            "antisemitic", "xenophobe", "hater", "extremist", "genocidal"
        )
    ),

    EXPLICIT_SEXUAL(
        listOf(
            "sex", "porn", "xxx", "naked", "boobs", "butt", "orgasm", "penis", "vagina",
            "cum", "sperm", "milf", "bdsm", "fetish", "anal", "hentai", "incest",
            "masturbate", "ejaculate", "pornhub", "camgirl", "squirting"
        )
    ),

    SPAM(
        listOf(
            "free", "money", "winner", "lottery", "cash", "bitcoin", "crypto",
            "giveaway", "prize", "gift", "cheap", "discount", "deal", "loan",
            "investment", "click", "profit", "rich", "earn"
        )
    ),

    RELIGIOUS(
        listOf(
            "mohammed", "allah", "buddha", "god", "messiah", "prophet", "christ", "satan",
            "lucifer", "devil", "hell", "demon", "antichrist", "shiva", "zeus"
        )
    ),

    DRUGS(
        listOf(
            "weed", "cannabis", "cocaine", "heroin", "meth", "lsd", "ecstasy", "opium",
            "addict", "drug", "stoned", "high", "joint", "blunt", "molly", "shrooms",
            "fentanyl", "oxy", "dope"
        )
    ),

    SENSITIVE(
        listOf(
            "suicide", "death", "kill", "die", "gun", "bomb", "weapon", "war", "violence",
            "blood", "terror", "shoot", "execute", "massacre", "assassinate", "genocide",
            "attack", "explosion", "slaughter", "hostage"
        )
    );

    companion object {
        /**
         * Retrieves all words across all categories.
         *
         * @return List of all restricted words.
         */
        fun getAllWords(): List<String> {
            return entries.flatMap { it.words }
        }

        /**
         * Finds the category a word belongs to.
         *
         * @param word The word to search for.
         * @return The [RestrictedWords] category, or `null` if not found.
         */
        fun findCategory(word: String): RestrictedWords? {
            return entries.find { category -> word in category.words }
        }
    }
}