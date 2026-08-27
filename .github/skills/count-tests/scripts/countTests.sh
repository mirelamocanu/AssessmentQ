#!/usr/bin/env bash
#
# count-tests.sh
#
# Counts Cucumber tests in the account-adjustment QA project.
#
# Scans .feature files under the project (excluding target/ folders), counting
# each plain Scenario as one test and each Scenario Outline as one test per
# Examples data row (header and comment rows excluded). Reports TotalNo,
# NoOfComponentTests and NoOfIntegrationTests based on the feature's folder or
# its @COMPONENT_TEST / @INTEGRATION_TEST tag.
#
# Usage: ./count-tests.sh [ROOT_DIR] [-v]

set -euo pipefail

ROOT="${1:-$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)}"
VERBOSE="${2:-}"

#######################################
# Returns the number of executable tests contained in a single .feature file.
# Arguments:
#   $1 - absolute path to the .feature file
# Outputs:
#   test count on stdout
#######################################
count_feature_tests() {
  awk '
    {
      line = $0
      gsub(/^[ \t]+|[ \t]+$/, "", line)

      if (line == "" || line ~ /^#/) next

      if (line ~ /^Scenario Outline:/ || line ~ /^Scenario Template:/) {
        inExamples = 0; headerSeen = 0; next
      }
      if (line ~ /^Scenario:/ || line ~ /^Example:/) {
        inExamples = 0; headerSeen = 0; count++; next
      }
      if (line ~ /^Examples:/ || line ~ /^Scenarios:/) {
        inExamples = 1; headerSeen = 0; next
      }
      if (inExamples && line ~ /^\|/) {
        if (!headerSeen) headerSeen = 1; else count++
        next
      }
      if (inExamples) { inExamples = 0; headerSeen = 0 }
    }
    END { print count + 0 }
  ' "$1"
}

component=0
integration=0
other=0

while IFS= read -r -d '' file; do
  tests=$(count_feature_tests "$file")

  if [[ "$file" == */features/component/* ]] || grep -q "@COMPONENT_TEST" "$file"; then
    component=$((component + tests))
  elif [[ "$file" == */features/integration/* ]] || grep -q "@INTEGRATION_TEST" "$file"; then
    integration=$((integration + tests))
  else
    other=$((other + tests))
  fi

  if [[ "$VERBOSE" == "-v" ]]; then
    printf '%s: %s\n' "$file" "$tests"
  fi
done < <(find "$ROOT" -type f -name '*.feature' -not -path '*/target/*' -print0)

total=$((component + integration + other))

printf 'TotalNo              : %d\n' "$total"
printf 'NoOfComponentTests   : %d\n' "$component"
printf 'NoOfIntegrationTests : %d\n' "$integration"