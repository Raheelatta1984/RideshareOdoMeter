#!/bin/bash
# RideshareOdoMeter: Android SDK Setup Script for Codespaces
# No GUI required - Pure CLI installation
# Sydney Time Compliance: ATO Logbook Framework

set -e

echo "==========================================" 
echo "🛡️  SWARM MISSION: Android SDK Setup"
echo "==========================================" 
echo "Environment: GitHub Codespaces (Linux)"
echo "Time Zone: Sydney (AEST)"
echo ""

# Step 1: Create Android home directory
export ANDROID_HOME="${HOME}/android-sdk"
export ANDROID_SDK_ROOT="${ANDROID_HOME}"
export PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/tools/bin:${PATH}"

mkdir -p ${ANDROID_HOME}/cmdline-tools
cd ${ANDROID_HOME}/cmdline-tools

echo "📥 Downloading Android Command Line Tools (12.0)..."
wget -q https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O cmdline-tools.zip

echo "📦 Extracting Command Line Tools..."
unzip -q cmdline-tools.zip
rm cmdline-tools.zip

# Rename extracted directory to 'latest' (required by sdkmanager)
mv cmdline-tools latest

echo "✅ Command Line Tools installed at ${ANDROID_HOME}/cmdline-tools/latest"

# Step 2: Accept all SDK licenses
echo ""
echo "📜 Accepting Android SDK Licenses..."
mkdir -p ${ANDROID_HOME}/licenses
echo "24333f8a63b6825ea9c5514f83c2829b004d1fee" > ${ANDROID_HOME}/licenses/android-sdk-license
echo "84831b9409646a918e30573bab4c9c91346d8abd" > ${ANDROID_HOME}/licenses/android-sdk-preview-license
echo "d56f5187479451eabf01fb78af6dfcb131b33910" > ${ANDROID_HOME}/licenses/google-play-services-license
echo "33b6a2b64607f11454d1a00d0ee5a1c2130021ee" > ${ANDROID_HOME}/licenses/gpl-3-license

# Step 3: Install SDK platforms, build tools, and platform tools
echo ""
echo "🔨 Installing Android SDK Platform 34..."
sdkmanager --sdk_root=${ANDROID_HOME} "platforms;android-34" > /dev/null 2>&1

echo "🔨 Installing Android Build Tools 34.0.0..."
sdkmanager --sdk_root=${ANDROID_HOME} "build-tools;34.0.0" > /dev/null 2>&1

echo "🔨 Installing Android Platform Tools..."
sdkmanager --sdk_root=${ANDROID_HOME} "platform-tools" > /dev/null 2>&1

echo "🔨 Installing Android Emulator (optional, for CI/CD later)..."
sdkmanager --sdk_root=${ANDROID_HOME} "emulator" > /dev/null 2>&1

# Step 4: Verify installation
echo ""
echo "✅ Verifying Android SDK Installation..."
echo "Android SDK Root: ${ANDROID_HOME}"
echo "SDK Manager Version:"
sdkmanager --version

# Step 5: Set environment variables for current session and future sessions
echo ""
echo "📝 Setting up environment variables..."
cat >> ~/.bashrc << 'EOF'

# Android SDK Configuration (RideshareOdoMeter)
export ANDROID_HOME="${HOME}/android-sdk"
export ANDROID_SDK_ROOT="${ANDROID_HOME}"
export PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/tools/bin:${PATH}"
EOF

# Also set for current session
source ~/.bashrc

echo ""
echo "==========================================" 
echo "✅ ANDROID SDK SETUP COMPLETE"
echo "==========================================" 
echo ""
echo "Environment Variables Set:"
echo "  ANDROID_HOME: ${ANDROID_HOME}"
echo "  PATH: Updated with SDK tools"
echo ""
echo "Installed Components:"
echo "  ✓ Android SDK Platform 34"
echo "  ✓ Build Tools 34.0.0"
echo "  ✓ Platform Tools (adb, fastboot)"
echo "  ✓ Android Emulator"
echo ""
echo "Next Step: Run './gradlew assembleDebug' to build APK"
echo "==========================================" 