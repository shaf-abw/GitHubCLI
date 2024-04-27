**GitHub CLI for Pull Requests and Merge**


This Groovy-based CLI application interacts with the GitHub API to create and manage pull requests and retrieve issues for a specific repository.

**Prerequisites**

Before using this CLI application, ensure you have:

**Groovy:** 
Install Groovy on your system. You can download it here.
1) GitHub Account: You'll need a GitHub account to generate a personal access token.
2) Personal Access Token: Generate a personal access token from GitHub with appropriate permissions to interact with repositories. Set this token as an environment variable named ACCESS_TOKEN.

**Installation**

1) Clone this repository to your local machine: git clone https://github.com/your-username/github-cli.git

2) Navigate to the project directory: cd github-cli

**Usage**

**Creating a Pull Request**

**To create a pull request:**

groovy github_cli.groovy create-pr <repoName> "PR Title" "PR Body" <username:headBranch> <baseBranch>

**Example:**

groovy github_cli.groovy create-pr myrepo "New Feature" "This pull request adds a new feature" shaf:feature-branch main

**Merging a Pull Request**

**To merge a pull request:**

groovy github_cli.groovy merge-pr repoName pullRequestNumber

**Example:**

groovy github_cli.groovy merge-pr myrepo 123

**Commands**
  1) create-pr: Creates a pull request for the specified repository.
  2) merge-pr: Merges a pull request by its number.
  
**Troubleshooting**

If you encounter any issues or errors while using this CLI application, ensure that:

Your GitHub personal access token (**ACCESS_TOKEN**) is correctly set as an environment variable.
The provided repository name, branch names, and pull request details are accurate and accessible.

**License**

This project is open-source and available under the MIT License.

You are free to use, modify, and distribute this code for personal and commercial purposes. However, please note that this project comes with no warranties or support. By using this code, you agree to the terms and conditions of the MIT License.

See the LICENSE file for more details.
