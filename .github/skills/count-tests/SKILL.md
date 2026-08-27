---
name: count-tests
description: Count the number of tests in the current project
model: Claude Haiku 4.5 (copilot)
---

1. Run the script at `qa/.github/skills/count-tests/count-tests.sh`  using bash to count the number of tests in the
   current project.

2. The script will output the total number of tests found in the project,the number of tests that passed, and the number
   of component and integration tests.
3. Use the template found here [template](./Template.md) to format the output of the script and provide a summary of the
   test results.

```