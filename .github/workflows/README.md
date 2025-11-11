# CI/CD Workflows

This directory contains GitHub Actions workflows for continuous integration and deployment.

## Workflows Overview

### 1. Pull Request CI (`pull-request.yml`)
Runs on every pull request to ensure code quality before merging.

**Triggers:** Pull requests to `main` or `develop` branches

**Jobs:**
- **Validate PR**: Checks PR title follows semantic versioning convention
- **Build and Test**: Compiles code, runs unit tests, generates coverage reports
- **Code Quality**: Runs SpotBugs, Checkstyle, and PMD analysis
- **Security Scan**: Performs CodeQL analysis and OWASP dependency checks
- **Docker Build**: Tests Docker image builds for all services
- **Integration Tests**: Runs integration tests with MongoDB
- **PR Summary**: Posts a summary comment on the PR with all check results

**Requirements:**
- MongoDB service is automatically started
- All checks must pass before merging

### 2. CI/CD Pipeline (`ci-cd.yml`)
Main continuous integration and deployment pipeline for main branches.

**Triggers:**
- Push to `main` or `develop` branches
- Manual workflow dispatch

**Jobs:**
- **Build and Test**: Full build with test execution and coverage
- **Security Analysis**: CodeQL and Trivy security scanning
- **Build and Push Images**: Builds and pushes Docker images to GitHub Container Registry
- **Deploy to Staging**: Deploys to staging environment (develop branch)
- **Deploy to Production**: Deploys to production environment (main branch)
- **Performance Tests**: Runs performance tests on staging
- **Notifications**: Sends deployment notifications

**Environments:**
- `staging`: Deployed from `develop` branch
- `production`: Deployed from `main` branch (requires approval)

**Secrets Required:**
- `GITHUB_TOKEN`: Automatically provided
- `SLACK_WEBHOOK`: For Slack notifications (optional)

### 3. Code Quality (`code-quality.yml`)
Comprehensive code quality checks including formatting, linting, and documentation.

**Triggers:** Push or PR to any branch

**Jobs:**
- **Code Formatting**: Checks code follows formatting standards (Spotless)
- **Lint**: Runs Checkstyle, PMD, and SpotBugs
- **SonarCloud Analysis**: Sends code to SonarCloud for analysis
- **Documentation Check**: Validates markdown and checks for broken links
- **License Compliance**: Checks dependencies for license compliance

**Requirements:**
- `SONAR_TOKEN`: Required for SonarCloud integration (optional)

### 4. Dependency Update (`dependency-update.yml`)
Automated dependency updates using Maven and Dependabot.

**Triggers:**
- Scheduled: Every Monday at 9 AM UTC
- Manual workflow dispatch

**Jobs:**
- **Update Dependencies**: Checks for and updates Maven dependencies
- **Auto-merge Dependabot**: Automatically merges minor/patch Dependabot PRs

**Features:**
- Creates automated PRs for dependency updates
- Only updates to compatible versions (no major updates)
- Auto-merges safe updates from Dependabot

## Dependabot Configuration

The `dependabot.yml` file configures automatic dependency updates for:
- Maven dependencies (weekly)
- GitHub Actions (weekly)
- Docker base images for all services (weekly)

## Workflow Status Badges

Add these badges to your README.md:

```markdown
![Pull Request CI](https://github.com/jdavidtorres/management-apps/workflows/Pull%20Request%20CI/badge.svg)
![CI/CD Pipeline](https://github.com/jdavidtorres/management-apps/workflows/CI%2FCD%20Pipeline/badge.svg)
![Code Quality](https://github.com/jdavidtorres/management-apps/workflows/Code%20Quality/badge.svg)
```

## Local Testing

### Test PR Workflow Locally
```bash
# Install act (https://github.com/nektos/act)
brew install act

# Run PR workflow
act pull_request -W .github/workflows/pull-request.yml
```

### Validate Workflows
```bash
# Validate workflow syntax
actionlint .github/workflows/*.yml
```

## Environment Variables

### Build-time Variables
- `JAVA_VERSION`: Java version (17)
- `MAVEN_OPTS`: Maven JVM options (-Xmx3072m)
- `REGISTRY`: Container registry (ghcr.io)

### Runtime Variables
Set these as secrets in GitHub repository settings:
- `SONAR_TOKEN`: SonarCloud authentication token
- `SLACK_WEBHOOK`: Slack webhook URL for notifications

## Docker Image Naming

Images are pushed to GitHub Container Registry with the following naming convention:
```
ghcr.io/jdavidtorres/management-apps/{service-name}:{tag}
```

**Tags:**
- `latest`: Latest build from main branch
- `develop`: Latest build from develop branch
- `v{version}`: Semantic version tag
- `{branch}-{sha}`: Branch name + commit SHA

## Deployment Process

### Staging Deployment (Automatic)
1. Push to `develop` branch
2. CI/CD pipeline runs
3. Docker images are built and pushed
4. Deployment to staging environment
5. Smoke tests verify deployment

### Production Deployment (Manual Approval)
1. Push to `main` branch
2. CI/CD pipeline runs
3. Docker images are built and pushed
4. Manual approval required in GitHub
5. Deployment to production environment
6. Smoke tests verify deployment
7. GitHub release is created

## Monitoring

### Build Artifacts
All workflows upload artifacts that can be downloaded from the Actions tab:
- Test results (XML format)
- Coverage reports (Jacoco)
- OWASP dependency check reports
- License compliance reports
- Performance test results

### Notifications
- PR comments with build status
- Slack notifications (if configured)
- GitHub deployment status updates
- Release notes on production deployments

## Troubleshooting

### Build Failures
1. Check the Actions tab for detailed logs
2. Verify MongoDB service is running for tests
3. Check Java version compatibility
4. Review dependency conflicts

### Docker Build Issues
1. Verify Dockerfile syntax
2. Check base image availability
3. Review build context and .dockerignore
4. Validate multi-stage build steps

### Deployment Failures
1. Check environment secrets are configured
2. Verify network connectivity
3. Review health check endpoints
4. Check container logs

## Best Practices

1. **Keep workflows fast**: Use caching and parallel jobs
2. **Fail fast**: Run quick checks first (formatting, linting)
3. **Security first**: Always scan for vulnerabilities
4. **Clean up**: Remove old artifacts and images
5. **Document changes**: Update this README when modifying workflows

## Contributing

When adding new workflows:
1. Follow existing naming conventions
2. Add comprehensive comments
3. Use reusable actions when possible
4. Test locally before committing
5. Update this documentation

## Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Workflow Syntax](https://docs.github.com/en/actions/reference/workflow-syntax-for-github-actions)
- [Maven with GitHub Actions](https://docs.github.com/en/actions/guides/building-and-testing-java-with-maven)
- [Docker with GitHub Actions](https://docs.github.com/en/actions/guides/publishing-docker-images)
