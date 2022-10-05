# Bitfunk Action Workflows

GitHub Action workflows for [reuse](https://docs.github.com/en/actions/using-workflows/reusing-workflows).

## Auto approve

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
