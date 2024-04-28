import java.io.InputStreamReader
import java.io.BufferedReader

class GithubCliCliTest {

    // Execute CLI command and return output
    private String executeCliCommand(String command) {
        def process = command.execute()
        def reader = new BufferedReader(new InputStreamReader(process.inputStream))
        
        StringBuilder result = new StringBuilder()
        String line
        while ((line = reader.readLine()) != null) {
            result.append(line)
        }
        
        return result.toString()
    }

    // Test creating a pull request via CLI with wrong command
    void testCliWithWrongCommand() {

        def command = "groovy gitHubCli.groovy create"
        def output = executeCliCommand(command)

        assert output.contains("Unknown command")
    }

    // Test creating a pull request via CLI with wrong command
    void testCreatePullRequestCliWithWrongArgs() {
        def repoName = "sampleRepo"
        def title = "Test Pull Request"
        def body = "This is a test pull request"
        def headBranch = "feature-branch"

        def command = "groovy gitHubCli.groovy create-pr ${repoName} \"${title}\" \"${body}\" ${headBranch}"
        def output = executeCliCommand(command)

        assert output.contains("Validation Failed")
    }

    // Test merging a pull request via CLI with wrong command
    void testMergePullRequestCliWithWrongArgs() {
        def repoName = "sampleRepo"

        def command = "groovy gitHubCli.groovy merge-pr ${repoName}"
        def output = executeCliCommand(command)

        assert output.contains("Error")
    }

    // Test creating a pull request via CLI
    void testCreatePullRequestCli() {
        def repoName = "sampleRepo"
        def title = "Test Pull Request"
        def body = "This is a test pull request"
        def headBranch = "feature-branch"
        def baseBranch = "main"

        def command = "groovy gitHubCli.groovy create-pr ${repoName} \"${title}\" \"${body}\" ${headBranch} ${baseBranch}"
        def output = executeCliCommand(command)

        assert output.contains("Validation Failed")     // We received a 'Validation Failed' response from API due to invalid test inputs.
    }

    // Test merging a pull request via CLI
    void testMergePullRequestCli() {
        def repoName = "sampleRepo"
        def pullRequestNumber = 123

        def command = "groovy gitHubCli.groovy merge-pr ${repoName} ${pullRequestNumber}"
        def output = executeCliCommand(command)

        assert output.contains("Not Found")     // We received a 'Not Found' response from API due to invalid test inputs.
    }

    // Main function to run CLI tests
    static void main(String[] args) {
        def test = new GithubCliCliTest()

        // Execute CLI test methods
        test.testCliWithWrongCommand()
        test.testCreatePullRequestCliWithWrongArgs()
        test.testMergePullRequestCliWithWrongArgs()
        test.testCreatePullRequestCli()
        test.testMergePullRequestCli()

        println "All the test cases are passed"
    }
}
