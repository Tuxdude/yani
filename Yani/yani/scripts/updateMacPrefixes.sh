#!/usr/bin/env sh

WIRESHARK_MANUF_DB_URL="https://code.wireshark.org/review/gitweb?p=wireshark.git;a=blob_plain;f=manuf"
WIRESHARK_MANUF_DB_FILE="./wireshark-manuf"

# Download the latest database
curl "$WIRESHARK_MANUF_DB_URL" -o "$WIRESHARK_MANUF_DB_FILE"

# Collect all the Vendor MAC address prefix mapping into an XML file
./mac_addr_prefix_parser.py ./wireshark-manuf > ../src/main/res/xml/mac_address_map.xml
