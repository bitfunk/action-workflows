#!/usr/bin/env kotlin

@file:DependsOn("it.krzeminski:github-actions-kotlin-dsl:0.40.0")
@file:Import("_shared.main.kts")

import it.krzeminski.githubactions.actions.actions.GithubScriptV6
import it.krzeminski.githubactions.domain.RunnerType.UbuntuLatest
import it.krzeminski.githubactions.domain.triggers.WorkflowCall
import it.krzeminski.githubactions.dsl.workflow
import it.krzeminski.githubactions.yaml.toYaml
import java.io.File

val flow = workflow(
    name = "Owner auto approve",
    on = listOf(WorkflowCall()),
    sourceFile = __FILE__.toPath(),
    yamlConsistencyJobCondition = yamlConsistencyCondition,
) {
    job(
        id = "owner-auto-approve",
        runsOn = UbuntuLatest,
        _customArguments = mapOf(
          "permissions" to mapOf(
              "pull-requests" to "write"
          )
        ),
    ) {
        uses(
            name = "Approve owners review",
            condition = "github.actor == 'wmontwe' &&  contains(github.event.comment.body, 'LGTM')",
            action = GithubScriptV6(
                script = """
                    github.rest.pulls.createReview({
                        owner: context.repo.owner,
                        repo: context.repo.repo,
                        pull_number: context.issue.number,
                        review_id: 1,
                        event: 'APPROVE',
                        body: 'This is ready to go.'
                    })
                """.trimIndent()
            )
        )
    }
}

println(flow.toYaml())

File("owner-auto-approve.yaml").writeText(flow.toYaml()  + "\n")
