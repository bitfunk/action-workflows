#!/usr/bin/env kotlin

@file:DependsOn("it.krzeminski:github-actions-kotlin-dsl:0.28.0")

val yamlConsistencyCondition = "github.repository == 'bitfunk/action-workflows'"
val yamlConsistencyConditionNever = "github.repository == 'bitfunk/does-not-exist'"
