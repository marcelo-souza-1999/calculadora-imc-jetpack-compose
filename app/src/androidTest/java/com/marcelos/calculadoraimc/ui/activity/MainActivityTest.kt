package com.marcelos.calculadoraimc.ui.activity

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkAllElementsAreVisible() {
        composeTestRule.setContent {
            MainActivity().MainContent()
        }

        composeTestRule.onNodeWithTag("topBar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("txtFieldPeso").assertIsDisplayed()
        composeTestRule.onNodeWithTag("txtFieldAltura").assertIsDisplayed()
        composeTestRule.onNodeWithTag("txtTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("txtResult").assertExists()
        composeTestRule.onNodeWithTag("btnCalcularImc").assertIsDisplayed()
    }

    @Test
    fun checkTopBarAndTxtTitleIsCorrect() {
        composeTestRule.setContent {
            MainActivity().MainContent()
        }

        composeTestRule
            .onNodeWithTag("topBar")
            .onChildren()
            .filter(hasText("Calculadora IMC"))
            .assertAny(hasText("Calculadora IMC"))

        composeTestRule
            .onNodeWithTag("txtTitle")
            .assertTextEquals("Calcular IMC")
    }

    @Test
    fun iconButtonClickReturnsFocusToPesoField() {
        composeTestRule.setContent {
            MainActivity().MainContent()
        }

        composeTestRule.onNodeWithTag("iconButtonTopBar")
            .performClick()

        composeTestRule.onNodeWithTag("txtFieldPeso")
            .assertIsFocused()
    }

    @Test
    fun calcularButtonShowsResultWhenFieldsAreFilled() {
        composeTestRule.setContent {
            MainActivity().MainContent()
        }

        composeTestRule.onNodeWithTag("txtFieldPeso").performTextInput("70")
        composeTestRule.onNodeWithTag("txtFieldAltura").performTextInput("1.75")

        composeTestRule.onNodeWithTag("btnCalcularImc").performClick()

        composeTestRule.onNodeWithText("Seu IMC é 22.86\nVocê está com o peso normal")
            .assertIsDisplayed()
    }
}