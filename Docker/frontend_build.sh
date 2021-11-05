
cd Frontend/
cd GoalFriends/

export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export ANDROID_SDK_ROOT=/opt/android-sdk
export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/bin/

chmod +x gradlew

./gradlew build

cp app/build/outputs/apk/debug/app-debug.apk /artifacts