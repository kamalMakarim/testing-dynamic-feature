# List of questions

## Kalau misal implement library yang sama di beda module, ukuran membesar ?

## Bisa dipake kaya protected route ?

Seperti nya tidak

# 5/3/2025

## Instant Dynamic Feature vs Dynamic Feature

- Instant langsung ke download begitu download aplikasi nya
- Dynamic Feature enggak

## Pros and Cons of Each install time

### On demand only

**Pros:**

- Reduces initial app download size
- Users download features only when needed
- Can improve app performance by loading features dynamically

**Cons:**

- Requires internet connection to download features
- May lead to delays in accessing features
- Increased complexity in managing feature downloads

### Include module at install time

**Pros:**

- All features available immediately after installation
- No need for additional downloads
- Simplifies app architecture

**Cons:**

- Increases initial app download size
- May include features that users never use
- Can lead to higher storage usage on the device

### Only include at install time for device with specified feature

**Pros:**

- Optimizes app size based on device capabilities
- Ensures relevant features are available based on device specifications
- Can improve user experience by tailoring the app to the device

**Cons:**

- Requires careful management of device-specific features
- Potentially increases complexity in app development
- May lead to inconsistencies in app behavior across different devices

## AppCompactActivity vs ComponentActivity

- Component activity lebih cocok kalau butuh yg ringan dan fitur dipake sedikit
- AppCompactActivity buat backward compatibility dan lebih lengkap fitur

## Referensi

- https://medium.com/@hasan.cse91/mastering-android-dynamic-feature-module-delivery-1-3-3cf08afd1e42
- https://github.com/hasancse91/dynamic-feature-module-android
