# CleverNote
Projekt do předmětu TAMZII - MAC0441

Obecné:
Aplikace pro tvorbu vlastních poznámek, textové a zvukové poznámky, k poznámkám lze taky přidat obrázek, audio. Ukládání poznámek. Aplikace umožňuje načítání novinek z IGN.com pomocí News API.

Funkcionalita + Specifikace:

Pro zobrazení dat jsem použil - ListView, GridView.

Menu pomocí Draweru s animací - přepínání mezi Fragmenty.

Uložení barvy Draweru pomocí SharedPreferences - nutno potvrdit najetím do Settings.

Zvukové poznámky - Media Recorder, MediaPlayer.

Poznámky s obrázkem z galerie.

Ukládání poznámek pomocí SQLite.

Načítání a vypsání článků z IGN.com pomocí News API (JSON formát) + AsyncTask + intent na Browser pro detaily článku.
