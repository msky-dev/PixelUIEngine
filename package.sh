if [ $# -eq 0 ]
  then
    echo "package.sh <version>"
	exit
fi

#COMMON
export APPNAME="PixelUI"
export SEVEN_ZIP_PATH="./buildtools/7z.exe"
export RCEDIT_PATH="./buildtools/rcedit-x64.exe"
export JAR_NAME="pixeluiengine-desktop-1.0-jar-with-dependencies.jar"
export JAR_PATH="./desktop/target/${JAR_NAME}"
export MAIN_CLASS="net.mslivo.example.ExampleLauncherMain"
export ADDITIONAL_FILES=(
)

#WINDOWS_X64
export WIN_X64_ENABLED="true"
export WIN_X64_ROAST_BINARY="./buildtools/roast/roast-win-x86_64.exe"
export WIN_X64_TEMP_DIR="temp/out_win_x64"
export WIN_X64_JRE="C:/Program Files/Java/windows_x64/jdk-25.0.2+10-jre" 
export WIN_X64_ICON="./buildtools/appicon.ico"
export WIN_X64_FILE_SUFFIX="win_x64"

#LINUX_X64
export LINUX_X64_ENABLED="true"
export LINUX_X64_ROAST_BINARY="./buildtools/roast/roast-linux-x86_64"
export LINUX_X64_TEMP_DIR="temp/out_linux_x64"
export LINUX_X64_JRE="C:/Program Files/Java/linux_x64/jdk-25.0.2+10-jre" 
export LINUX_X64_FILE_SUFFIX="linux_x64"

#MACOS_X64_AARCH64
export MAC_X64_AARCH64_ENABLED="true"
export MAC_X64_AARCH64_ROAST_BINARY="./buildtools/roast/roast-macos-universal"
export MAC_X64_AARCH64_TEMP_DIR="temp/out_mac_x64_aarch64"
export MAC_X64_JRE="C:/Program Files/Java/macos_x64/jdk-25.0.2+10-jre" 
export MAC_AARCH64_JRE="C:/Program Files/Java/macos_aarch64/jdk-25.0.2+10-jre" 
export MAC_X64_AARCH64_FILE_SUFFIX="mac_x64_aarch64"
export MAC_X64_AARCH64_ICON="./buildtools/appicon.icns"

# Init
export APPNAME_SAFE="${APPNAME// /_}"
rm -f -r "./temp"

# ############################ WINDOWS X64 ############################

if [ "$WIN_X64_ENABLED" = "true" ]; then
    echo "~~~~~~ Packaging WINDOWS_X64 ~~~~~~"
	rm -f "${APPNAME_SAFE}_${1}_${WIN_X64_FILE_SUFFIX}.zip"
	mkdir -p "${WIN_X64_TEMP_DIR}"
	cd ${WIN_X64_TEMP_DIR}
	# copy runtime
	mkdir -p ./runtime
    echo "Copying windows x64 runtime"
	cp -r "${WIN_X64_JRE}/." "./runtime"
	# copy binary
	cp "../../${WIN_X64_ROAST_BINARY}" "./${APPNAME}.exe"
	# set .exe icon
	echo "setting exe icon"
	"../../${RCEDIT_PATH}" "${APPNAME}.exe" --set-icon "../../${WIN_X64_ICON}"
	# copy jar
	cp "../../${JAR_PATH}" "./${JAR_NAME}"
	# copy additional files
    for FILE in "${ADDITIONAL_FILES[@]}"; do
      cp "../../${FILE}" "./${FILE}"
    done
	# create json
	mkdir "./app"
	cat > "./app/${APPNAME}.json" << EOF
{
  "classPath": [
    "${JAR_NAME}"
  ],
  "mainClass": "${MAIN_CLASS}",
  "vmArgs": [
    "--add-modules=jdk.incubator.vector"
  ],
  "args":[],
  "useMainAsContextClassLoader": false,
  "useZgcIfSupportedOs": true,
  "runOnFirstThread": false
}
EOF
	#Create archive
	"../../${SEVEN_ZIP_PATH}" "a" "-tzip" "-r" "${APPNAME_SAFE}.zip" .
	mv "./${APPNAME_SAFE}.zip" "./../../${APPNAME_SAFE}_${1}_${WIN_X64_FILE_SUFFIX}.zip"
	cd ../..
else
    echo "WINDOWS_X64 skipped"
fi

# ############################ LINUX X64 ############################

if [ "$LINUX_X64_ENABLED" = "true" ]; then
    echo "~~~~~~ Packaging LINUX_X64 ~~~~~~"
	rm -f "${APPNAME_SAFE}_${1}_${LINUX_X64_FILE_SUFFIX}.tar.gz"
	mkdir -p "${LINUX_X64_TEMP_DIR}"
	cd ${LINUX_X64_TEMP_DIR}
	# copy runtime
	mkdir -p ./runtime
    echo "Copying linux x64 runtime"
	cp -r "${LINUX_X64_JRE}/." "./runtime"
	# copy binary
	cp "../../${LINUX_X64_ROAST_BINARY}" "./${APPNAME}.exe"
	# copy jar
	cp "../../${JAR_PATH}" "./${JAR_NAME}"
	# copy additional files
    for FILE in "${ADDITIONAL_FILES[@]}"; do
      cp "../../${FILE}" "./${FILE}"
    done
	# create json
	mkdir "./app"
	cat > "./app/${APPNAME}.json" << EOF
{
  "classPath": [
    "${JAR_NAME}"
  ],
  "mainClass": "${MAIN_CLASS}",
  "vmArgs": [
    "--add-modules=jdk.incubator.vector"
  ],
  "args":[],
  "useMainAsContextClassLoader": false,
  "useZgcIfSupportedOs": true,
  "runOnFirstThread": false
}
EOF
	#Create archive
	"../../${SEVEN_ZIP_PATH}" "a" "-ttar" "-r" "${APPNAME_SAFE}.tar" .
	"../../${SEVEN_ZIP_PATH}" "a" "-tgzip" "-r" "${APPNAME_SAFE}.tar.gz" "${APPNAME_SAFE}.tar"
	mv "./${APPNAME_SAFE}.tar.gz" "./../../${APPNAME_SAFE}_${1}_${LINUX_X64_FILE_SUFFIX}.tar.gz"
		cd ../..
else
    echo "LINUX_X64 skipped"
fi


############################ MAC X64 AARCH64 ############################

if [ "$MAC_X64_AARCH64_ENABLED" = "true" ]; then
    echo "~~~~~~ Packaging MAC X64 AARCH64 ~~~~~~"

    rm -f "${APPNAME_SAFE}_${1}_${MAC_X64_AARCH64_FILE_SUFFIX}.tar.gz"

    mkdir -p "${MAC_X64_AARCH64_TEMP_DIR}"
    cd "${MAC_X64_AARCH64_TEMP_DIR}"

    #############################################
    # APP STRUCTURE
    #############################################
    MACOS_DIR="./${APPNAME}.app/Contents/MacOS"
    RESOURCE_DIR="./${APPNAME}.app/Contents/Resources"
    mkdir -p "${MACOS_DIR}"
    mkdir -p "${RESOURCE_DIR}"

    #############################################
    # UNIVERSAL BINARY
    #############################################
    cp "../../${MAC_X64_AARCH64_ROAST_BINARY}" "${RESOURCE_DIR}/${APPNAME}"
    chmod +x "${RESOURCE_DIR}/${APPNAME}"

    #############################################
    # RUNTIMES (CRITICAL NAMES)
    #############################################
    echo "Copying macOS runtimes"

    mkdir -p "${RESOURCE_DIR}/runtime-x64"
	cp -r "${MAC_X64_JRE}/Contents/Home/." "${RESOURCE_DIR}/runtime-x64"

    mkdir -p "${RESOURCE_DIR}/runtime-aarch64"
    cp -r "${MAC_AARCH64_JRE}/Contents/Home/." "${RESOURCE_DIR}/runtime-aarch64"

    #############################################
    # APP (CONFIG + JAR)
    #############################################
    mkdir -p "${RESOURCE_DIR}/app"

    cp "../../${JAR_PATH}" "${RESOURCE_DIR}/${JAR_NAME}"

    for FILE in "${ADDITIONAL_FILES[@]}"; do
        cp "../../${FILE}" "${RESOURCE_DIR}/${FILE}"
    done

    #############################################
    # CONFIG (MUST MATCH EXECUTABLE NAME)
    #############################################
    cat > "${RESOURCE_DIR}/app/${APPNAME}.json" << EOF
{
  "classPath": [
    "${JAR_NAME}"
  ],
  "mainClass": "${MAIN_CLASS}",
  "vmArgs": [
    "--add-modules=jdk.incubator.vector"
  ],
  "args":[],
  "useMainAsContextClassLoader": false,
  "useZgcIfSupportedOs": true,
  "runOnFirstThread": true
}
EOF

	#############################################
    # Icon
    #############################################
	
	cp "../../${MAC_X64_AARCH64_ICON}" "${RESOURCE_DIR}/${APPNAME}.icns"
	
    #############################################
    # Info.plist
    #############################################
    PLIST_DIR="./${APPNAME}.app/Contents"
    mkdir -p "${PLIST_DIR}"

    cat > "${PLIST_DIR}/Info.plist" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple Computer//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
  <key>CFBundleName</key>
  <string>${APPNAME}</string>
  <key>CFBundleGetInfoString</key>
  <string>${APPNAME}</string>
  <key>CFBundleExecutable</key>
  <string>launch.sh</string>
  <key>CFBundleIconFile</key>
  <string>${APPNAME}</string>
</dict>
</plist>

EOF

    #############################################
    # Launch script
    #############################################

cat > "${MACOS_DIR}/launch.sh" << EOF
#!/bin/bash

MACOS_DIR="\$(cd "\$(dirname "\$0")" && pwd)"
RES_DIR="\$MACOS_DIR/../Resources"

cd "\$RES_DIR"

# execute permissions
chmod +x "./${APPNAME}"
chmod +x "./runtime-aarch64/bin/java"
chmod +x "./runtime-x64/bin/java"

exec "./${APPNAME}" "\$@"
EOF

    #############################################
    # PACKAGE
    #############################################
    "../../${SEVEN_ZIP_PATH}" "a" "-ttar" "${APPNAME_SAFE}.tar" "./${APPNAME}.app"
    "../../${SEVEN_ZIP_PATH}" "a" "-tgzip" "${APPNAME_SAFE}.tar.gz" "${APPNAME_SAFE}.tar"

    mv "./${APPNAME_SAFE}.tar.gz" \
       "./../../${APPNAME_SAFE}_${1}_${MAC_X64_AARCH64_FILE_SUFFIX}.tar.gz"

    cd ../..

else
    echo "MAC_X64_AARCH64 skipped"
fi

# CLEANUP
rm -f -r "./temp"