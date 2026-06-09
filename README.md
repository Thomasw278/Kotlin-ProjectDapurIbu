# Kotlin-ProjectDapurIbu

<p align="center">
  <img src="app/src/main/res/drawable/dapuribu.png" width="200px" alt="Dapur Ibu Logo">
</p>

### Nama Anggota
```bash
71230975 - Thomas Aquinas Ryan Wisnu Adi
```

### Judul Project 
```bash
Dapur Ibu : Katalog Resep & Konsultasi Memasak
```

### 📁 Struktur Folder
```bash
DapurIbuProject
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java/com/example/dapuribuproject/
│   │   │   │   ├── Adapter/                   # Adapter untuk RecyclerView
│   │   │   │   │   ├── ChatAdapter.kt
│   │   │   │   │   └── KatalogAdapter.kt
│   │   │   │   ├── Api/                       # Konfigurasi Network & API
│   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   └── NetworkModule.kt
│   │   │   │   ├── DataClass/                 # Model Data (POJO/Entity)
│   │   │   │   │   ├── ChatMessage.kt
│   │   │   │   │   ├── Katalog.kt
│   │   │   │   │   ├── Post.kt
│   │   │   │   │   └── User.kt
│   │   │   │   ├── Fragment/                  # Fragment UI Halaman
│   │   │   │   │   ├── ChatFragment.kt
│   │   │   │   │   ├── Home_Admin_Fragment.kt
│   │   │   │   │   ├── Home_Fragment.kt
│   │   │   │   │   ├── Katalog_Fragment.kt
│   │   │   │   │   └── Profile_Fragment.kt
│   │   │   │   ├── Helper/                    # Database & Utilities (SQLite)
│   │   │   │   │   └── DatabaseHelper.kt
│   │   │   │   ├── LoginRegis/                # Fitur Autentikasi
│   │   │   │   │   ├── GantiPasswordActivity.kt
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   └── RegistrasiActivity.kt
│   │   │   │   ├── ui/theme/                  # Konfigurasi Tema (Compose)
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   ├── AddKatalogActivity.kt      # Form Tambah Data
│   │   │   │   ├── DapurIbuApplication.kt     # Hilt Application Class
│   │   │   │   ├── DetailKatalogActivity.kt   # Detail Resep
│   │   │   │   ├── MainActivity.kt            # Entry Point Utama
│   │   │   │   └── PostUtil.kt                # Utility class
│   │   │   └── res/
│   │   │       ├── drawable/                  # Aset Gambar & Icon
│   │   │       ├── layout/                    # Desain UI (XML)
│   │   │       ├── values/                    # Strings, Colors, Styles
│   │   │       └── xml/                       # Konfigurasi XML lainnya
│   │   ├── test/java/com/example/dapuribuproject/ # Unit Tests
│   │   │   ├── ExampleUnitTest.kt
│   │   │   └── PostUtilTest.kt
│   │   └── androidTest/java/com/example/dapuribuproject/ # Instrumented Tests (UI)
│   │       ├── AuthTest.kt
│   │       ├── ExampleInstrumentedTest.kt
│   │       ├── FeatureTest.kt
│   │       └── MainActivityTest.kt
│   ├── build.gradle.kts                       # App-level dependencies
│   └── AndroidManifest.xml                    # App Manifest
├── build.gradle.kts                           # Project-level settings
└── settings.gradle.kts
```
