# Bulk Scan CCD event handler

[![Build Status](https://travis-ci.org/hmcts/bulk-scan-ccd-event-handler-lib.svg?branch=master)](https://travis-ci.org/hmcts/bulk-scan-ccd-event-handler-lib)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d17f0de26d1c4f339dd58bfee53c7fc8)](https://www.codacy.com/app/HMCTS/bulk-scan-ccd-event-handler-lib)
[![codecov](https://codecov.io/gh/hmcts/bulk-scan-ccd-event-handler-lib/branch/master/graph/badge.svg)](https://codecov.io/gh/hmcts/bulk-scan-ccd-event-handler-lib)
[![Download](https://api.bintray.com/packages/hmcts/hmcts-maven/bulk-scan-ccd-event-handler/images/download.svg) ](https://bintray.com/hmcts/hmcts-maven/bulk-scan-ccd-event-handler/_latestVersion)

This library will be used to handle CCD events related to creating a case from an bulk-scan exception record.

## Developing

### Prerequisites
- [JDK 8](https://java.com)

### Unit tests
To run all unit tests execute the following command:
```bash
./gradlew test
```

### Code quality checks
We use [checkstyle](http://checkstyle.sourceforge.net/) and [PMD](https://pmd.github.io/).  
To run all checks execute the following command:
```bash
./lint.sh
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
