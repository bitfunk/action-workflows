#!/usr/bin/env kotlin

@file:DependsOn("it.krzeminski:github-actions-kotlin-dsl:0.35.0")
@file:Import("_shared.main.kts")

import it.krzeminski.githubactions.actions.CustomAction
import it.krzeminski.githubactions.actions.fullName
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
    yamlConsistencyJobCondition = yamlConsistencyConditionNever,
) {
    job(
        id = "auto-approve",
        runsOn = RunnerType.UbuntuLatest,
        _customArguments = mapOf(
            "uses" to ownerAutoApproveAction.fullName
        )
    ) {
        run(
            command = "REMOVE ME"
        )
    }
}

// workaround for not supported uses external job
val yaml = flow.toYaml()
    .replace("  auto-approve:\n" +
            "    runs-on: ubuntu-latest\n" +
            "    needs:\n" +
            "    - check_yaml_consistency\n" +
            "    steps:\n" +
            "    - id: step-0\n" +
            "      run: REMOVE ME", "  auto-approve:")

println(yaml)

File("ci-auto-approve.yaml").writeText(yaml + "\n")
