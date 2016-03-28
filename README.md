# Green Beans

Green Beans is designed to help you take control of your cash flow.

# Developer Environment

1. Install Ruby
2. Install Java (version 8 or higher)
   * Install [Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
3. Install npm
   * install compass and sass

# Building

In the project folder:

One time only:

1. `cd ui`
2. `npm install`

Then:

1. `mvn package`

That should result in the following on the console:

````
[INFO] Green's Opinion - Finance .......................... SUCCESS [  0.002 s]
[INFO] Green's Opinion - Finance - Services ............... SUCCESS [  4.934 s]
[INFO] Green's Opinion - Finance - UI ..................... SUCCESS [ 29.549 s]
[INFO] Green's Opinion - Finance - Application ............ SUCCESS [ 38.910 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:13 min
[INFO] Finished at: 2016-03-27T17:18:35-07:00
[INFO] Final Memory: 23M/413M
[INFO] ------------------------------------------------------------------------
````

Built binaries:

````
application/target/bundles/GreenBeans-0.8.0.dmg
application/target/bundles/GreenBeans-0.8.0.pkg
application/target/bundles/GreenBeans-0.8.0.msi
````

# License

Copyright 2015, 2016 David Green

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
