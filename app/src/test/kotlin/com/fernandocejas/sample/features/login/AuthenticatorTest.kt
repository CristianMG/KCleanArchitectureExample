
package com.fernandocejas.sample.features.login

import com.fernandocejas.sample.UnitTest
import com.fernandocejas.sample.domain.Authenticator
import org.amshove.kluent.shouldBe
import org.junit.Test

class AuthenticatorTest : UnitTest() {

    private val authenticator = Authenticator()

    @Test fun `returns default value`() {
        authenticator.userLoggedIn() shouldBe false
    }
}
