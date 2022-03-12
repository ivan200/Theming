package com.ivan200.theminglib

enum class ThemeColor {
    //base colors
    /** Основной цвет подсветки **/
    ColorPrimary,
    /** Цвет фона **/
    ColorBackground,

    //additional colors
    /** Цвет хорошего результата **/
    ColorFine,
    /** Цвет плохого результата **/
    ColorError,

    //System components
    /** Цвет фона окна, на случай если статусбар прозрачный **/
    ColorWindowBackground,
    /** Цвет статусбара **/
    ColorStatusBar,
    /** Цвет статусбара на апи 21-22 (Android 5), где текст на статусбаре всегда белый **/
    ColorStatusBarDark,

    /** Цвет панели навигации **/
    ColorNavBar,
    /** Цвет панели навигации на апи 21-25 (Android 5-7), где кнопки всегда белые **/
    ColorNavBarDark,
    /** Цвет разделителя панели навигаци, включается на апи 28+ (Android 9+) **/
    ColorNavBarDivider,

    /** Цвет подсветки RecyclerView и ListView при скролле.
     * От него наследуются цвета разных сторон подсветки,
     * и также используется на апи<21 (Android <5) для всех строн RecyclerView **/
    ColorEdgeGlow,

    /** Цвет подсветки верхней границы ScrollView, ListView и RecyclerView **/
    ColorEdgeGlowTop,
    /** Цвет подсветки нижней границы ScrollView, ListView и RecyclerView **/
    ColorEdgeGlowBottom,
    /** Цвет подсветки левой границы HorizontalScrollView, ViewPager и RecyclerView **/
    ColorEdgeGlowLeft,
    /** Цвет подсветки правой границы HorizontalScrollView, ViewPager и RecyclerView **/
    ColorEdgeGlowRight,

    //Text
    /** Цвет текста **/
    ColorText,
    /** Цвет хинта текста **/
    ColorTextHint,

    /** Цвет разделителя в ячейках **/
    ColorDivider,

    //Input
    /** Цвет иконки глаза скрывающей пароль **/
    ColorInputPasswordEye,
    /** Цвет подсвечиваемого помощника **/
    ColorInputHelper,
    /** Цвет подсвечиваемой ошибки **/
    ColorInputError,
    /** Цвет верхней надписи при активации данного поля ввода **/
    ColorInputHintFocused,
    /** Цвет хинта вводимого текста **/
    ColorInputHint,
    /** Цвет вводимого текста **/
    ColorInputText,
    /** Цвет полоски под полем ввода **/
    ColorInputBottomLine,
    /** Цвет моргающего курсора **/
    ColorInputCursor,
    /** Цвет выделения текста **/
    ColorInputHighlight,
    /** Цвет захватов при выделении  текста **/
    ColorInputHandles,

    //ProgressBar
    /** Цвет прогрессбара **/
    ColorProgressBar,
    /** Цвет второго прогрессбара (автоматически затеняется) **/
    ColorProgressBarSecondary,
    /** Цвет фона прогрессбара **/
    ColorProgressBarBackground,
    //SeekBar
    /** Цвет ручки сикбара **/
    ColorSeekBarThumb,
    /** Цвет точек на сикбаре с делениями **/
    ColorSeekBarTickMark,

    //Button
    /** Цвет фона кнопки **/
    ColorButtonBackground,
    /** Цвет текста кнопки **/
    ColorButtonText,

    //Checkbox
    /** Цвет чекбокса **/
    ColorCheckBoxActive,
    /** Цвет неактивированного чекбокса **/
    ColorCheckBoxInactive,

    //RadioButton
    /** Цвет радиокнопки **/
    ColorRadioActive,
    /** Цвет неактивированной радиокнопки **/
    ColorRadioInactive,

    //Switch
    /** Цвет переключателя **/
    ColorSwitchActive,
    /** Цвет неактивированного переключателя **/
    ColorSwitchInactive,

    //FloatingActionButton
    /** Цвет фона fab кнопки **/
    ColorFabBackground,
    /** Цвет иконки на fab кнопке **/
    ColorFabIcon,

    //AlertDialog
    /** Цвет фона диалога **/
    ColorAlertBackground,
    /** Цвет заголовка диалога **/
    ColorAlertTitle,
    /** Цвет иконки диалога **/
    ColorAlertIcon,
    /** Цвет текста диалога **/
    ColorAlertMessage,
    /** Цвет кнопок  диалога **/
    ColorAlertButtons,
    /** Цвет позитивной кнопки далога **/
    ColorAlertButtonPositive,
    /** Цвет негативной кнопки далога **/
    ColorAlertButtonNegative,
    /** Цвет нейтральной кнопки далога **/
    ColorAlertButtonNeutral,

    //ImageView
    /** Цвет иконок **/
    ColorIcon,

    //ActionBar
    /** Цвет верхнего экшенбара **/
    ColorActionBar,
    /** Цвет текста верхнего экшенбара **/
    ColorActionBarText,
    /** Цвет второго текста верхнего экшенбара **/
    ColorActionBarTextSecondary,
    /** Цвет иконок верхнего экшенбара **/
    ColorActionBarIcons,

    //BottomNavigationView
    /** Цвет фона нижней панели **/
    ColorBottomNavBackground,
    /** Цвет иконок нижней панели **/
    ColorBottomNavIcon,
    /** Цвет иконки выбранной вкладки нижней панели **/
    ColorBottomNavIconSelected,
    /** Цвет текста нижней панели **/
    ColorBottomNavText,
    /** Цвет текста выбранной вкладки нижней панели **/
    ColorBottomNavTextSelected,

}