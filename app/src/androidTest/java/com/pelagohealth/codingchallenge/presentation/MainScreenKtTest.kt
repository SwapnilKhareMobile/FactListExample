package com.pelagohealth.codingchallenge.presentation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.pelagohealth.codingchallenge.MainActivity
import com.pelagohealth.codingchallenge.di.DataModule
import com.pelagohealth.codingchallenge.di.NetworkModule
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.presentation.ui.uistate.FactUIState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DataModule::class, NetworkModule::class)
class MainScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        // Get the ViewModel instance from the launched Activity
        viewModel = composeTestRule.activity.viewModel()
    }

    @Test
    fun testLoadingState() {
        viewModel.factList.value = FactUIState.Loading

        composeTestRule.onNodeWithTag("progress_indicator").assertExists()
    }

    @Test
    fun testErrorState() {
        viewModel.factList.value = FactUIState.Error

        composeTestRule.onNodeWithText("Error!").assertExists()
        composeTestRule.onNodeWithText("Try again!").assertExists().performClick()
    }

    @Test
    fun testNoDataState() {
        viewModel.factList.value = FactUIState.Success(emptyList())

        composeTestRule.onNodeWithText("No facts available please click on more facts to fetch new facts!").assertExists()
    }

    @Test
    fun testSuccessState() {
        val facts = listOf(
            Fact("Fact 1", "http://fact1.com"),
            Fact("Fact 2", "http://fact2.com")
        )
        viewModel.factList.value = FactUIState.Success(facts)

        composeTestRule.onNodeWithTag("random_facts_title").assertExists()
        composeTestRule.onNodeWithText("Fact 1").assertExists()
        composeTestRule.onNodeWithText("Fact 2").assertExists()
    }
}