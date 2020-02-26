# Theming
Библиотека для изменения цветов различных компонентов приложения программно

## Использование

1) Переопределить основные используемые цвета, например:
```
object Theming : ThemingBase(){
    override val colorPrimary: Int get() = Color.parseColor("#246E31")
    override val colorBackground: Int get() = Color.parseColor("#FAFAFA")
}
```

2) Использовать раскрашивание в приложении, например:
```
Theming.themeActivity(this) //перекрашивает системные компоненты активити: статусбар, навбар, оверскролл
Theming.themeViews(button1, toolbar, view) //Раскрашивает вью по их типу
Theming.themeViewBack(itemView) //Раскрасить только фон
Theming.themeTextView(title, isSecondary = true) //раскрасить текствью как secondary текст
```
И др...
