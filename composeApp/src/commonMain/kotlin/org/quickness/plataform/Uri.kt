@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.quickness.plataform

import org.quickness.interfaces.plataform.Uri

expect class Uri(url: String) : Uri {
    override fun navigate()
}
