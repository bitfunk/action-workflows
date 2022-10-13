# Bitfunk Action Workflows

GitHub Action workflows for [reuse](https://docs.github.com/en/actions/using-workflows/reusing-workflows) written in Kotlin.

## Workflows

### Auto approve

This will approve a pull-request when the owner (wmontwe) comments with :+1:

```yaml
name: CI - Auto approve

on:
  issue_comment:
    types:
      - created
  
jobs:
  auto-approve:
    uses: bitfunk/action-workflows/.github/workflows/owner-auto-approve.yaml@main
```

## Develop

We use [GitHub Actions Kotlin DSL](https://github.com/krzema12/github-workflows-kt) to write our GitHub Action workflows.

Open the Kotlin scripts in IntelliJ IDEA and change them to your needs. Run them once to generate the corresponding yaml representation. Commit and push your changes.
