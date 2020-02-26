package com.ivan200.theming.preferences

//
// Created by Ivan200 on 21.02.2020.
//

object Prefs : PrefsBase() {
    var colorPrimary by IntPref()
    var colorBackground by IntPref()

    var colorFine by IntPref()
    var colorError by IntPref()

    var colorStatusBar by IntPref()
    var colorStatusBarDark by IntPref()
    var statusDrawSystemBar by BooleanPref()
    var statusLightTheme by BooleanPref()

    var colorNavBar by IntPref()
    var colorNavBarDark by IntPref()
    var navBarDrawSystemBar by BooleanPref()
    var navBarLightTheme by IntPref()
    var colorNavBarDivider by IntPref()

    var colorOverScroll by IntPref()

    var colorText by IntPref()
    var colorTextHint by IntPref()

    var colorInputPasswordEye by IntPref()
    var colorInputHelper by IntPref()
    var colorInputError by IntPref()
    var colorInputHintFocused by IntPref()
    var colorInputHint by IntPref()
    var colorInputText by IntPref()
    var colorInputBottomLine by IntPref()
    var colorInputCursor by IntPref()
    var colorInputHighlight by IntPref()
    var colorInputHandles by IntPref()


    var colorProgressBar by IntPref()
    var colorProgressBarSecondary by IntPref()
    var colorProgressBarBackground by IntPref()
    var colorSeekBarThumb by IntPref()
    var colorSeekBarTickMark by IntPref()

    var colorButtonBackground by IntPref()
    var colorButtonText by IntPref()

    var colorCheckBoxActive by IntPref()
    var colorCheckBoxInactive by IntPref()

    var colorRadioActive by IntPref()
    var colorRadioInactive by IntPref()

    var colorSwitchActive by IntPref()
    var colorSwitchInactive by IntPref()

    var colorFabBackground by IntPref()
    var colorFabIcon by IntPref()

    var colorAlertBackground by IntPref()
    var colorAlertTitle by IntPref()
    var colorAlertIcon by IntPref()
    var colorAlertMessage by IntPref()
    var colorAlertButtons by IntPref()

    var colorIcon by IntPref()

    var colorActionBar by IntPref()
    var colorActionBarText by IntPref()
    var colorActionBarTextSecondary by IntPref()
    var colorActionBarIcons by IntPref()

    var colorBottomNavBackground by IntPref()
    var colorBottomNavIcon by IntPref()
    var colorBottomNavIconSelected by IntPref()
    var colorBottomNavText by IntPref()
    var colorBottomNavTextSelected by IntPref()

}