# Kotlin-ProjectDapurIbu

<p align="center">
  <img src="app/src/main/res/drawable/dapuribu.png" width="350px" alt="Dapur Ibu Logo">
</p>

### Author Name
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
│   │   │   │   ├── adapter/                   # Adapter untuk RecyclerView
│   │   │   │   │   ├── ChatAdapter.kt
│   │   │   │   │   └── KatalogAdapter.kt
│   │   │   │   ├── api/                       # Konfigurasi Network & API
│   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   └── NetworkModule.kt
│   │   │   │   ├── dataclass/                 # Model Data (POJO/Entity)
│   │   │   │   │   ├── ChatMessage.kt
│   │   │   │   │   ├── Katalog.kt
│   │   │   │   │   ├── Post.kt
│   │   │   │   │   └── User.kt
│   │   │   │   ├── fragment/                  # Fragment UI Halaman
│   │   │   │   │   ├── ChatFragment.kt
│   │   │   │   │   ├── Home_Admin_Fragment.kt
│   │   │   │   │   ├── Home_Fragment.kt
│   │   │   │   │   ├── Katalog_Fragment.kt
│   │   │   │   │   └── Profile_Fragment.kt
│   │   │   │   ├── helper/                    # Database & Utilities (SQLite)
│   │   │   │   │   └── DatabaseHelper.kt
│   │   │   │   ├── loginregis/                # Fitur Autentikasi
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
│   │   │       ├── dokumentasi/               # Foto Dokumentasi
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

### Foto Dokumentasi
Login & Registrasi & Ganti Password

<p align="center">
  <img src="app/src/main/res/dokumentasi/login.png" width="350px" alt="Login">
  <img src="app/src/main/res/dokumentasi/regis.png" width="350px" alt="Registrasi">
  <img src="app/src/main/res/dokumentasi/gantipass.png" width="350px" alt="Ganti Password">
</p>
