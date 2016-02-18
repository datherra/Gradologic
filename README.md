## Weblogic provision with Gradle
This project has all the code needed to spin up an Weblogic 10.3.6 (11g) [Development Edition](http://docs.oracle.com/cd/E23943_01/doc.1111/e14142/zip_installer.htm#WLSIG240) with a basic domain configuration.
This can be used as bootstrap for any other projects producing artifacts that will be deployed onto Weblogic.

It can also deploy WAR files to this local Weblogic or to instances running on servers.

#### Bonus
There is a small app (servlet) that can be deployed on Weblogic to show contents of files placed on the servers.
The file name is hardcoded to avoid malicious usage of this servlet.

#### Pre Reqs

- Java 7
- Works on Windows and Linux

#### Quick working example
Clone repo
```
git clone https://github.com/datherra/Gradologic.git
```
Change dir and bring VM up (requires [VirtualBox](https://www.virtualbox.org/))
```
cd Gradologic
vagrant linux up
```
SSH to the VM and change to project's dir
```
vagrant ssh linux
cd /vagrant
```
Start Weblogic (dowload, installation and setup will be done for you)
```
gradlew startWeblogic
```
Then deploy
```
gradlew wlDeploy
```
Access the app (port 7011 on Linux VM, but 7001 if running on your local machine):
http://localhost:7011/metadata/hello_world

From this point on, any changes made on your servlets is ready to be re-deployed.
Just use `gradle wlDeploy` again and refresh your browser.

#### Deploy to a remote server
Edit the connection details on file `web/build.gradle` accordingly, i.e.:
```
weblogic {
  adminurl = 't3://myserver.mycorp.net:17001'
  user = 'weblogic'
  password = 'that_weblogic_password'
  ...
}
```
The task `wlDeploy` is provided by the Gradle Plugin [com.lv.weblogic](https://plugins.gradle.org/plugin/com.lv.weblogic).
This plugin is a wrapper around Weblogic's original Ant tasks.

## Caveats
Idempotence on Gradle is tricky, specially when dealing with multi-vm environment and/or shared folder on Vagrant. Whenever you destroy the VM is also recommened to delete the __infra/.local__ dir:
```
vagrant destroy linux
rm -rf infra/.local # or correspondent command on Windows
```
For details, please refer to the __How does it work?__ section on [Gradle Docs](https://docs.gradle.org/current/userguide/more_about_tasks.html)


## Tasks implemented by this project
```
gradlew tasks --all

Other tasks
-----------
infra:startWeblogic - Starts Weblogic in background
    infra:createWeblogicDomain - Unpacks domain template creating a domain
    infra:createWeblogicTemplate - Generates a domain template
    infra:downloadFile - Regular download of Weblogic via HTTP
    infra:extractWeblogic - Unpacks previously downloaded WLS Dev edition
    infra:setupWeblogic - Runs Weblogic configure<sh|cmd> script
infra:stopWeblogic - Stops Weblogic using scripts provided by it
```

## Infrastructure
__Main features of `infra/build.gradle`:__

- everything is installed on `infra/.local` and this folder are not meant to be checked in the repo
- tasks are idempotent
- works on Linux and Windows
- consistent environment, built from code. You can just delete the `infra/.local` and run the `startWeblogic` task again to get the environment restored
- domain template creation script is found on `infra/buildSrc/main/resources/createTemplate.py`. It can serve as bootstrap to create more sophisticated domain configurations
- task `createWeblogicTemplate` is an example on how to run any WLST script via Gradle
- task `startWeblogic` is an example on how to use the custom task type __SpawnProcess__, running a process in the background and unblocking Gradle, as opposed to the builtin __Exec__ task type
- it comes with basic Vagrant support, in case you want to try this project on a local Linux VM (Windows VM option is WIP)

## Included Servlets
- `HelloWorldServlet` just servers as proof of concept.
- `InfoServlet` was built with the intent to read a local file deployed by other application on the application server. This file would display metadata about this other app (long story short, the app couldn't do by itself).
- The Java compile tasks are set to generate bytecode version compatible with Java 6. Remove this option from `web/build.gradle` to build bytecode according to the JVM compiling it.