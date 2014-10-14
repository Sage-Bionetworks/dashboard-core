dashboard-core
==============

[![Build Status](https://travis-ci.org/Sage-Bionetworks/dashboard-core.svg?branch=master)](https://travis-ci.org/Sage-Bionetworks/dashboard-core)

Shared by all the dashboard packages.

1. Install Vagrant
2. Install VirtualBox
3. At the project folder, run `vagrant up`
4. Then `vagrant ssh`
5. Once in the guest box, `cd /vagrant`, then `./gradlew clean build`
6. When done, exit the guest box, then `vagrant suspend` or `vagrant halt` 
