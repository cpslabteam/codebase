#Codebase

Codebase is an Open Source library of utility classes that are required by Java applications. 

## Motivation behind the Codebase library
The fundamental idea of the Codebase library is to avoid replicating basic utility functionality over and over again. 

In practice, programmers often need to replicate the code of utility classes and, moreover, have a tendency to regard this code as of lesser importance. An obvious consequence is that these classes don't get as thoroughly tested as they often should, often compromising code quality. Therefore, the underlying idea is to put here functionality that are commonly used in the code to avoid that each project/developer to implement its own version and have it throughly tested. 

The main goals of codebase are to: 

* Contributing to increasing the overall code quality by offering a library of high quality utility classes (tested and revised) 
* Simplifying the dependencies of applications (using codebase prevents depending on other libraries) 
* Setting a standard for code quality (the code is available in open source to be analyzed) by helping the users of the library to immitate it.

## Design and development of codebase
* The classes included in the main package are general purpose classes used for, among other, parsing command line arguments, file handling, cyphering, string and binary operations. Utility classes for more specialized functionality are organized into appropriate sub-packages. 

* All the code undergoes strict static checking and most of it is unit tested for maximum reliability.

* Codebase can be used as an OSGi bundle.

* Codebase is a manifest-first Maven project meaning that and that dependencies are declared in the `META-INF/MANIFEST` file instead of in `pom.xml`.

## How to contribute
If you have suggestions for enhancement or have identified a bug in Codebase are happy to receive your contributions nad give you credit.

Please make sure you read the [contribution guide](./CONTRIBUTING.md) and have your Eclise Environment properly installed as we explain in the [Environment Setup Kit](https://github.com/it4energy/environment-setup-kit).

Thank you for considering contributing to Codebase.

## Licensing
The codebase library is licensed under the [LGPL license](http://www.gnu.org/copyleft/lesser.html).

In simple terms this means that the authors of the files and documentation of the documentation contained in the is repository grant you permission to use it on your own projects regardless both for open source and commercial purposes as long as they are credited.

A copy of the license file is included in the root of the project.

## Alternatives to Codebase
Other good open source alternatives to codebase are:

* [Apache Commons](http://commons.apache.org) and 
* [Google Guava](https://code.google.com/p/guava-libraries) 

Although similar in spirit, we decided to keep maintaining codebase because it has a lower footprint and is also simpler to understand.

