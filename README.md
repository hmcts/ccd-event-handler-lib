# Bulk Scan CCD event handler

[![Build Status](https://travis-ci.org/hmcts/bulk-scan-ccd-event-handler-lib.svg?branch=master)](https://travis-ci.org/hmcts/bulk-scan-ccd-event-handler-lib)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d17f0de26d1c4f339dd58bfee53c7fc8)](https://www.codacy.com/app/HMCTS/bulk-scan-ccd-event-handler-lib)
[![codecov](https://codecov.io/gh/hmcts/bulk-scan-ccd-event-handler-lib/branch/master/graph/badge.svg)](https://codecov.io/gh/hmcts/bulk-scan-ccd-event-handler-lib)
[![Download](https://api.bintray.com/packages/hmcts/hmcts-maven/bulk-scan-ccd-event-handler/images/download.svg) ](https://bintray.com/hmcts/hmcts-maven/bulk-scan-ccd-event-handler/_latestVersion)

This library will be used to handle CCD events related to creating a case from an bulk-scan exception record.

## Usage

1. Create an instance of case transformer by implementing
[`ExceptionRecordToCaseTransformer`](https://github.com/hmcts/bulk-scan-ccd-event-handler-lib/blob/master/src/main/java/uk/gov/hmcts/reform/bulkscanccdeventhandler/transformer/ExceptionRecordToCaseTransformer.java)
interface.

2. Create an instance of a handler:
   ```java
   ExceptionRecordEventHandler handler = 
       ExceptionRecordEventHandlerFactory.getHandler(
           transformer,
           ccdUrl,
           s2sTokenSupplier
       );
   ```
   where `s2sTokenSupplier` is an function returning s2s token for your service.

3. Call the handler by passing [`CaseCreationRequest`](https://github.com/hmcts/bulk-scan-ccd-event-handler-lib/blob/master/src/main/java/uk/gov/hmcts/reform/bulkscanccdeventhandler/handler/model/CaseCreationRequest.java)
to it:
   ```java
   CaseCreationResult result = handler.handle(careCreationRequest);
   ``` 

The handler will create a case in CCD for you and return the ID of the new case.

You can check a sample implementation in
[this](https://github.com/hmcts/bulk-scan-ccd-event-handler-sample-app) repository.

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
