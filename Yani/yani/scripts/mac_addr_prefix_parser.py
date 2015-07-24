#! /usr/bin/env python
#
#   Copyright (C) 2015 Ash [Tuxdude] <tuxdude.io@gmail.com>
#
#   This file is part of yani.
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#
import re as _re
import sys as _sys
macAddrRegex = _re.compile(r'^(([0-9a-f]{2}:){2}[0-9a-f]{2})\s+([\w\-+.]+)\s*#*.*$')

if len(_sys.argv) != 2:
    print 'Invalid arguments'
    print 'Usage: ' + _sys.argv[0] + ' <file_to_parse>'
    _sys.exit(-1)

print '<?xml version="1.0" encoding="UTF-8"?>'
print '<!--'
print '    Copyright (C) 2015 Ash [Tuxdude] <tuxdude.io@gmail.com>'
print ''
print '    This file is part of yani.'
print ''
print '    Licensed under the Apache License, Version 2.0 (the "License");'
print '    you may not use this file except in compliance with the License.'
print '    You may obtain a copy of the License at'
print ''
print '        http://www.apache.org/licenses/LICENSE-2.0'
print ''
print '    Unless required by applicable law or agreed to in writing, software'
print '    distributed under the License is distributed on an "AS IS" BASIS,'
print '    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.'
print '    See the License for the specific language governing permissions and'
print '    limitations under the License.'
print '-->'
print '<map>'

# Open the input file passed as command line argument
with open(_sys.argv[1]) as inputFile:
    # Iterate over every line in the file
    for line in inputFile:
        line = line.strip().lower()
        # Ignore comments
        if not line.startswith('#'):
            matchObj = macAddrRegex.match(line)
            if matchObj:
                macAddrPrefix = matchObj.group(1)
                macAddrVendor = matchObj.group(3)
                print '    <entry key="' + macAddrPrefix + '" value="' + macAddrVendor + '" />'
            else:
                #print 'Did not match line: ' + line
                pass

print '</map>'
