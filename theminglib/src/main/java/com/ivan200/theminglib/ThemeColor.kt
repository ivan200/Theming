package com.ivan200.theminglib

enum class ThemeColor {
    //base colors
    /** Основной цвет подсветки **/
    colorPrimary,
    /** Цвет фона **/
    colorBackground,

    //additional colors
    /** Цвет хорошего результата **/
    colorFine,
    /** Цвет плохого результата **/
    colorError,

    //System components
    /** Цвет фона окна, на случай если статусбар прозрачный **/
    colorWindowBackground,
    /** Цвет статусбара **/
    colorStatusBar,
    /** Цвет статусбара на апи 21-22, где текст на статусбаре всегда белый **/
    colorStatusBarDark,

    /** Цвет нижнего навбара **/
    colorNavBar,
    /** Цвет нижнего навбара на апи 21-25, где кнопки всегда белые **/
    colorNavBarDark,
    /** Цвет разделителя нижнего навбара, включается на апи 28+ **/
    colorNavBarDivider,

    /** Цвет подсветки RecyclerView и ListView при скролле.
     * От него наследуются цвета разных сторон подсветки,
     * и также используется на апи<21 для всех строн RecyclerView **/
    colorEdgeGlow,

    /** Цвет подсветки верхней границы ScrollView, ListView и RecyclerView **/
    colorEdgeGlowTop,
    /** Цвет подсветки нижней границы ScrollView, ListView и RecyclerView **/
    colorEdgeGlowBottom,
    /** Цвет подсветки левой границы HorizontalScrollView, ViewPager и RecyclerView **/
    colorEdgeGlowLeft,
    /** Цвет подсветки правой границы HorizontalScrollView, ViewPager и RecyclerView **/
    colorEdgeGlowRight,

    //Text
    /** Цвет текста **/
    colorText,
    /** Цвет хинта текста **/
    colorTextHint,

    /** Цвет разделителя в ячейках **/
    colorDivider,

    //Input
    /** Цвет иконки глаза скрывающей пароль **/
    colorInputPasswordEye,
    /** Цвет подсвечиваемого помощника **/
    colorInputHelper,
    /** Цвет подсвечиваемой ошибки **/
    colorInputError,
    /** Цвет верхней надписи при активации данного поля ввода **/
    colorInputHintFocused,
    /** Цвет хинта вводимого текста **/
    colorInputHint,
    /** Цвет вводимого текста **/
    colorInputText,
    /** Цвет полоски под полем ввода **/
    colorInputBottomLine,
    /** Цвет моргающего курсора **/
    colorInputCursor,
    /** Цвет выделения текста **/
    colorInputHighlight,
    /** Цвет захватов при выделении  текста **/
    colorInputHandles,

    //ProgressBar
    /** Цвет прогрессбара **/
    colorProgressBar,
    /** Цвет второго прогрессбара (автоматически затеняется) **/
    colorProgressBarSecondary,
    /** Цвет фона прогрессбара **/
    colorProgressBarBackground,
    //SeekBar
    /** Цвет ручки сикбара **/
    colorSeekBarThumb,
    /** Цвет точек на сикбаре с делениями **/
    colorSeekBarTickMark,

    //Button
    /** Цвет фона кнопки **/
    colorButtonBackground,
    /** Цвет текста кнопки **/
    colorButtonText,

    //Checkbox
    /** Цвет чекбокса **/
    colorCheckBoxActive,
    /** Цвет неактивированного чекбокса **/
    colorCheckBoxInactive,

    //RadioButton
    /** Цвет радиокнопки **/
    colorRadioActive,
    /** Цвет неактивированной радиокнопки **/
    colorRadioInactive,

    //Switch
    /** Цвет переключателя **/
    colorSwitchActive,
    /** Цвет неактивированного переключателя **/
    colorSwitchInactive,

    //FloatingActionButton
    /** Цвет фона fab кнопки **/
    colorFabBackground,
    /** Цвет иконки на fab кнопке **/
    colorFabIcon,

    //AlertDialog
    /** Цвет фона диалога **/
    colorAlertBackground,
    /** Цвет заголовка диалога **/
    colorAlertTitle,
    /** Цвет иконки диалога **/
    colorAlertIcon,
    /** Цвет текста диалога **/
    colorAlertMessage,
    /** Цвет кнопок  диалога **/
    colorAlertButtons,
    /** Цвет позитивной кнопки далога **/
    colorAlertButtonPositive,
    /** Цвет негативной кнопки далога **/
    colorAlertButtonNegative,
    /** Цвет нейтральной кнопки далога **/
    colorAlertButtonNeutral,

    //ImageView
    /** Цвет иконок **/
    colorIcon,

    //ActionBar
    /** Цвет верхнего экшенбара **/
    colorActionBar,
    /** Цвет текста верхнего экшенбара **/
    colorActionBarText,
    /** Цвет второго текста верхнего экшенбара **/
    colorActionBarTextSecondary,
    /** Цвет иконок верхнего экшенбара **/
    colorActionBarIcons,

    //BottomNavigationView
    /** Цвет фона нижней панели **/
    colorBottomNavBackground,
    /** Цвет иконок нижней панели **/
    colorBottomNavIcon,
    /** Цвет иконки выбранной вкладки нижней панели **/
    colorBottomNavIconSelected,
    /** Цвет текста нижней панели **/
    colorBottomNavText,
    /** Цвет текста выбранной вкладки нижней панели **/
    colorBottomNavTextSelected,

}