#!/usr/bin/env kotlin

@file:DependsOn("it.krzeminski:github-actions-kotlin-dsl:0.28.0")

import it.krzeminski.githubactions.actions.CustomAction
import it.krzeminski.githubactions.domain.RunnerType
import it.krzeminski.githubactions.domain.triggers.IssueComment
import it.krzeminski.githubactions.domain.triggers.WorkflowDispatch
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.toYaml
import java.io.File

val ownerAutoApproveAction = CustomAction(
    actionOwner = "bitfunk",
    actionName = "action-workflows/.github/workflows/owner-auto-approve.yaml",
    actionVersion = "main",
    inputs = linkedMapOf()
)

val flow = workflow(
    name = "CI - Auto approve",
    on = listOf(
        WorkflowDispatch(),
        IssueComment(_customArguments = mapOf(
            "types" to "created"
        )),
    ),
    sourceFile = __FILE__.toPath(),
) {
    job(
        id = "auto-approve",
        runsOn = RunnerType.UbuntuLatest
    ) {
        uses(
            name = "Approve owners review",
            action = ownerAutoApproveAction
        )
    }
}

println(flow.toYaml())

File("ci-auto-approve.yaml").writeText(flow.toYaml())
