package org.quickness.utils.`object`

import org.quickness.utils.`object`.Extensions.isVowel
import org.quickness.utils.enums.MexicanState
import org.quickness.utils.enums.RestrictedWords

object ValidatesData {
    fun isNotInappropriate(name: String): Boolean {
        val prohibitedWordsSet = RestrictedWords.getAllWords().toSet()

        val nameWords = name.split(Regex("\\s+"))

        return nameWords.none { word -> prohibitedWordsSet.contains(word.lowercase()) }
    }

    fun doesDateMatchCurp(
        curpDate: String,
        day: String,
        month: String,
        year: String
    ): Boolean = curpDate == "${year.takeLast(2)}${month.padStart(2, '0')}${day.padStart(2, '0')}"

    fun doesSexMatchCurp(
        curpSex: Char,
        curp: String,
        sex: String
    ): Boolean {
        val enteredSex = curp[10].uppercaseChar()
        return enteredSex == curpSex && enteredSex == if (sex == "Male") 'H' else 'M'
    }

    fun doesStateMatchCurp(
        curpState: String,
        selectedState: String
    ): Boolean {
        return MexicanState.fromDisplayName(selectedState)?.code == curpState
    }

    fun doNamesMatchCurp(
        curpName: String,
        name: String
    ): Boolean {
        val names = name.split(" ")
        if (names.size < 3) return false

        val firstSurname = names[0].uppercase()
        val secondSurname = names[1].uppercase()
        val firstName = names[2].uppercase()

        var generatedInitials =
            "${firstSurname[0]}${firstSurname.drop(1).firstOrNull { it.isVowel() }}"
        generatedInitials += secondSurname[0]
        generatedInitials += firstName[0]

        return curpName.startsWith(generatedInitials)
    }
}