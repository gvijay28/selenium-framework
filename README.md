## Selenium-framework

#### 1. Git basic commands

kindly use below git commands for code fetching from your project:

```sh
    1. git clone <project URL> -> git clone https://github.com/gvijay28/selenium-framework.git
    2. ls
    3. cd selenium-framework
    4. git checkout -b <new branch name> origin/<existing branch name>
```


#### 2. Run tests locally using cmd line

You'd want to run your tests locally when first creating them (or fixing them)

##### a. Run all features(i.e. run ALL tests)

cd into {local_working_dir} and run command:
```sh
$ ./gradlew cucumberTest
```

##### b. Run a single feature
You have two different ways to run individual features

Decide which feature you want to run, then you can easily identify the test runner class using syntax 'Run{featurename}Test' e.g.
'RunLoginTest' is the test runner class corresponding to the feature 'Login'

```sh
$ ./gradlew cucumberTest -DcucumberTest.single=RunLoginTest
```

Another way to run a single feature based on tags. Specify tag that you are interested to run @{tagname}. Make sure the feature
file has this tag defined at top of the file @Uploader


```sh
$ ./gradlew cucumberTest -Dcucumber.options="--tags @Uploader"
```


##### c. Run a single scenario from a feature (aka run a single test)

You can have one more level of granular control over what to run by specifying tag associated with the scenario that you are interested to run
@{tagname}. Here I will run a single scenario that I have tagged in the feature file AddNewUser.feature as @Negative

```sh
$ ./gradlew cucumberTest -DcucumberTest.single=RunAddNewUserTest -Dcucumber.options="--tags @Negative"
```


##### d. Run all scenarios that match a tag from all feature files

You can get finer control over what specific scenarios should run e.g. you want to run all Negative tests
from all the feature files. the command-line arguments are similar to above except do NOT mention any feature file:

```sh
$ ./gradlew cucumberTest -Dcucumber.options="--tags @Negative"
```

This will look for all the scenario marked with tag @Negative across all feature files
