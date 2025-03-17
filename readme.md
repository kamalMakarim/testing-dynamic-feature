## Explanation about the project

Dalam projek ini terdapat login activity yang menentukan feature yang diperlukan oleh user, jika dia admin maka akan mendownload admin_feature, sementara bila user biasa maka akan mendownload user_feature. untuk menjalankan backend yang digunakan dapat ke direktori /demo-backend lalu menjalankan perintah

```
npm install
npm run start
```

## How To Make A Dynamic Feature On Android Studio

1. Create a new module
   ![alt text](./md-image/createModule.png)
2. Pick Dynamic Feature Module
   ![alt text](./md-image/createDynamicFeature.png)
3. Create module title and pick install time
   ![alt text](./md-image/dynamicFeatureOptions.png)
   install time dapat diganti di AndroidManifest milik dynamic feature
   ![alt text](./md-image/on-demand-manifest.png)
   ![alt text](./md-image/instant-manifest.png)
4. Gunakan SplitInstallManager untuk menginstall modul dynamic feature, dapat dilihat di bagian berikut
   [Contoh Penggunaan SplitInstallManager](https://github.com/kamalMakarim/testing-dynamic-feature/blob/main/app/src/main/java/com/kamal/testingdynamicmodule/dynamic_module/DynamicModuleDownloadUtil.kt)

## Things To Look Out For

1. Ketika menggunakan dynamic feature, fitur dynamic tidak bisa di import langsung di app, perlu menggunakan reflection
2. Jika ingin menggunakan dynamic feature on-demand, pastikan aplikasi sudah di upload ke playstore
3. Jika terdapat asset yang diakses oleh dua atau lebih dynamic feature, disarankan untuk meletakkan asset tersebut di base module untuk menghindari duplikasi asset
4. Jika ingin mengakses asset dari dynamic feature, gunakan AssetManager untuk mengakses asset tersebut (Belum di uji)

## Internal Storage Used Comparison

| With Dynamic Feature | No Feature Downloaded | Admin Feature Downloaded | User Feature Downloaded | Both Features Downloaded | Download Size |
| -------------------- | --------------------- | ------------------------ | ----------------------- | ------------------------ | ------------- |
| False                | -                     | -                        | -                       | 8.27MB                   | 5.8MB         |
| True                 | 11.60MB               | 11.61MB                  | 11.61MB                 | 11.62MB                  | 9.9MB         |
