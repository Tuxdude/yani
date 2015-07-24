/*  Copyright (C) 2015 Ash [Tuxdude] <tuxdude.io@gmail.com>
 *
 *  This file is part of yani.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tuxdude.yani.network;

public final class MacAddressInfo {
    public final String fullAddress;
    public final String vendor;
    public final String lowerAddress;

    public MacAddressInfo(String fullAddress, String vendor, String lowerAddress) {
        this.fullAddress = fullAddress;
        this.vendor = vendor;
        this.lowerAddress = lowerAddress;
    }
}