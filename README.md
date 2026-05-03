# Kotlin-ProjectDapurIbu

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
│   │   │   ├── java/com/example/dapuribuproject/    # Source Code Utama (Logic Aplikasi)
│   │   │   │   ├── fragment/                       # Fragment untuk UI per Halaman
│   │   │   │   │   ├── Home_Fragment.kt            # Dashboard utama user
│   │   │   │   │   ├── Home_Admin_Fragment.kt      # Dashboard khusus admin
│   │   │   │   │   ├── Katalog_Fragment.kt         # Halaman daftar resep/katalog
│   │   │   │   │   ├── ChatFragment.kt             # Halaman fitur chat
│   │   │   │   │   └── Profile_Fragment.kt         # Halaman profil user
│   │   │   │   ├── Helper/                         # Utility & Database
│   │   │   │   │   └── DatabaseHelper.kt           # Manajemen database lokal (SQLite)
│   │   │   │   ├── Adapter/                        # Adapter untuk List (RecyclerView)
│   │   │   │   │   ├── KatalogAdapter.kt           # Adapter untuk list katalog resep
│   │   │   │   │   └── ChatAdapter.kt              # Adapter untuk tampilan chat
│   │   │   │   ├── DataClass/                      # Model Data (Entity)
│   │   │   │   │   ├── Katalog.kt                  # Model data katalog
│   │   │   │   │   ├── User.kt                     # Model data user
│   │   │   │   │   └── ChatMessage.kt              # Model data pesan chat
│   │   │   │   ├── ApiService.kt                   # Interface Koneksi API (Retrofit)
│   │   │   │   ├── MainActivity.kt                 # Entry point & Navigasi Utama
│   │   │   │   ├── LoginActivity.kt                # Logic Autentikasi Login
│   │   │   │   ├── RegistrasiActivity.kt           # Logic Pendaftaran User
│   │   │   │   └── AddKatalogActivity.kt           # Form Tambah Katalog (Admin)
│   │   │   └── res/                                # Resources (UI Design)
│   │   │       ├── layout/                         # File Desain UI (XML)
│   │   │       ├── drawable/                       # Gambar & Aset Visual
│   │   │       └── values/                         # Konfigurasi Warna, String & Tema
│   ├── build.gradle.kts                            # Konfigurasi Dependensi (Library)
│   └── AndroidManifest.xml                         # Manifest Aplikasi (Permission & Activity)
├── build.gradle.kts                                # Konfigurasi Project-level
└── settings.gradle.kts
```
