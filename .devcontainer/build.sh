#!/bin/bash

# RideshareOdoMeter - Automated Build Script for Codespaces
# Run this script to automatically build the APK

set -e

echo "=========================================="
echo "🚀 RideshareOdoMeter Build Script"
echo "=========================================="
echo ""

# Step 1: Check Java
echo "✓ Checking Java..."
java -version
echo ""

# Step 2: Pull latest code
echo "✓ Pulling latest changes from GitHub..."
git pull origin main
echo ""

# Step 3: Make gradlew executable
echo "✓ Making gradlew executable..."
chmod +x gradlew
echo ""

# Step 4: Verify files exist
echo "✓ Verifying required files..."
ls -la gradlew
ls -la gradle/wrapper/gradle-wrapper.properties
echo ""

# Step 5: Check gradle version
echo "✓ Checking Gradle version..."
./gradlew --version
echo ""

# Step 6: Build APK
echo "=========================================="
echo "🔨 Building APK..."
echo "=========================================="
echo ""
./gradlew assembleDebug

echo ""
echo "=========================================="
echo "✅ BUILD COMPLETE!"
echo "=========================================="
echo ""
echo "APK Location:"
find . -name "app-debug.apk" -type f
echo ""
echo "Install on emulator:"
echo "  adb install $(find . -name 'app-debug.apk' -type f | head -1)"
echo ""
