package com.qazstudy.uikit

class WrongClassExtendedException(
    actual: String,
    expectation: String
) : IllegalStateException("$actual must extend $expectation")