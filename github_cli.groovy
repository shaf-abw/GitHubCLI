import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.net.HttpURLConnection
import java.net.URL

// GitHub API Token
String getToken() {
    return System.getenv('ACCESS_TOKEN')
}

/**
 * Function to send an HTTP request and handle the response.
 */
def sendHttpRequest(String endpoint, String method, Map headers = [:], Map payload = [:]) {

    def BASE_URL = "https://api.github.com/repos"
    def OWNER = "shaf-abw"
    def apiUrl = "${BASE_URL}/${OWNER}/${endpoint}"

    def connection = (new URL(apiUrl)).openConnection() as HttpURLConnection
    connection.requestMethod = method
    headers.each { key, value -> connection.setRequestProperty(key, value) }

    if (payload) {
        connection.setRequestProperty("Content-Type", "application/vnd.github+json")
        connection.doOutput = true
        connection.outputStream.withWriter { it << JsonOutput.toJson(payload) }
    }

    def responseCode = connection.responseCode
    def responseText = connection.inputStream.text

    if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
        return new JsonSlurper().parseText(responseText)
    } else {
        throw new RuntimeException("HTTP request failed with status ${responseCode}: ${responseText}")
    }
}

/**
 * Function to create a pull request.
 */
def createPullRequest(String repoName, String title, String body, String headBranch, String baseBranch) {
    def endpoint = "${repoName}/pulls"
    def payload = [
        title: title,
        body: body,
        head: headBranch,
        base: baseBranch
    ]
    def headers = [
        "Authorization": "Bearer ${getToken()}"
    ]
    
    def jsonResponse = sendHttpRequest(endpoint, "POST", headers, payload)
    println("Pull request created successfully!")
    println("URL: ${jsonResponse.html_url}")
    println("ID: ${jsonResponse.id}")
}

/**
 * Function to merge a pull request.
 */
def mergePullRequest(String repoName, int pullRequestNumber) {
    def endpoint = "${repoName}/pulls/${pullRequestNumber}/merge"
    def headers = [
        "Authorization": "Bearer ${getToken()}"
    ]
    
    sendHttpRequest(endpoint, "PUT", headers)
    println("Pull request merged successfully!")
}

// Main function to handle CLI commands
def command = args[0]

try {
    if (command == "create-pr") {
        // Example: groovy github_cli.groovy create-pr repoName "PR Title" "PR Body" headBranch baseBranch
        createPullRequest(args[1], args[2], args[3], args[4], args[5])
    } else if (command == "merge-pr") {
        // Example: groovy github_cli.groovy merge-pr repoName pullRequestNumber
        mergePullRequest(args[1], args[2].toInteger())
    } else {
        println("Unknown command: $command")
        System.exit(1)
    }
} catch (Exception ex) {
    println("Error: ${ex.message}")
    System.exit(1)
}
