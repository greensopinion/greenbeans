
# TODO

1. Icon
1. Reasonable report display without data
1. More colour (menu? branding?)
1. Import deleted files even when unchecked?
1. New category rule same as existing
1. Change Password (reset master password)
1. Wipe data (lost password)

# HELP

ideas:

* including help content elsewhere
* linking to help from pages
* hyperlinks within a page, or going to help scroll to visible
* tooltip help
  * column clarification (not balance)

* about section


# Machine Setup and Build

1. Install JDK 8 or newer
2. Install Git
3. Install Maven 3 or newer
4. Install the JCE policy files
5. Windows only: Install the WIX toolset http://wixtoolset.org/
6. Install npm
7. Install grunt ``npm install -g grunt-cli``
8. cd into the ui project, run ``npm install``
9. cd up one level to the root of the project
10. run ``mvn package``

## Build Troubleshooting on Windows

If your build is complaining that compass.bat is not recognized, follow these steps and try again

1. Install ruby (rubyinstaller.org)
2. Ensure that <RubyHome>\bin is on your path
3. Run ``gem.bat install compass``

If your build has troubles launching PhantomJS to run karma tests, follow these steps and try again

1. Set PHANTOMJS_BIN environment variable to the location of the phantomjs.exe (mine was ``E:\greensopinion.finance2\greensopinion.finance.ui\node_modules\phantomjs\lib\phantom\phantomjs.exe``)

If your build is not producing a Windows msi installer

1. Ensure that Wix 3.0 or greater is installed
2. Ensure that Wix is on the path