---
name: review-current-files
description: Review the current files
model: Claude Haiku 4.5 (copilot)
tool: code-review
---

Review the current files and provide feedback on their content, structure, and overall quality. Identify any areas that
need improvement or clarification.

Requirements:

- Use `.github/copilot-instructions.md` as the guide for the review process.
- Apply other well-established review best practices (naming, readability, structure, duplication, error handling,
  testability).
- Do NOT examine other files in the repository — only the currently open/attached files.

Success criteria:

- Findings are specific, reference the exact file and line/section, and explain why each is a problem.
- Findings are grouped by severity (blocking / recommended / optional).
- End the review with a question asking which findings to implement.

